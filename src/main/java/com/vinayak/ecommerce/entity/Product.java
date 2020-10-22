package com.vinayak.ecommerce.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product")
@Data
public class Product {

    private Long id;

    private String sku;
}
