package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.entity.*;
import org.whut.dataManagement.business.returnMeter.service.ReturnGraphService;
import org.whut.dataManagement.business.returnMeter.service.ReturnMeterInfoService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
@Component
@Path("/graph")
public class ReturnGraphWeb {
    private static final PlatformLogger logger = PlatformLogger.getLogger(ReturnGraphWeb.class);
    @Autowired
    private ReturnGraphService returnGraphService;
    @Autowired
    private ReturnMeterInfoService returnMeterInfoService;

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/graphYear")
    @POST
    public String getYear(@FormParam("year") String year){
        List<MonthProductions> plist = new ArrayList<MonthProductions>();
        List<MonthReturnMeters> rlist = new ArrayList<MonthReturnMeters>();
        List<MonthReturnRate> ratelist = new ArrayList<MonthReturnRate>();
        plist = returnGraphService.graphReturnYear(year);
        rlist = returnMeterInfoService.getMonthReturnMeters(year);
        double monthRate = 0;
        for (int i = 0; i < plist.size(); i++){
            MonthReturnRate monthReturnRate = new MonthReturnRate();
            monthReturnRate.setMonth(i + 1);
            if (plist.get(i).getProductions() != 0){
                monthRate = (double)rlist.get(i).getReturnMeters() / (double)plist.get(i).getProductions();
                monthReturnRate.setRate(monthRate);
            }
            else {
                monthReturnRate.setRate(0);
            }
            ratelist.add(monthReturnRate);
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(ratelist, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/graphYears")
    @POST
    public String getYears(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<MonthProductions> plist = new ArrayList<MonthProductions>();
        List<MonthReturnMeters> rlist = new ArrayList<MonthReturnMeters>();
        List<YearReturnRate> ylist = new ArrayList<YearReturnRate>();
        plist = returnGraphService.graphReturnYears(Integer.toString(year));
        rlist = returnMeterInfoService.getYearReturnMeters(Integer.toString(year));
        double temp = 0;
        for (int i = 0; i < 10; i++){
            YearReturnRate yearReturnRate = new YearReturnRate();
            if (plist.get(i).getYear().equals(rlist.get(i).getYear())){
                yearReturnRate.setYear(plist.get(i).getYear());
                if (plist.get(i).getProductions() != 0){
                    temp = (double)rlist.get(i).getReturnMeters() / (double)plist.get(i).getProductions();
                    yearReturnRate.setRate(temp);
                }
                else
                    yearReturnRate.setRate(0);
                ylist.add(yearReturnRate);
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(ylist, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/graphCompareYears")
    @POST
    public String getCompareYears(@FormParam("year1")String year1,@FormParam("year2")String year2){
        List<MonthReturnMeters> list1 = new ArrayList<MonthReturnMeters>();
        List<MonthReturnMeters> list2 = new ArrayList<MonthReturnMeters>();
        List<YearsCompare> result = new ArrayList<YearsCompare>();
        list1 = returnMeterInfoService.getMonthReturnMeters(year1);
        list2 = returnMeterInfoService.getMonthReturnMeters(year2);
        if (list1.size() == list2.size())
            for (int i = 0; i < list1.size(); i++){
                YearsCompare yearsCompare = new YearsCompare();
                yearsCompare.setMonth(i + 1);
                yearsCompare.setNumber1(list1.get(i).getReturnMeters());
                yearsCompare.setNumber2(list2.get(i).getReturnMeters());
                result.add(yearsCompare);
            }
        return JsonResultUtils.getObjectResultByStringAsDefault(result, JsonResultUtils.Code.SUCCESS);
    }
}
