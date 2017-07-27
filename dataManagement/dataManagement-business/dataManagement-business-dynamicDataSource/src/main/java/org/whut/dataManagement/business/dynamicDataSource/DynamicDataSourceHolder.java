package org.whut.dataManagement.business.dynamicDataSource;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class DynamicDataSourceHolder {
    //数据源标识保存在线程变量中，避免多线程操作数据库互相干扰
    private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();
    public static String getDataSource()
    {
        return THREAD_DATA_SOURCE.get();
    }
    public static void setDataSource(String dataSource)
    {
        THREAD_DATA_SOURCE.set(dataSource);
    }
    public static void clearDataSource()
    {
        THREAD_DATA_SOURCE.remove();
    }
}
