package com.wms.wms.mapper.product.impl;

import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.entity.Product;
import com.wms.wms.mapper.productcategory.ProductCategoryRequestMapper;
import com.wms.wms.util.StringHelper;

import java.util.Arrays;

public class ProductRequestMapperImpl {
    Product requestToProduct(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO == null) {
            return null;
        } else {
            Product.ProductBuilder product = Product.builder();
            product.id(productRequestDTO.getId());
            product.name(StringHelper.preProcess(productRequestDTO.getName()));
            product.description(productRequestDTO.getDescription());
            product.code(StringHelper.preProcess(productRequestDTO.getCode()));
            product.uom(StringHelper.preProcess(productRequestDTO.getUom()));
            product.customFields(productRequestDTO.getCustomFields());
            byte[] images = productRequestDTO.getImages();
            if (images != null) {
                product.images(Arrays.copyOf(images, images.length));
            }

            product.productCategory(ProductCategoryRequestMapper.INSTANCE.requestToCategory(productRequestDTO.getProductCategory()));
            return product.build();
        }
    };
}
