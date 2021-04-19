/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.constant;

import java.util.Arrays;
import java.util.Objects;

public enum Type
{
    BOOLEAN,
    INTEGER,
    DOUBLE,
    CHAR,
    VARCHAR,
    TEXT,
    DATE,
    TIMESTAMP,
    INSTANT,
    BLOB;

    public static Type parse(String value)
    {
        return Arrays.stream(values())
                .filter(type -> Objects.equals(type.name(), value.toUpperCase()))
                .findFirst().orElse(null);
    }
}
