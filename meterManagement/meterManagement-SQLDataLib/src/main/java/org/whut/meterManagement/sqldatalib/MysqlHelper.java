package org.whut.meterManagement.sqldatalib;

import org.whut.meterManagement.database.DB;
import org.whut.meterManagement.sqldatalib.entity.MyTableField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by zhang_minzhong on 2016/12/22.
 */
public class MysqlHelper implements SqlHelper {
    private String connString;

    public String getConnString() {
        return connString;
    }

    public void setConnString(String connString) {
        this.connString = connString;
    }

    public MysqlHelper(String connString) {
        this.connString = connString;
    }


    // 执行数据库更新语句
    // <param name="updateSql">SQL语句(Insert,Update或Delete)</param>
    // <returns>更新行数</returns>
    @Override
    public int executeNonQuery(String updateSql) {
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        int i = DB.updateGetCount(stmt, updateSql);
        DB.closeStmt(stmt);
        DB.closeConn(conn);
        return i;
    }

    // 执行查询，返回首行首咧
    // <param name="selectSql">Selece查询语句</param>
    // <returns>返回首行首列对象</returns>
    @Override
    public Object executeScalar(String selectSql) {
        if(selectSql.toUpperCase().indexOf("SELECT")<0)
            return null;
        Object o = null;
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        ResultSet rs = DB.query(stmt, selectSql);
        try {
            rs.next();
            o = rs.getObject(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeResultSet(rs);
        DB.closeStmt(stmt);
        DB.closeConn(conn);
        return o;
    }

    // 查询，返回结果集
    // <param name="sql">Select查询语句</param>
    @Override
    public ResultSet executeQuery(String sql) {
        if(sql.toUpperCase().indexOf("SELECT")<0){
            return null;
        }
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        ResultSet rs = DB.query(stmt, sql);
        //DB.closeResultSet(rs);
        //DB.closeStmt(stmt);
        //DB.closeConn(conn);
        return rs;
    }

    // 开始事物，执行多条更新语句
    // <param name="sqls">SQL语句数组</param>
    @Override
    public void executeWithTransaction(String[] sqls) {
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        DB.executeBatch(stmt,sqls);
        DB.closeStmt(stmt);
        DB.closeConn(conn);
    }

    // 开始事物，执行多条更新语句
    // <param name="sqls">SQL语句列表</param>
    @Override
    public void executeWithTransaction(List<String> sqls) {
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        String[] sqlArr = new String[sqls.size()];
        for(int i = 0;i<sqlArr.length;i++)
            sqlArr[i] = sqls.get(i);
        DB.executeBatch(stmt,sqlArr);
        DB.closeStmt(stmt);
        DB.closeConn(conn);
    }

    // 插入记录，并返回自动编号
    // <param name="insertSql"></param>
    @Override
    public int insertGetID(String insertSql) {
        if(insertSql.toUpperCase().indexOf("INSERT")<0)
            return 0;
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        int generatedKey = DB.updateGetAuto(stmt,insertSql);
        DB.closeStmt(stmt);
        DB.closeConn(conn);
        return generatedKey;
    }

    // 查询记录是否存在
    @Override
    public boolean executeExsit(String selectSql) {
        if(selectSql.toUpperCase().indexOf("SELECT")<0)
            return false;
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        ResultSet rs = DB.query(stmt,selectSql);
        try {
            if(rs.next()){
                DB.closeResultSet(rs);
                DB.closeStmt(stmt);
                DB.closeConn(conn);
                return true;
            }
            else{
                DB.closeResultSet(rs);
                DB.closeStmt(stmt);
                DB.closeConn(conn);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DB.closeResultSet(rs);
        DB.closeStmt(stmt);
        DB.closeConn(conn);
        return false;
    }

    // 向表中插入新记录，并返回自动编号字段
    // <param name="TbName">表名</param>
    // <param name="fields">字段列表</param>
    @Override
    public int insertGetID(String TbName, List<MyTableField> fields) {
        Connection conn = DB.getConn(connString);
        Statement stmt = DB.getStmt(conn);
        String sql = "insert into "+TbName+"(";
        for(int i=0;i<fields.size();i++){
            if(i==0)
                sql += fields.get(0).getFieldName();
            else{
                sql += ","+fields.get(i).getFieldName();
            }
        }
        sql += ") values(";
        for(int i=0;i<fields.size();i++){
            if(i==0)
                sql += "'"+fields.get(0).getFieldValue()+"'";
            else{
                sql += ",'"+fields.get(i).getFieldValue()+"'";
            }
        }
        sql += ")";
        System.out.println(sql);
        int i = DB.updateGetAuto(stmt,sql);
        return i;
    }

    @Override
    public String getConnectionString() {
        return connString;
    }
}
