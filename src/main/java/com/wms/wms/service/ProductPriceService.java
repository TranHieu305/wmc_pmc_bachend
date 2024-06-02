package com.wms.wms.service;

import com.wms.wms.dto.request.ProductPriceRequest;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductPrice;

import java.util.List;

public interface ProductPriceService {

    // Save product price
    ProductPrice save(ProductPriceRequest request);

    List<ProductPrice> findAll();

    void deleteById(int id);

    ProductPrice getProductPriceById(int id);

    ProductPrice getCurrentPrice(Product product);

    List<ProductPrice> findPricesByProductId(int productId);

}
