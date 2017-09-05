package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.entity.MonthProductions;
import org.whut.dataManagement.business.returnMeter.entity.MonthReturnMeters;
import org.whut.dataManagement.business.returnMeter.entity.MonthReturnRate;
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
                //monthReturnRate.setRate((double)(i + 1)/(double)(i + 10));
            }
            else {
                monthReturnRate.setRate(0);
            }
            ratelist.add(monthReturnRate);
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(ratelist, JsonResultUtils.Code.SUCCESS);
    }

}
