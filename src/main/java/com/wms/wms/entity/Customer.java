package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {
    // Define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Customer name cannot be blank")
    @Size(min = 1, max = 255, message = "Customer name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Customer description must be between 1 and 255 characters")
    private  String description;

    @Column(name = "address")
    @Size(min = 1, max = 255, message = "Customer address must be between 1 and 255 characters")
    private String address;

    @Column(name = "email")
    @Email(message = "Please provide a valid phone email")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    private String phone;

    @Column(name = "longitude")
    @Digits(integer = 10, fraction = 6, message = "Customer longitude must be decimal")
    private BigDecimal longitude = BigDecimal.ZERO;

    @Column(name = "latitude")
    @Digits(integer = 10, fraction = 6, message = "Customer latitude must be decimal")
    private BigDecimal latitude = BigDecimal.ZERO;
}
