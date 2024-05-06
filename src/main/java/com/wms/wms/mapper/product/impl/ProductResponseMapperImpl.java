package com.wms.wms.mapper.product.impl;

import com.wms.wms.dto.response.ProductDetailResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.mapper.productcategory.ProductCategoryResponseMapper;

import java.util.Arrays;

public class ProductResponseMapperImpl {
    ProductDetailResponse productToResponse(Product product){
        if (product == null) {
            return null;
        } else {
            ProductDetailResponse.ProductDetailResponseBuilder productDetailResponse = ProductDetailResponse.builder();
            productDetailResponse.id(product.getId());
            productDetailResponse.name(product.getName());
            productDetailResponse.description(product.getDescription());
            productDetailResponse.code(product.getCode());
            productDetailResponse.uom(product.getUom());
            productDetailResponse.customFields(product.getCustomFields());
            byte[] images = product.getImages();
            if (images != null) {
                productDetailResponse.images(Arrays.copyOf(images, images.length));
            }

            productDetailResponse.productCategory(ProductCategoryResponseMapper.INSTANCE.categoryToResponse(product.getProductCategory()));
            productDetailResponse.createdAt(product.getCreatedAt());
            productDetailResponse.modifiedAt(product.getModifiedAt());
            return productDetailResponse.build();
        }
    };


}
