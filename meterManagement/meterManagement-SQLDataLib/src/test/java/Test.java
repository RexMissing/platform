
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
        MysqlHelper mysqlHelper = new MysqlHelper("jdbc:mysql://127.0.0.1:3306/test?user=root&password=root");
        //insert into user(username,password) values('aaa','123')
        //delete from user where username = 'aaa'
        //int i = mysqlHelper.executeNonQuery("delete from user where username = 'aaa'");
        //System.out.println(i);
        //int j = mysqlHelper.executeNonQuery("update User set password='123'");
        //System.out.println(j);

        List<MyTableField> fieldList = new ArrayList<MyTableField>();
        MyTableField field1 = new MyTableField();
        field1.setFieldName("username");
        field1.setFieldValue("aaa");
        fieldList.add(field1);
        MyTableField field2 = new MyTableField();
        field2.setFieldName("password");
        field2.setFieldValue("123");
        fieldList.add(field2);
        int j = mysqlHelper.insertGetID("user", fieldList);
        System.out.println(j);
    }
}
