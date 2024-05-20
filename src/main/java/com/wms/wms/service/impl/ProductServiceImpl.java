package com.wms.wms.service.impl;

import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.dto.response.ProductDetailResponse;
import com.wms.wms.dto.response.ProductGeneralResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.exception.UniqueConstraintViolationException;
import com.wms.wms.repository.ProductRepository;
import com.wms.wms.service.IProductCategoryService;
import com.wms.wms.service.IProductService;
import com.wms.wms.util.StringHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final IProductCategoryService productCategoryService;

    @Override
    @Transactional
    public ProductDetailResponse save(ProductRequestDTO requestDTO) {
        this.validate(requestDTO);
        Product product;
        if (requestDTO.getId() != 0) {
            product = this.getProduct(requestDTO.getId());
        }
        else {
            product = Product.builder().id(0).build();
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
                .build();
    }

    @Override
    public ProductDetailResponse findById(int productId) {
        Product dbProduct = this.getProduct(productId);
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
                .description(product.getDescription())
                .categoryName(product.getProductCategory().getName())
                .build()
        ).toList();
        log.info("Get Product list successfully");
        return productDetailResponseList;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Product product = this.getProduct(id);
        productRepository.delete(product);
        log.info("Delete Product ID: {} successfully", id);
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Product exists with the given Id: " + id));
    }

    private void validate(ProductRequestDTO requestDTO) {
        // validate ID
        Product existingProduct = null;
        if (requestDTO.getId() != 0) {
            existingProduct = this.getProduct(requestDTO.getId());
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
}
