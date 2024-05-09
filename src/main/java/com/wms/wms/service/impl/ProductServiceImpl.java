package com.wms.wms.service.impl;

import com.wms.wms.dao.IProductDAO;
import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.dto.response.ProductDetailResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.exception.UniqueConstraintViolationException;
import com.wms.wms.mapper.product.ProductRequestMapper;
import com.wms.wms.mapper.product.ProductResponseMapper;
import com.wms.wms.service.IProductService;
import com.wms.wms.util.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
    private final IProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public ProductDetailResponse save(ProductRequestDTO requestDTO) {
        validate(requestDTO);

        Product product = ProductRequestMapper.INSTANCE.requestToProduct(requestDTO);
        Product dbProduct = productDAO.save(product);
        log.info("Save Product successfully with ID: {}", dbProduct.getId());
        return ProductResponseMapper.INSTANCE.productToResponse(dbProduct);
    }

    @Override
    public ProductDetailResponse findById(int productId) {
        Product dbProduct = getProductById(productId);
        log.info("Get Product detail ID: {} successfully ", productId);
        return ProductResponseMapper.INSTANCE.productToResponse(dbProduct);
    }

    @Override
    public List<ProductDetailResponse> findByName(String name) {
        return null;
    }

    @Override
    public List<ProductDetailResponse> findAll() {
        List<Product> productList = productDAO.findAll();
        List<ProductDetailResponse> productDetailResponseList = productList.stream().map(
                ProductResponseMapper.INSTANCE::productToResponse
        ).toList();
        log.info("Get Product list successfully");
        return productDetailResponseList;
    }

    @Override
    public void deleteById(int id) {
        Product product = getProductById(id);
        productDAO.delete(product);
        log.info("Delete Product ID: {} successfully", id);
    }

    @Override
    public void verifyProductExists(int productId) {
        getProductById(productId);
    }

    @Override
    public boolean hasProductsInCategory(int categoryId) {
        List<Product> productList = productDAO.findByCategoryId(categoryId);
        return !productList.isEmpty();
    }

    /**
     * Retrieves the Product with the given ID from the database.
     * Throws a ResourceNotFoundException if the supplier does not exist.
     *
     * @param id Product ID
     * @return Product entity from Database
     */
    private Product getProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("No Product exists with the given Id: " + id);
        }
        return product;
    }

    private void validate(ProductRequestDTO requestDTO) {
        // validate ID
        Product existingProduct = null;
        if (requestDTO.getId() != 0) {
            existingProduct = getProductById(requestDTO.getId());
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
        List<Product> dbProductList = productDAO.findByNameAndUom(name, uom);
        if (!dbProductList.isEmpty()) {
            throw new UniqueConstraintViolationException("Product name: '" + name + "' and uom: '" + uom + "' already exists");
        }
    }


}
