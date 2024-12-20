package com.wms.wms.service.product;

import com.wms.wms.dto.request.product.ProductRequest;
import com.wms.wms.dto.response.product.ProductResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.entity.ProductWarehouse;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.product.ProductRequestMapper;
import com.wms.wms.mapper.product.ProductResponseMapper;
import com.wms.wms.repository.ProductCategoryRepository;
import com.wms.wms.repository.ProductRepository;
import com.wms.wms.repository.ProductWarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductWarehouseRepository productWarehouseRepository;

    @Override
    @Transactional
    public ProductResponse save(ProductRequest requestDTO) {
        this.validate(requestDTO);
        Product product = ProductRequestMapper.INSTANCE.toEntity(requestDTO);

        // Get category
        ProductCategory category = productCategoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException ("No Product category exists with the given Id: " + requestDTO.getCategoryId()));
        product.setProductCategory(category);

        // Save to db
        Product dbProduct = productRepository.save(product);
        log.info("Service Save Product successfully with ID: {}", dbProduct.getId());
        return ProductResponseMapper.INSTANCE.toDto(dbProduct);
    }

    @Override
    public ProductResponse findById(Long productId) {
        Product dbProduct = this.getProductById(productId);
        ProductResponse response = ProductResponseMapper.INSTANCE.toDto(dbProduct);

        BigDecimal quantity = BigDecimal.ZERO;
        List<ProductWarehouse> productWarehouses = productWarehouseRepository.findByProductId(productId);
        for (ProductWarehouse productWarehouse : productWarehouses) {
            quantity = quantity.add(productWarehouse.getQuantityOnHand());
        }
        response.setQuantity(quantity);

        log.info("Service Get Product detail ID: {} successfully ", productId);
        return response;
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Product> productList = productRepository.findAll();
        List<ProductWarehouse> productWarehouses = productWarehouseRepository.findAll();
        List<ProductResponse> responses = ProductResponseMapper.INSTANCE.toDtoList(productList);

        for (ProductResponse response : responses) {
            BigDecimal quantity = BigDecimal.ZERO;

            for (ProductWarehouse productWarehouse : productWarehouses) {
                if (productWarehouse.getProduct().getId().equals(response.getId())) {
                    quantity = quantity.add(productWarehouse.getQuantityOnHand());
                }
            }
            response.setQuantity(quantity);
        }

        log.info("Service Get Product list successfully");
        return responses;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = this.getProductById(id);
//        this.checkConstraintToDelete(product);
        productRepository.delete(product);
        log.info("Service Delete Product ID: {} successfully", id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Product exists with the given Id: " + id));
    }

    private void validate(ProductRequest requestDTO) {
        // validate ID
        Product existingProduct = null;
        if (requestDTO.getId() != 0) {
            existingProduct = this.getProductById(requestDTO.getId());
        }


//        if (existingProduct == null) {
//            checkUniqueNameAndUomForCreate(requestDTO.getName(), requestDTO.getUom());
//        }
//        else {
//            checkUniqueNameAndUomForUpdate(requestDTO.getName(), requestDTO.getUom(),
//                    existingProduct.getName(), existingProduct.getUom());
//        }
    }

//    /**
//     * Checks if the provided product (name, uom) is unique.
//     *
//     * @param name Product name
//     * @param uom Product uom
//     * @throws UniqueConstraintViolationException If a product with the same (name, uom) already exists.
//     */
//    private void checkUniqueNameAndUomForCreate(String name, String uom) {
//        String cleanName = StringHelper.preProcess(name);
//        String cleanUom = StringHelper.preProcess(uom);
//        checkUniqueNameAndUom(cleanName, cleanUom);
//    }
//
//    private void checkUniqueNameAndUomForUpdate(String newName, String newUom, String existingName, String existingUom) {
//        String cleanNewName = StringHelper.preProcess(newName);
//        String cleanNewUom = StringHelper.preProcess(newUom);
//
//        if (cleanNewName.equals(existingName) && cleanNewUom.equals(existingUom)) {
//            return;
//        }
//        checkUniqueNameAndUom(cleanNewName, cleanNewUom);
//    }
//
//    private void checkUniqueNameAndUom(String name, String uom) {
//        List<Product> dbProductList = productRepository.findByNameAndUom(name, uom);
//        if (!dbProductList.isEmpty()) {
//            throw new UniqueConstraintViolationException("Product name: '" + name + "' and uom: '" + uom + "' already exists");
//        }
//    }
//
//    private void checkConstraintToDelete(Product product) {
//        if (orderItemRepository.existsByProductId(product.getId())) {
//            throw new ConstraintViolationException("Cannot delete product. Associated order item exist.");
//        }
//    }
}
