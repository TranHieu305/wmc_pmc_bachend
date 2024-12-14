//package com.wms.wms.service;
//
//import com.wms.wms.dto.request.ProductPriceRequest;
//import com.wms.wms.entity.Partner;
//import com.wms.wms.entity.Product;
//import com.wms.wms.entity.ProductPrice;
//
//import java.util.List;
//
//public interface ProductPriceService {
//
//    // Save product price
//    ProductPrice save(ProductPriceRequest request);
//
//    List<ProductPrice> findAll();
//
//    void deleteById(int id);
//
//    ProductPrice getProductPriceById(int id);
//
//    // Get current prices of product by product Id
//    List<ProductPrice> findCurrentPricesByProductId(int productId);
//
//    List<ProductPrice> findCurrentPricesByPartnerId(int partnerId);
//
//    // Get current prices of product
//    List<ProductPrice> getCurrentPricesOfProduct(Product product);
//
//    ProductPrice getCurrentPriceByProductAndPartner(Product product, Partner partner);
//
//    // Get all price by productId
//    List<ProductPrice> findPricesByProductId(int productId);
//
//    List<ProductPrice> findAllCurrentPrices();
//}
