package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.entity.*;
import org.whut.dataManagement.business.returnMeter.service.ReturnGraphDataService;
import org.whut.dataManagement.business.returnMeter.service.ReturnGraphService;
import org.whut.dataManagement.business.returnMeter.service.ReturnMeterInfoService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

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
    private ReturnGraphDataService returnGraphDataService;
    @Autowired
    private ReturnMeterInfoService returnMeterInfoService;

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/xYear")
    @POST
    public String getMinYear(@FormParam("compareYear1") String compareYear1,
                             @FormParam("compareYear2") String compareYear2,
                             @FormParam("fmeternames") String fmeternames){
        Map<String,String> map1 = new HashMap<String, String>();
        Map<String,String> map2 = new HashMap<String, String>();
        map1.put("yearBegin",compareYear1 + "-01-01");
        map1.put("yearEnd",compareYear1 + "-12-31");
        map1.put("fmeternames",fmeternames);
        map2.put("yearBegin",compareYear2 + "-01-01");
        map2.put("yearEnd",compareYear2 + "-12-31");
        map2.put("fmeternames",fmeternames);
        int year1 = returnGraphDataService.getMinYear(map1);
        int year2 = returnGraphDataService.getMinYear(map2);
        int minYear = (year1 < year2) ? year1:year2;
        String sMinYear = String.valueOf(minYear);
        Map<String,String> map = new HashMap<String, String>();
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        map.put("minYear",sMinYear);
        list.add(map);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/year")
    @POST
    public String getMinYear(@FormParam("year") String year,
                             @FormParam("fmeternames") String fmeternames){
        Map<String,String> map = new HashMap<String, String>();
        map.put("yearBegin",year + "-01-01");
        map.put("yearEnd",year + "-12-31");
        map.put("fmeternames",fmeternames);
        int minYear = returnGraphDataService.getMinYear(map);
        String sMinYear = String.valueOf(minYear);
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        Map<String,String> resultmap = new HashMap<String, String>();
        resultmap.put("minYear",sMinYear);
        list.add(resultmap);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/graphYear")
    @POST
    public String getYear(@FormParam("year") String year,
                          @FormParam("fmeternames") String fmeternames,
                          @FormParam("minYear") String minYear){
        List<YearsProductions> acculist = new ArrayList<YearsProductions>();
        List<YearsReturnMeters> rlist = new ArrayList<YearsReturnMeters>();
        List<YearReturnRate> ratelist = new ArrayList<YearReturnRate>();
        acculist = returnGraphService.graphReturnAccuYear(year,fmeternames,minYear);
        rlist = returnMeterInfoService.getYearReturnMeters(year,fmeternames,minYear,year);
        double rate = 0;
        int count = Integer.parseInt(year) - Integer.parseInt(minYear);
        for (int i = 0; i <= count; i++){
            YearReturnRate yearReturnRate = new YearReturnRate();
            if (acculist.get(i).getYear() == rlist.get(i).getPyear()){
                if (acculist.get(i).getProductions() != 0){
                    rate = (double)rlist.get(i).getReturnMeters() / (double)acculist.get(i).getProductions();
                    yearReturnRate.setRate(rate);
                }
                else {
                    yearReturnRate.setRate(0);
                }
                ratelist.add(yearReturnRate);
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(ratelist, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/graphYears")
    @POST
    public String getYears(@FormParam("compareYear1") String compareYear1,
                           @FormParam("compareYear2") String compareYear2,
                           @FormParam("fmeternames") String fmeternames,
                           @FormParam("minYear") String minYear,
                           @FormParam("maxYear") String maxYear){
        List<YearsProductions> plist = new ArrayList<YearsProductions>();
        List<YearsReturnMeters> rlist1 = new ArrayList<YearsReturnMeters>();
        List<YearsReturnMeters> rlist2 = new ArrayList<YearsReturnMeters>();
        List<YearsReturnRate> ylist = new ArrayList<YearsReturnRate>();
        plist = returnGraphService.graphReturnYears(fmeternames,minYear,maxYear);
        rlist1 = returnMeterInfoService.getYearReturnMeters(compareYear1,fmeternames,minYear,maxYear);
        rlist2 = returnMeterInfoService.getYearReturnMeters(compareYear2,fmeternames,minYear,maxYear);
        double temp1 = 0;
        double temp2 = 0;
        int count = Integer.parseInt(maxYear) - Integer.parseInt(minYear);
        for (int i = 0; i <= count; i++){
            YearsReturnRate yearsReturnRate = new YearsReturnRate();
            if (plist.get(i).getYear() == rlist1.get(i).getPyear() && plist.get(i).getYear() == rlist2.get(i).getPyear()){
                yearsReturnRate.setYear(plist.get(i).getYear());
                if (plist.get(i).getProductions() != 0){
                    temp1 = (double)rlist1.get(i).getReturnMeters() / (double)plist.get(i).getProductions();
                    temp2 = (double)rlist2.get(i).getReturnMeters() / (double)plist.get(i).getProductions();
                    yearsReturnRate.setRate1(temp1);
                    yearsReturnRate.setRate2(temp2);
                }
                else{
                    yearsReturnRate.setRate1(0);
                    yearsReturnRate.setRate2(0);
                }
                ylist.add(yearsReturnRate);
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(ylist, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/graphCompareYears")
    @POST
    public String getCompareYears(@FormParam("compareYear1") String compareYear1,
                                  @FormParam("compareYear2") String compareYear2,
                                  @FormParam("fmeternames") String fmeternames,
                                  @FormParam("minYear") String minYear,
                                  @FormParam("maxYear") String maxYear){
        List<YearsReturnMeters> list1 = new ArrayList<YearsReturnMeters>();
        List<YearsReturnMeters> list2 = new ArrayList<YearsReturnMeters>();
        List<YearsCompare> result = new ArrayList<YearsCompare>();
        list1 = returnMeterInfoService.getYearReturnMeters(compareYear1,fmeternames,minYear,maxYear);
        list2 = returnMeterInfoService.getYearReturnMeters(compareYear2,fmeternames,minYear,maxYear);
        if (list1.size() == list2.size())
            for (int i = 0; i < list1.size(); i++){
                YearsCompare yearsCompare = new YearsCompare();
                if (list1.get(i).getPyear() == list2.get(i).getPyear())
                    yearsCompare.setYear(list1.get(i).getPyear());
                yearsCompare.setNumber1(list1.get(i).getReturnMeters());
                yearsCompare.setNumber2(list2.get(i).getReturnMeters());
                result.add(yearsCompare);
            }
        return JsonResultUtils.getObjectResultByStringAsDefault(result, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/graphSaleYears")
    @POST
    public String getCompareYears(@FormParam("fmeternames") String fmeternames,
                                  @FormParam("minYear") String minYear,
                                  @FormParam("maxYear") String maxYear){
        List<YearsProductions> list = new ArrayList<YearsProductions>();
        List<YearsCompare> result = new ArrayList<YearsCompare>();
        list = returnGraphService.graphReturnYears(fmeternames,minYear,maxYear);
        for (int i = 0; i < list.size(); i++){
            YearsCompare yearsCompare = new YearsCompare();
            yearsCompare.setYear(list.get(i).getYear());
            yearsCompare.setNumber1(list.get(i).getProductions());
            yearsCompare.setNumber2(list.get(i).getProductions());
            result.add(yearsCompare);
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(result, JsonResultUtils.Code.SUCCESS);
    }
}
