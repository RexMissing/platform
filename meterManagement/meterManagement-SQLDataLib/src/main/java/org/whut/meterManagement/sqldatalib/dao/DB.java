package org.whut.meterManagement.sqldatalib.dao;

import java.sql.*;

/**
 * Created by zhang_minzhong on 2016/12/29.
 */
public interface DB {
    public final String connString = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=root";
    //获取连接
    public Connection getConn();

    //关闭连接
    public void closeConn(Connection conn);

    //根据结果集关闭连接
    public void closeConn(ResultSet rs);

    //根据Statement关闭连接
    public void closeConn(Statement stmt);

    //获取statement
    public Statement getStmt(Connection conn);

    //获取preparedStatement
    public PreparedStatement getPStmt(Connection conn,String sql);

    //关闭Statement
    public void closeStmt(Statement stmt);

    //获取结果集
    public ResultSet query(Statement stmt,String sql);

    //关闭结果集
    public void closeResultSet(ResultSet rs);

    //执行一条更新操作语句，并返回所影响的行数目
    public int updateGetCount(Statement stmt,String updateSql) ;


    //批量执行sql语句
    public void executeBatch(Statement stmt,String[] sqls);

    //插入记录，获取生成的自增字段
    public int updateGetAuto(Statement stmt,String insertSql);
}
