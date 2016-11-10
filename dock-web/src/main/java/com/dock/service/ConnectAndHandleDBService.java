package com.dock.service;

import com.mysql.jdbc.Connection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by gaojian on 2016/4/22.
 */
public interface ConnectAndHandleDBService
{
    /**
     * 用于连接数据库
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    Connection connectDB() throws IOException, ClassNotFoundException, SQLException;

    /**
     * 获取列名
     * @return
     */
    List<String> getColumnNames(ResultSet rs) throws SQLException;

    /**
     * 用于处理从数据库查询所得的数据 组装数据   List< map(columnName, value) >
     */
    List<Map<String, Object>> handleData(Connection connection, ResultSet rs, List<String> columnNames) throws SQLException;

    /**
     * 传一个sql返回数据list
     * @param sql
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    List<Map<String, Object>> handleData(String sql) throws SQLException, IOException, ClassNotFoundException;

    /**
     * 获取数据总条数
     * @return
     */
    long countRow(ResultSet rs) throws SQLException;

    /**
     * 获取结果集
     * @param connection
     * @param sql
     * @return
     */
    ResultSet getResultSet(Connection connection, String sql) throws SQLException;
}
