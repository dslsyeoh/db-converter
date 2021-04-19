/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="stocks")
public class StockEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double price;

    @Column
    private int quantity;
}
