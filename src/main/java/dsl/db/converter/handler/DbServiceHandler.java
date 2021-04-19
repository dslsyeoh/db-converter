/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.handler;

import dsl.db.converter.object.DatabaseObject;
import dsl.db.converter.service.DbService;
import dsl.db.converter.utils.ExcelUtils;
import dsl.db.converter.utils.SQLGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service("dbService")
public class DbServiceHandler implements DbService
{
    @Autowired
    private DataSource dataSource;

    @Override
    public void dump(String excelFilename)
    {
        List<DatabaseObject> databaseObject = ExcelUtils.read(excelFilename);
        try (Connection connection = dataSource.getConnection())
        {
            connection.setAutoCommit(false);
            for(DatabaseObject dbObject : databaseObject)
            {
                String sql = SQLGenerator.generateSQL(dbObject);

                try (PreparedStatement statement = connection.prepareStatement(sql))
                {
                    for(List<Object> row : dbObject.getData())
                    {
                        SQLGenerator.prepareStatement(statement, dbObject.getDataTypes(), row);
                        statement.execute();
                    }
                }
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.out.println("Database has been initialized");
        }
    }
}
