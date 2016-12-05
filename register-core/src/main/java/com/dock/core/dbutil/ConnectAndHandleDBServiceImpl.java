package com.dock.core.dbutil;

import com.dock.core.utils.StringUtils;
import com.google.common.collect.Maps;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by gaojian on 2016/4/22.
 */

@Service
public class ConnectAndHandleDBServiceImpl implements ConnectAndHandleDBService
{
    private static final Logger log = LoggerFactory.getLogger(ConnectAndHandleDBServiceImpl.class);

    @Override
    public Connection connectDB() throws IOException, ClassNotFoundException, SQLException
    {
        Properties props = new Properties() ;

        props.load(this.getClass().getClassLoader().getResourceAsStream("spring/databases/product-data-access.properties")) ;
        String driver = props.getProperty("jdbc.driverClassName") ;
        String url = props.getProperty("jdbc.url") ;
        String user = props.getProperty("jdbc.username") ;
        String password = props.getProperty("jdbc.password") ;

        log.info("数据库：drver" + driver + "！url：" + url + "!user：" + user);

        Class.forName(driver) ;

        Connection Connection = (Connection) DriverManager.getConnection(url, user, password);

        log.info("数据库连接成功。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。CONNECT DB SUCCESS！！！");

        return Connection;
    }

    @Override
    public List<String> getColumnNames(ResultSet rs) throws SQLException
    {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columnNames = new ArrayList<String>();
        int count = rsmd.getColumnCount();
        for (int i=1; i<=count; i++) {
            String columnName = rsmd.getColumnName(i);
            columnNames.add(columnName);
        }

        log.info("列名为" + columnNames);

        return columnNames;
    }

    @Override
    public List<Map<String, Object>> handleData(Connection connection, ResultSet rs, List<String> columnNames) throws SQLException
    {
        List<Map<String, Object>> dataList = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> obj = Maps.newHashMap();
            for (String columnName : columnNames) {
                String columnValue = StringUtils.msNull(rs.getString(columnName));
                obj.put(columnName, columnValue);
            }
            dataList.add(obj);
        }

        return dataList;
    }

    @Override
    public long countRow(ResultSet rs) throws SQLException
    {
        long count = 0;
        while (rs.next()) {
            count = count + 1;
        }
        return count;
    }

    @Override
    public ResultSet getResultSet(Connection connection, String sql) throws SQLException
    {
        Statement statement = (Statement) connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        return rs;
    }

    @Override
    public List<Map<String, Object>> handleData(String sql) throws SQLException, IOException, ClassNotFoundException
    {
        Connection connection = connectDB();
        ResultSet resultSet = getResultSet(connection, sql);
        List<String> columns = getColumnNames(getResultSet(connection, sql));

        List<Map<String, Object>> dataList = handleData(connection, resultSet, columns);

        return dataList;
    }
}
