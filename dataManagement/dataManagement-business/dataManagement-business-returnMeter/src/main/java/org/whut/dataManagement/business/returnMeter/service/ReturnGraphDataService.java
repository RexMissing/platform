package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnGraphMapper;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8.
 */
@DataSource("datamanagement")
public class ReturnGraphDataService {
    @Autowired
    private ReturnGraphMapper returnGraphMapper;

    public int getMinYear(Map<String,String> conditions){
        Date date = returnGraphMapper.getMinYear(conditions);
        if (date == null)
            return 9999;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }
}
