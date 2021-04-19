/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.utils;

import dsl.db.converter.constant.Type;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import static dsl.db.converter.constant.Type.*;

public final class TypeChecker
{
    public TypeChecker() {}

    public static Type getType(Object object)
    {
        try
        {
            if(object instanceof byte[])
            {
                return BLOB;
            }
            else
            {
                String value = String.valueOf(object);
                if(value.isEmpty()) throw new ValueException("Value cannot be empty");
                if(NumberUtils.isCreatable(value)) return NumberUtils.isDigits(value) ? INTEGER : DOUBLE;
                if(isInstantValid(value)) return INSTANT;
                if(isValidDate(value, "yyyy-MM-dd HH:mm:ss")) return TIMESTAMP;
                if(isValidDate(value, "yyyy-MM-dd")) return DATE;
                return value.length() > 255 ? TEXT : value.length() == 1 ? CHAR : VARCHAR;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isValidDate(String value, String pattern)
    {
        try
        {
            DateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            sdf.parse(value);
            sdf.format(value);
            return true;
        }
        catch (ParseException | IllegalArgumentException e)
        {
            return false;
        }
    }

    private static boolean isInstantValid(String value)
    {
        try
        {
            Instant.parse(value);
            return true;
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
    }
}
