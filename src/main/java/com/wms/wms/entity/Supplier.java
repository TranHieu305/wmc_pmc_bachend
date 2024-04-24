package com.wms.wms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "supplier")
public class Supplier extends AbstractEntity{
    // Define fields

    @Column(name = "name")
    @NotBlank(message = "Supplier name cannot be blank")
    @Size(min = 1, max = 255, message = "Supplier name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Customer description must be between 1 and 255 characters")
    private  String description;

    @Column(name = "address")
    @Size(max = 255, message = "Supplier address must be between 1 and 255 characters")
    private String address;

    @Column(name = "email")
    @Email(message = "Please provide a valid phone email")
    @NotNull(message = "Email is required")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    @NotBlank(message = "Phone number is required")
    private String phone;
}
