
import org.whut.meterManagement.database.DB;
import org.whut.meterManagement.sqldatalib.MysqlHelper;
import org.whut.meterManagement.sqldatalib.entity.MyTableField;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2016/12/21.
 */
public class Test {
    public static void main(String[] args){
        //database=sm_meter;user id=cdb_outerroot;password=jinklmou#8008;
        // server=559beeffde656.sh.cdb.myqcloud.com;port=6039;characterset=gbk;allowzerodatetime=True;convertzerodatetime=True
        MysqlHelper mysqlHelper = new MysqlHelper("jdbc:mysql://127.0.0.1:3306/test?user=root&password=root");
        ResultSet rs = mysqlHelper.executeQuery("select * from user");
        try {
            while (rs.next()){
                System.out.println(rs.getString(1)+":"+rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            System.out.print("finally");
            DB.closeConn(rs);
        }
        mysqlHelper.executeNonQuery("insert user(username,password) select username,password from user");
        //insert into user(username,password) values('aaa','123')
        //delete from user where username = 'aaa'
        //int i = mysqlHelper.executeNonQuery("delete from user where username = 'aaa'");
        //System.out.println(i);
        //int j = mysqlHelper.executeNonQuery("update User set password='123'");
        //System.out.println(j);
        /*List<MyTableField> fieldList = new ArrayList<MyTableField>();
        MyTableField field1 = new MyTableField();
        field1.setFieldName("username");
        field1.setFieldValue("aaa");
        fieldList.add(field1);
        MyTableField field2 = new MyTableField();
        field2.setFieldName("password");
        field2.setFieldValue("123");
        fieldList.add(field2);
        int j = mysqlHelper.insertGetID("user", fieldList);
        System.out.println(j);*/

    }
}
