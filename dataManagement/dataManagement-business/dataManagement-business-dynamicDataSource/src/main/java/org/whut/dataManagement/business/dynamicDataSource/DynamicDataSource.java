package org.whut.dataManagement.business.dynamicDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        //从自定义的位置获取数据源标识
        return DynamicDataSourceHolder.getDataSource();
    }
}
