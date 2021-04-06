/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public final class Converter
{
    public Converter() {}

    public static Timestamp toTimestamp(String value)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            formatter.setLenient(false);
            return new Timestamp(formatter.parse(value).getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp fromInstant(String value)
    {
        try
        {
            return Timestamp.from(Instant.parse(value));
        }
        catch (DateTimeParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
