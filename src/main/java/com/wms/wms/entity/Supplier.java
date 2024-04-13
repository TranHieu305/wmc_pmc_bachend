package com.wms.wms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "supplier")
public class Supplier {
    // Define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int id;

    @Column(name = "name")
    @NotBlank(message = "Supplier name cannot be blank")
    @Size(min = 1, max = 255, message = "Supplier name must be between 1 and 255 characters")
    private String name;

    @Column(name = "address")
    @Size(max = 255, message = "Supplier address must be between 1 and 255 characters")
    private String address;

    @Column(name = "email")
    @Email(message = "Please provide a valid phone email")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    private String phone;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private  Timestamp modifiedAt;

    public Supplier() {
    }

    public Supplier(int id, String name, String address, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
