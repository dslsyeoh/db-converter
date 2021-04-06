/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.domain;

import dsl.db.converter.constant.TestEnum;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Table(name ="test")
public class TestEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int colInt;

    @Column
    private double colDouble;

    @Column
    private char colChar;

    @Column
    private String colString;

    @Column
    @Lob
    private String colLongText;

    @Column
    private boolean colBoolean;

    @Column
    @Lob
    private byte[] colBlob;

    @Column
    private Timestamp colTimestamp;

    @Column
    private Date colDate;

    @Column
    private Instant colInstant;

    @Column
    @Enumerated(EnumType.STRING)
    private TestEnum colEnum;

}
