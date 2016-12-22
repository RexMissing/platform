package org.whut.meterManagement.database;

import java.sql.*;

/**
 * Created by zhang_minzhong on 2016/12/22.
 */
public class DB {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public static Connection getConn(String connStr){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public static void closeConn(Connection conn){
        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取statement
    public static Statement getStmt(Connection conn){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    //获取preparedStatement
    public static PreparedStatement getPStmt(Connection conn,String sql){
        PreparedStatement pStmt = null;
        try {
            pStmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pStmt;
    }

    public static void closeStmt(Statement stmt){
        try {
            if(stmt!=null){
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取结果集
    public static ResultSet query(Statement stmt,String sql){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void closeResultSet(ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //执行一条更新操作语句，并返回所影响的行数目
    public static int updateGetCount(Statement stmt,String updateSql) {
        int i = 0;
        try {
            i = stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }


    //批量执行sql语句
    public static void executeBatch(Statement stmt,String[] sqls){
        Connection conn = null;
        try {
            conn = stmt.getConnection();
            conn.setAutoCommit(false);
            for(int i=0;i<sqls.length;i++){
                stmt.addBatch(sqls[i]);
            }
            stmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try{
                if(conn!=null){
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    //插入记录，获取生成的自增字段
    public static int updateGetAuto(Statement stmt,String insertSql){
        int i = 0;
        ResultSet rs = null;
        try {
            stmt.executeUpdate(insertSql, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            while (rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeResultSet(rs);
        return i;
    }
}
