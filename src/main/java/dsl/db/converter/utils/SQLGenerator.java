/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.utils;

import dsl.db.converter.constant.Type;
import dsl.db.converter.object.DatabaseObject;

import javax.activation.UnsupportedDataTypeException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class SQLGenerator
{
    public SQLGenerator() {}

    public static String generateSQL(DatabaseObject dbObject)
    {
        String columnNames = String.join(", ", dbObject.getColumnNames());
        String params = IntStream.range(0, dbObject.getDataSize()).mapToObj(i -> "?").collect(Collectors.joining(", "));

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", dbObject.getTableName(), columnNames, params);

        System.out.println("Generated SQL: " + sql);

        return sql;
    }

    public static PreparedStatement prepareStatement(PreparedStatement statement, List<String> dataTypes, List<Object> data)
    {
        for (int idx = 0; idx < data.size(); idx++)
        {
            Object value = data.get(idx);
            Type type = Type.parse(dataTypes.get(idx));

            if(Objects.nonNull(type))
            {
                try
                {
                    prepareStatement(statement, idx + 1, value, type);
                }
                catch (SQLException | UnsupportedDataTypeException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return statement;
    }

    private static void prepareStatement(PreparedStatement statement, int position, Object object, Type type) throws SQLException, UnsupportedDataTypeException
    {
        String value = String.valueOf(object);
        switch (type)
        {
            case VARCHAR:
            case CHAR:
            case TEXT:
                statement.setString(position, value);
                break;
            case INTEGER:
                statement.setInt(position, (int) Double.parseDouble(value));
                break;
            case DOUBLE:
                statement.setDouble(position, Double.parseDouble(value));
                break;
            case DATE:
                statement.setDate(position, Date.valueOf(value));
                break;
            case TIMESTAMP:
                statement.setTimestamp(position, Converter.toTimestamp(value));
                break;
            case INSTANT:
                statement.setTimestamp(position, Converter.fromInstant(value));
                break;
            case BOOLEAN:
                statement.setBoolean(position, Boolean.parseBoolean(value));
                break;
            case BLOB:
                statement.setBytes(position, ((byte[]) object));
                break;
            default:
                throw new UnsupportedDataTypeException(type + " is not supported");
        }
    }
}
