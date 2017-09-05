package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.entity.MonthProductions;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnGraphMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
@DataSource("NetBarCode")
public class ReturnGraphService {
    @Autowired
    private ReturnGraphMapper returnGraphMapper;

    public List<MonthProductions> graphReturnYear(String year){
        List<MonthProductions> plist = new ArrayList<MonthProductions>();
        int temp = 0;
        for (int i = 1; i <= 12; i++){
            MonthProductions monthProductions = new MonthProductions();
            monthProductions.setYear(year);
            monthProductions.setMonth(i);
            temp = returnGraphMapper.graphReturnYear(monthProductions);
            monthProductions.setProductions(temp);
            plist.add(monthProductions);
        }
        return plist;
    }
}
