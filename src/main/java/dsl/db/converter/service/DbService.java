/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.service;

import java.util.List;

public interface DbService
{
    void dump(String tableName, List<String> header, List<Object> row);
}
