package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeterInfo;
import org.whut.dataManagement.business.returnMeter.entity.StaQuery;
import org.whut.dataManagement.business.returnMeter.service.ReturnMeterInfoService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
@Component
@Path("/returnMeterInfo")
public class ReturnMeterInfoWeb {
    private static final PlatformLogger logger = PlatformLogger.getLogger(ReturnMeterInfoWeb.class);
    @Autowired
    private ReturnMeterInfoService returnMeterInfoService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("jsonString") String jsonString)throws ParseException {
        if(jsonString==null||jsonString.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能为空");
        }
        ReturnMeterInfo returnMeterInfo = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,ReturnMeterInfo.class);
        if(returnMeterInfo==null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能是空!");
        }
        returnMeterInfoService.add(returnMeterInfo);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString")String jsonString) {
        ReturnMeterInfo returnMeterInfo = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,ReturnMeterInfo.class);
        int result=returnMeterInfoService.delete(returnMeterInfo);
        if(result>0){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS) ;
        }
        else {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/update")
    @POST
    public String update(@FormParam("jsonString")String jsonString) {
        if (jsonString == null || jsonString.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能是空!");
        }
        ReturnMeterInfo returnMeterInfo = JsonMapper.buildNonDefaultMapper().fromJson(jsonString, ReturnMeterInfo.class);
        if (returnMeterInfo == null) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能是空!");
        }
        returnMeterInfoService.update(returnMeterInfo);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list() {
        List<ReturnMeterInfo> list = returnMeterInfoService.getlist();
//        List<ReturnMeterInfo> returnMeterInfoList= new ArrayList<ReturnMeterInfo>();
//        for(ReturnMeterInfo returnMeterInfo:list)
//        {
//            ReturnMeterInfo subreturnMeterInfo = new ReturnMeterInfo();
//            subreturnMeterInfo.setFmetercode(returnMeterInfo.getFmetercode());
//            subreturnMeterInfo.setFcustomer(returnMeterInfo.getFcustomer());
//            subreturnMeterInfo.setFmetername(returnMeterInfo.getFmetername());
//            subreturnMeterInfo.setFquantity(returnMeterInfo.getFquantity());
//            subreturnMeterInfo.setFdatetime(returnMeterInfo.getFdatetime());
//            subreturnMeterInfo.setFoperator(returnMeterInfo.getFoperator());
//            returnMeterInfoList.add(subreturnMeterInfo);
//        }
//        if (returnMeterInfoList.toArray().length==0)  {
//            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
//        }
//        return JsonResultUtils.getObjectResultByStringAsDefault(returnMeterInfoList, JsonResultUtils.Code.SUCCESS);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByCondition")
    @POST
    public String findByCondition(@FormParam("foperator")String foperator,@FormParam("fcustomer")String fcustomer,@FormParam("fmetername")String fmetername,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(foperator!=null&&!foperator.equals(""))
        {
            condition.put("foperator",foperator);
        }
        if (fcustomer!=null&&!fcustomer.equals(""))
        {
            condition.put("fcustomer",fcustomer);
        }
        if (fmetername!=null&&!fmetername.equals(""))
        {
            condition.put("fmetername",fmetername);
        }
        if(sTime!=null&&!sTime.equals("")){
            condition.put("startTime",sTime+" 00:00:00");
        }
        if(eTime!=null&&!eTime.equals("")){
            condition.put("endTime",eTime+" 59:59:59");
        }
        List<Map<String,Object>> list=returnMeterInfoService.findByCondition(condition);
        System.out.print(list);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findBySearch")
    @POST
    public String findBySearch(@FormParam("fmetercode")String fmetercode,@FormParam("fmetername")String fmetername)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        if (fmetername!=null&&!fmetername.equals(""))
        {
            condition.put("fmetername",fmetername);
        }
        List<Map<String,Object>> list = returnMeterInfoService.findBySearch(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/stalist")
    @POST
    public String stalist(@FormParam("querytype")String querytype, @FormParam("queryvalue")String queryvalue, @FormParam("begindate")String begindate, @FormParam("enddate")String enddate) {
        StaQuery staQuery = new StaQuery();
        staQuery.setQuerytype(querytype);
        staQuery.setBegindate(begindate);
        staQuery.setEnddate(enddate);
        staQuery.setQueryvalue(queryvalue);
        List<ReturnMeterInfo> list = returnMeterInfoService.getstalist(staQuery);
        List<ReturnMeterInfo> returnMeterInfoList= new ArrayList<ReturnMeterInfo>();
        for(ReturnMeterInfo returnMeterInfo:list)
        {
            ReturnMeterInfo subreturnMeterInfo = new ReturnMeterInfo();
            subreturnMeterInfo.setFmetercode(returnMeterInfo.getFmetercode());
            subreturnMeterInfo.setFcustomer(returnMeterInfo.getFcustomer());
            subreturnMeterInfo.setFmetername(returnMeterInfo.getFmetername());
            subreturnMeterInfo.setFquantity(returnMeterInfo.getFquantity());
            subreturnMeterInfo.setFdatetime(returnMeterInfo.getFdatetime());
            subreturnMeterInfo.setFoperator(returnMeterInfo.getFoperator());
            returnMeterInfoList.add(subreturnMeterInfo);
        }
        if (returnMeterInfoList.toArray().length==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        else
            return JsonResultUtils.getObjectResultByStringAsDefault(returnMeterInfoList, JsonResultUtils.Code.SUCCESS);
    }
}
