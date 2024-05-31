package com.wms.wms.entity;

import jakarta.persistence.*;
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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
