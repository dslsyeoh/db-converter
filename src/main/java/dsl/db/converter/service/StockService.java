/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.service;

import dsl.db.converter.object.Stock;

import java.util.List;

public interface StockService
{
    List<Stock> list();
}
