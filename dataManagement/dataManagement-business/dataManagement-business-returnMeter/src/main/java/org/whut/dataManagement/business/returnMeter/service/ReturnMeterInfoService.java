package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.entity.MonthReturnMeters;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeterInfo;
import org.whut.dataManagement.business.returnMeter.entity.StaQuery;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterInfoMapper;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
@DataSource("datamanagement")
public class ReturnMeterInfoService{
    @Autowired
    private ReturnMeterInfoMapper returnMeterInfoMapper;

    public void add(ReturnMeterInfo returnMeterInfo) {
        returnMeterInfoMapper.add(returnMeterInfo);
    }

    public void update(ReturnMeterInfo returnMeterInfo) {
         returnMeterInfoMapper.update(returnMeterInfo);
    }

    public int delete(ReturnMeterInfo returnMeterInfo) {
        return returnMeterInfoMapper.delete(returnMeterInfo);
    }

    public List<ReturnMeterInfo> getlist() {
        return returnMeterInfoMapper.getlist();
    }
    public List<Map<String,Object>> findByCondition(Map<String,Object> condition)
    {
        return returnMeterInfoMapper.findByCondition(condition);
    }
    public List<Map<String,Object>> findBySearch(Map<String,Object> condition)
    {
        return returnMeterInfoMapper.findBySearch(condition);
    }

    public List<ReturnMeterInfo> getstalist(StaQuery staQuery) {
        if (staQuery.getQuerytype().equals("无"))
            return returnMeterInfoMapper.getstalist(staQuery);
        else if (staQuery.getQuerytype().equals("录入人员"))
            return returnMeterInfoMapper.getinlist(staQuery);
        else if (staQuery.getQuerytype().equals("市场"))
            return returnMeterInfoMapper.getcomlist(staQuery);
        else if (staQuery.getQuerytype().equals("配置规格"))
            return returnMeterInfoMapper.gettypelist(staQuery);
        else if (staQuery.getQuerytype().equals("退库单据号"))
            return returnMeterInfoMapper.getretlist(staQuery);
        else
            return null;
    }

    public List<MonthReturnMeters> getMonthReturnMeters(String year){
        List<MonthReturnMeters> rlist = new ArrayList<MonthReturnMeters>();
        int temp = 0;
        for (int i = 1; i <= 12; i++){
            MonthReturnMeters monthReturnMeters = new MonthReturnMeters();
            monthReturnMeters.setYear(year);
            monthReturnMeters.setMonth(i);
            temp = returnMeterInfoMapper.getMonthReturnMeters(monthReturnMeters);
            monthReturnMeters.setReturnMeters(temp);
            rlist.add(monthReturnMeters);
        }
        return rlist;
    }

    public List<MonthReturnMeters> getYearReturnMeters(String year){
        List<MonthReturnMeters> rlist = new ArrayList<MonthReturnMeters>();
        int temp = 0;
        for (int i = 0; i < 10; i++){
            MonthReturnMeters monthReturnMeters = new MonthReturnMeters();
            monthReturnMeters.setYear(Integer.toString(Integer.parseInt(year) - i));
            monthReturnMeters.setMonth(0);
            temp = returnMeterInfoMapper.getYearReturnMeters(monthReturnMeters);
            monthReturnMeters.setReturnMeters(temp);
            rlist.add(monthReturnMeters);
        }
        return rlist;
    }
}
