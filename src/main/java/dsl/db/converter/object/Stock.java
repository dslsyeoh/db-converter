/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.object;

import lombok.Data;

@Data
public class Stock
{
    private long id;
    private String name;
    private String description;
    private int quantity;
    private double price;
}
