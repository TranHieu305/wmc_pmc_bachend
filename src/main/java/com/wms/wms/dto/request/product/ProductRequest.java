package com.wms.wms.dto.request.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters")
    private String name;

    @Size(max = 255, message = "Product description must be under 256 characters")
    private  String description;

    @Size(max = 63, message = "Product code must be under 63 characters")
    @NotBlank(message = "Product code cannot be blank")
    private String code;

    @Size(max = 63, message = "Product uom must be under 63 characters")
    @NotBlank(message = "Product uom cannot be blank")
    private String uom;

    private Long categoryId;
}
