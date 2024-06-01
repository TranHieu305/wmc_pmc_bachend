package com.wms.wms.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
public class SupplierRequest {
    @Setter
    private int id;

    @NotBlank(message = "Supplier name cannot be blank")
    @Size(min = 1, max = 255, message = "Supplier name must be between 1 and 255 characters")
    private String name;

    @Size(max = 255, message = "Customer description must be between 1 and 255 characters")
    private  String description;

    @Size(max = 255, message = "Supplier address must be between 1 and 255 characters")
    private String address;

    @Email(message = "Please provide a valid phone email")
    @NotNull(message = "Email is required")
    private String email;

    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    @NotBlank(message = "Phone number is required")
    private String phone;
}
