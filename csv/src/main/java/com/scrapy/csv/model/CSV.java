package com.scrapy.csv.model;

import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name = "product",schema = "hackathon_catalog",uniqueConstraints = @UniqueConstraint(columnNames ={"product_id"}))
public class CSV {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Id;
    @Column(name = "product_id")
    private String productId;


}
