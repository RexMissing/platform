package org.whut.meterManagement.sqldatalib.sqlhelper;

import org.whut.meterManagement.sqldatalib.dao.DB;
import org.whut.meterManagement.sqldatalib.dao.MySQLDB;
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
    public DB getDB(){
        DB db = new MySQLDB();
        return db;
    }
    // 执行数据库更新语句
    // <param name="updateSql">SQL语句(Insert,Update或Delete)</param>
    // <returns>更新行数</returns>
    @Override
    public int executeNonQuery(String updateSql) {
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
        int i = db.updateGetCount(stmt, updateSql);
        db.closeStmt(stmt);
        db.closeConn(conn);
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
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
        ResultSet rs = db.query(stmt, selectSql);
        try {
            rs.next();
            o = rs.getObject(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.closeResultSet(rs);
            db.closeStmt(stmt);
            db.closeConn(conn);
        }
        return o;
    }

    // 查询，返回结果集
    // <param name="sql">Select查询语句</param>
    @Override
    public ResultSet executeQuery(String sql) {
        if(sql.toUpperCase().indexOf("SELECT")<0){
            return null;
        }
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
        ResultSet rs = db.query(stmt, sql);
        //DB.closeResultSet(rs);
        //DB.closeStmt(stmt);
        //DB.closeConn(conn);
        return rs;
    }

    // 开始事物，执行多条更新语句
    // <param name="sqls">SQL语句数组</param>
    @Override
    public void executeWithTransaction(String[] sqls) {
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
        db.executeBatch(stmt, sqls);
        db.closeStmt(stmt);
        db.closeConn(conn);
    }

    // 开始事物，执行多条更新语句
    // <param name="sqls">SQL语句列表</param>
    @Override
    public void executeWithTransaction(List<String> sqls) {
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
        String[] sqlArr = new String[sqls.size()];
        for(int i = 0;i<sqlArr.length;i++)
            sqlArr[i] = sqls.get(i);
        db.executeBatch(stmt, sqlArr);
        db.closeStmt(stmt);
        db.closeConn(conn);
    }

    // 插入记录，并返回自动编号
    // <param name="insertSql"></param>
    @Override
    public int insertGetID(String insertSql) {
        if(insertSql.toUpperCase().indexOf("INSERT")<0)
            return 0;
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
        int generatedKey = db.updateGetAuto(stmt, insertSql);
        db.closeStmt(stmt);
        db.closeConn(conn);
        return generatedKey;
    }

    // 查询记录是否存在
    @Override
    public boolean executeExsit(String selectSql) {
        if(selectSql.toUpperCase().indexOf("SELECT")<0)
            return false;
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
        ResultSet rs = db.query(stmt, selectSql);
        try {
            if(rs.next()){
                db.closeResultSet(rs);
                db.closeStmt(stmt);
                db.closeConn(conn);
                return true;
            }
            else{
                db.closeResultSet(rs);
                db.closeStmt(stmt);
                db.closeConn(conn);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.closeResultSet(rs);
            db.closeStmt(stmt);
            db.closeConn(conn);
        }
        return false;
    }

    // 向表中插入新记录，并返回自动编号字段
    // <param name="TbName">表名</param>
    // <param name="fields">字段列表</param>
    @Override
    public int insertGetID(String TbName, List<MyTableField> fields) {
        DB db = getDB();
        Connection conn = db.getConn();
        Statement stmt = db.getStmt(conn);
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
        int i = db.updateGetAuto(stmt, sql);
        db.closeStmt(stmt);
        db.closeConn(conn);
        return i;
    }

    @Override
    public String getConnectionString() {
        return DB.connString;
    }
}
