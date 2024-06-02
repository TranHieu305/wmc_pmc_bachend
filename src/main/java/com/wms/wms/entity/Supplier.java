package com.wms.wms.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "supplier")
@DiscriminatorValue("SUPPLIER")
public class Supplier extends AbstractPartner{

}
