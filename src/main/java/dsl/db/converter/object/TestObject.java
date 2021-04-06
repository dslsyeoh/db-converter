/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.object;

import dsl.db.converter.constant.TestEnum;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Data
public class TestObject
{
    private Long id;
    private int colInt;
    private double colDouble;
    private char colChar;
    private String colString;
    private String colLongText;
    private boolean colBoolean;
    private byte[] colBlob;
    private Timestamp colTimestamp;
    private Date colDate;
    private Instant colInstant;
    private TestEnum colEnum;
}
