package com.wms.wms.service.impl;

import com.wms.wms.dao.IProductDAO;
import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.dto.response.ProductDetailResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.exception.UniqueConstraintViolationException;
import com.wms.wms.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
    private IProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(IProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public ProductDetailResponse save(ProductRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ProductDetailResponse findById(int productId) {
        return null;
    }

    @Override
    public List<ProductDetailResponse> findByName(String name) {
        return null;
    }

    @Override
    public List<ProductDetailResponse> findAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void verifyProductExists(int productId) {

    }

    /**
     * Retrieves the Product with the given ID from the database.
     * Throws a ResourceNotFoundException if the supplier does not exist.
     *
     * @param id Product ID
     * @return Product entity from Database
     */
    private Product getById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("No Product exists with the given Id: " + id);
        }
        return product;
    }


    /**
     * Checks if the provided product request DTO satisfies unique constraints.
     *
     * @param requestDTO The product request DTO to validate.
     * @throws UniqueConstraintViolationException If any unique constraint is violated.
     */
    private void checkUniqueConstraints(ProductRequestDTO requestDTO) {
        checkUniqueNameAndUom(requestDTO.getName(), requestDTO.getUom());
    }

    /**
     * Checks if the provided product (name, uom) is unique.
     *
     * @param name Product name
     * @param uom Product uom
     * @throws UniqueConstraintViolationException If a product with the same (name, uom) already exists.
     */
    private void checkUniqueNameAndUom(String name, String uom) {
        List<Product> dbProductList = productDAO.findByNameAndUom(name, uom);
        if (!dbProductList.isEmpty()) {
            throw new UniqueConstraintViolationException("Product name and uom must be unique");
        }
    }
}
