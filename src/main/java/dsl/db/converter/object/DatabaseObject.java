/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.object;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class DatabaseObject
{
    private String tableName;
    private Set<String> columnNames;
    private List<String> dataTypes;
    private List<List<Object>> data;
    private int dataSize;
}
