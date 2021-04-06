/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.handler;

import dsl.db.converter.service.DbService;
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
    public void dump(String tableName, List<String> header, List<Object> row)
    {
        try (Connection connection = dataSource.getConnection())
        {
            connection.setAutoCommit(false);
            String sql = SQLGenerator.generateSQL(tableName, header, row);

            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                SQLGenerator.prepareStatement(statement, row);
                statement.execute();
            }
            connection.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
