package org.whut.meterManagement.std;

import org.whut.meterManagement.sqldatalib.SqlHelper;

/**
 * Created by chenfu on 2016/12/22.
 */
public class StdUtils {

    private static SqlHelper sqlh;

    public static SqlHelper getSqlh() {
        if (sqlh==null)
        {
            //return new MSSqlHelper(ConfigurationManager.AppSettings["SQLServer"]);
//            if (ConfigurationManager.AppSettings["DataBaseType"]=="MSSQL")
//            {
//                return new MSSqlHelper(ConfigurationManager.AppSettings["MSSQL"]);
//            }
//            if (ConfigurationManager.AppSettings["DataBaseType"]=="MySQL")
//            {
//                return new MySqlHelper(ConfigurationManager.AppSettings["MySQL"]);
//            }
            return null;
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
