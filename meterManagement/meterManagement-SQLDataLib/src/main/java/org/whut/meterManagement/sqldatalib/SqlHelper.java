package org.whut.meterManagement.sqldatalib;

import org.whut.meterManagement.sqldatalib.entity.MyTableField;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by zhang_minzhong on 2016/12/21.
 */
public interface SqlHelper {
    public int executeNonQuery(String updateSql);
    public Object executeScalar(String selectSql);
    ResultSet executeQuery(String sql);
    void executeWithTransaction(String[] sqls);
    void executeWithTransaction(List<String> sqls);
    int insertGetID(String insertSql);
    boolean executeExsit(String selectSql);
    int insertGetID(String TbName, List<MyTableField> fields);
    String getConnectionString();
}
