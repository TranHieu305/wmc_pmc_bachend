package com.wms.wms.service.impl;

import com.wms.wms.dto.request.ProductRequest;
import com.wms.wms.dto.response.product.ProductDetailResponse;
import com.wms.wms.dto.response.product.ProductGeneralResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.entity.ProductPrice;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.exception.UniqueConstraintViolationException;
import com.wms.wms.repository.OrderItemRepository;
import com.wms.wms.repository.ProductRepository;
import com.wms.wms.service.ProductCategoryService;
import com.wms.wms.service.ProductPriceService;
import com.wms.wms.service.ProductService;
import com.wms.wms.util.StringHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryService productCategoryService;
    private final OrderItemRepository orderItemRepository;
    private final ProductPriceService productPriceService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductCategoryService productCategoryService,
                              OrderItemRepository orderItemRepository,
                              @Lazy ProductPriceService productPriceService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
        this.orderItemRepository = orderItemRepository;
        this.productPriceService = productPriceService;
    }

    @Override
    @Transactional
    public ProductDetailResponse save(ProductRequest requestDTO) {
        this.validate(requestDTO);
        Product product;
        if (requestDTO.getId() != 0) {
            product = this.getProductById(requestDTO.getId());
        }
        else {
            product = Product.builder().build();
        }

        product.setName(StringHelper.preProcess(requestDTO.getName()));
        product.setCode(StringHelper.preProcess(requestDTO.getCode()));
        product.setUom(StringHelper.preProcess(requestDTO.getUom()));
        product.setDescription(requestDTO.getDescription());
        product.setCustomFields(requestDTO.getCustomFields());
        product.setImages(requestDTO.getImages());

        ProductCategory category = productCategoryService.getCategory(requestDTO.getCategoryId());
        product.setProductCategory(category);

        Product dbProduct = productRepository.save(product);
        log.info("Save Product successfully with ID: {}", dbProduct.getId());
        ProductPrice currentPrice = productPriceService.getCurrentPrice(dbProduct);
        return ProductDetailResponse.builder()
                .id(dbProduct.getId())
                .name(dbProduct.getName())
                .description(dbProduct.getDescription())
                .code(dbProduct.getCode())
                .uom(dbProduct.getUom())
                .customFields(dbProduct.getCustomFields())
                .images(dbProduct.getImages())
                .productCategoryId(dbProduct.getProductCategory().getId())
                .productCategory(category)
                .currentPrice(currentPrice)
                .createdAt(dbProduct.getCreatedAt())
                .modifiedAt(dbProduct.getModifiedAt())
                .build();
    }

    @Override
    public ProductDetailResponse findById(int productId) {
        Product dbProduct = this.getProductById(productId);
        ProductPrice productPrice = productPriceService.getCurrentPrice(dbProduct);
        log.info("Get Product detail ID: {} successfully ", productId);
        return ProductDetailResponse.builder()
                .id(dbProduct.getId())
                .name(dbProduct.getName())
                .description(dbProduct.getDescription())
                .code(dbProduct.getCode())
                .uom(dbProduct.getUom())
                .customFields(dbProduct.getCustomFields())
                .images(dbProduct.getImages())
                .productCategoryId(dbProduct.getProductCategory().getId())
                .productCategory(dbProduct.getProductCategory())
                .currentPrice(productPrice)
                .createdAt(dbProduct.getCreatedAt())
                .modifiedAt(dbProduct.getModifiedAt())
                .build();
    }

    @Override
    public List<ProductDetailResponse> findByName(String name) {
        return null;
    }

    @Override
    public List<ProductGeneralResponse> findAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductGeneralResponse> productDetailResponseList = productList.stream().map(product -> ProductGeneralResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .uom(product.getUom())
                .categoryName(product.getProductCategory().getName())
                .build()
        ).toList();
        log.info("Get Product list successfully");
        return productDetailResponseList;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Product product = this.getProductById(id);
        this.checkConstraintToDelete(product);
        productRepository.delete(product);
        log.info("Delete Product ID: {} successfully", id);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Product exists with the given Id: " + id));
    }

    private void validate(ProductRequest requestDTO) {
        // validate ID
        Product existingProduct = null;
        if (requestDTO.getId() != 0) {
            existingProduct = this.getProductById(requestDTO.getId());
        }
        if (existingProduct == null) {
            checkUniqueNameAndUomForCreate(requestDTO.getName(), requestDTO.getUom());
        }
        else {
            checkUniqueNameAndUomForUpdate(requestDTO.getName(), requestDTO.getUom(),
                    existingProduct.getName(), existingProduct.getUom());
        }
    }

    /**
     * Checks if the provided product (name, uom) is unique.
     *
     * @param name Product name
     * @param uom Product uom
     * @throws UniqueConstraintViolationException If a product with the same (name, uom) already exists.
     */
    private void checkUniqueNameAndUomForCreate(String name, String uom) {
        String cleanName = StringHelper.preProcess(name);
        String cleanUom = StringHelper.preProcess(uom);
        checkUniqueNameAndUom(cleanName, cleanUom);
    }

    private void checkUniqueNameAndUomForUpdate(String newName, String newUom, String existingName, String existingUom) {
        String cleanNewName = StringHelper.preProcess(newName);
        String cleanNewUom = StringHelper.preProcess(newUom);

        if (cleanNewName.equals(existingName) && cleanNewUom.equals(existingUom)) {
            return;
        }
        checkUniqueNameAndUom(cleanNewName, cleanNewUom);
    }

    private void checkUniqueNameAndUom(String name, String uom) {
        List<Product> dbProductList = productRepository.findByNameAndUom(name, uom);
        if (!dbProductList.isEmpty()) {
            throw new UniqueConstraintViolationException("Product name: '" + name + "' and uom: '" + uom + "' already exists");
        }
    }

    private void checkConstraintToDelete(Product product) {
        if (orderItemRepository.existsByProductId(product.getId())) {
            throw new ConstraintViolationException("Cannot delete product. Associated order item exist.");
        }
    }
}
