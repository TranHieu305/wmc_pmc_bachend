package com.wms.wms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer")
public class Customer {
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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private  Timestamp modifiedAt;

    public Customer() {
    }

    public Customer(int id, String name, String description, String address, String email, String phone,
                    BigDecimal longitude, BigDecimal latitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
