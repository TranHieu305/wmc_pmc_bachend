package com.wms.wms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
@Table(name="partner")
@DiscriminatorColumn(name = "partner_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Partner name cannot be blank")
    @Size(min = 1, max = 255, message = "Partner name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Partner description must be between 1 and 255 characters")
    private  String description;

    @Column(name = "address")
    @Size(max = 255, message = "Partner address must be between 1 and 255 characters")
    private String address;

    @Column(name = "email")
    @Email(message = "Please provide a valid email")
    @NotNull(message = "Partner Email is required")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    @NotBlank(message = "Phone number is required")
    private String phone;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
