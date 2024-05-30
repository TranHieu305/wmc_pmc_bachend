package com.wms.wms.service;

import com.wms.wms.dto.request.ProductPriceRequest;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductPrice;

public interface IProductPriceService {

    // Save product price
    ProductPrice save(ProductPriceRequest request);

    void deleteById(int id);

    ProductPrice getProductPriceById(int id);

    ProductPrice getCurrentPrice(Product product);
}
