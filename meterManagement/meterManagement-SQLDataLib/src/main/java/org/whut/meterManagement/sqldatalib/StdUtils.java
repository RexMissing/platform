package org.whut.meterManagement.sqldatalib;

/**
 * Created by chenfu on 2016/12/22.
 */
public class StdUtils {

    private static SqlHelper sqlh;

    public static SqlHelper getSqlh() {
        if (sqlh==null)
        {
            return new MysqlHelper("jdbc:");
        }
        else
        {
            return sqlh;
        }
    }

    public static void setSqlh(SqlHelper sqlh) {
        StdUtils.sqlh = sqlh;
    }


}
