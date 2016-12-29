package org.whut.meterManagement.sqldatalib.dao;

import java.sql.*;

/**
 * Created by zhang_minzhong on 2016/12/22.
 */
public class MySQLDB implements DB{

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public Connection getConn(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关闭连接
    public void closeConn(Connection conn){
        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //根据结果集关闭连接
    public void closeConn(ResultSet rs){
        if(rs!=null) {
            Statement stmt = null;
            Connection conn = null;
            try {
                stmt = rs.getStatement();
                conn = stmt.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSet(rs);
                closeStmt(stmt);
                closeConn(conn);
            }
        }
    }
    //根据Statement关闭连接
    public void closeConn(Statement stmt){
        if(stmt!=null){
            Connection conn = null;
            try {
                conn = stmt.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeStmt(stmt);
                closeConn(conn);
            }
        }
    }

    //获取statement
    public Statement getStmt(Connection conn){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            closeConn(conn);
            e.printStackTrace();
        }
        return stmt;
    }

    //获取preparedStatement
    public PreparedStatement getPStmt(Connection conn,String sql){
        PreparedStatement pStmt = null;
        try {
            pStmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            closeConn(conn);
            e.printStackTrace();
        }
        return pStmt;
    }

    //关闭Statement
    public void closeStmt(Statement stmt){
        try {
            if(stmt!=null){
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取结果集
    public ResultSet query(Statement stmt,String sql){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            closeConn(stmt);
            e.printStackTrace();
        }
        return rs;
    }

    //关闭结果集
    public void closeResultSet(ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //执行一条更新操作语句，并返回所影响的行数目
    public int updateGetCount(Statement stmt,String updateSql) {
        int i = 0;
        try {
            i = stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            closeConn(stmt);
            e.printStackTrace();
        }
        return i;
    }


    //批量执行sql语句
    public void executeBatch(Statement stmt,String[] sqls){
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
            closeConn(stmt);
            e.printStackTrace();
        }
    }

    //插入记录，获取生成的自增字段
    public int updateGetAuto(Statement stmt,String insertSql){
        int i = 0;
        ResultSet rs = null;
        try {
            stmt.executeUpdate(insertSql, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            while (rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException e) {
            closeResultSet(rs);
            closeConn(stmt);
            e.printStackTrace();
        }
        closeResultSet(rs);
        return i;
    }
}
