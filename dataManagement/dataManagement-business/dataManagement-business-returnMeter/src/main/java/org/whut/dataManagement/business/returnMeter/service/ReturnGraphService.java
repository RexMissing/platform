package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.entity.YearsProductions;
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

    public List<YearsProductions> graphReturnAccuYear(String year,String fmeternames,String minYear){
        List<YearsProductions> plist = new ArrayList<YearsProductions>();
        int temp = 0;
        for (int i = Integer.parseInt(minYear); i <= Integer.parseInt(year); i++){
            YearsProductions yearsProductions = new YearsProductions();
            yearsProductions.setYear(i);
            yearsProductions.setFmetername(fmeternames);
            temp = returnGraphMapper.graphReturnAccuYear(yearsProductions);
            yearsProductions.setProductions(temp);
            plist.add(yearsProductions);
        }
        return plist;
    }
    public List<YearsProductions> graphReturnYears(String fmeternames,String minYear,String maxYear){
        List<YearsProductions> plist = new ArrayList<YearsProductions>();
        int temp = 0;
        for (int i = Integer.parseInt(minYear); i <= Integer.parseInt(maxYear); i++){
            YearsProductions yearsProductions = new YearsProductions();
            yearsProductions.setYear(i);
            yearsProductions.setFmetername(fmeternames);
            temp = returnGraphMapper.graphReturnYears(yearsProductions);
            yearsProductions.setProductions(temp);
            plist.add(yearsProductions);
        }
        return plist;
    }
}
