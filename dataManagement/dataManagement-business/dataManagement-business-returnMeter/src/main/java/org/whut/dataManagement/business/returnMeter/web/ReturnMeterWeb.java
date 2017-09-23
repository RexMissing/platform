package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.dataManagement.business.returnMeter.service.ReturnByCodeService;
import org.whut.dataManagement.business.returnMeter.service.ReturnMeterService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 2017/7/24 0024.
 */
@Component
@Path("/returnMeter")
public class ReturnMeterWeb {
    private static final PlatformLogger logger = PlatformLogger.getLogger(ReturnMeterWeb.class);
    @Autowired
    private ReturnMeterService returnMeterService;
    @Autowired
    private ReturnByCodeService returnByCodeService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("jsonString") String jsonString)throws ParseException
    {
        if(jsonString==null||jsonString.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能为空");
        }
        ReturnMeter returnMeter = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,ReturnMeter.class);
        if(returnMeter==null){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能是空!");
        }
        returnMeterService.add(returnMeter);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString")String jsonString) {
        ReturnMeter returnMeter = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,ReturnMeter.class);
        int result=returnMeterService.delete(returnMeter);
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
        ReturnMeter returnMeter = JsonMapper.buildNonDefaultMapper().fromJson(jsonString, ReturnMeter.class);
        if (returnMeter.getFcustomer() == null||returnMeter.getFdatetime()==null) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能是空!");
        }
        Map<String,Object> condition = new HashMap<String, Object>();
        if(returnMeter.getFmetercode()!=null&&!returnMeter.getFmetercode().equals(""))
        {
            condition.put("fmetercode",returnMeter.getFmetercode());
        }
        List<Map<String,Object>> list = returnByCodeService.findByCode(condition);
        System.out.print(list.get(0).get("fmetername").toString()+list.get(0).get("fmeterdirection").toString());
        returnMeter.setFmetername(list.get(0).get("fmetername").toString());
        returnMeter.setFcustomer(list.get(0).get("fcustomer").toString());
        returnMeter.setFmeterdirection(list.get(0).get("fmeterdirection").toString());
        returnMeterService.update(returnMeter);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list() {
        List<Map<String,Object>> list = returnMeterService.list();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getBatchlist")
    @POST
    public String getBatchlist() {
        List<Map<String,Object>> list = returnMeterService.getBatchlist();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
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
        List<Map<String,Object>> list=returnMeterService.findByCondition(condition);
        System.out.print(list);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findBySearch")
    @POST
    public String findBySearch(@FormParam("freturnbatch")String freturnbatch,@FormParam("fmetercode")String fmetercode,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(freturnbatch!=null&&!freturnbatch.equals(""))
        {
            condition.put("freturnbatch",freturnbatch);
        }
        if (fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        if(sTime!=null&&!sTime.equals("")){
            condition.put("startTime",sTime+" 00:00:00");
        }
        if(eTime!=null&&!eTime.equals("")){
            condition.put("endTime",eTime+" 59:59:59");
        }
        List<Map<String,Object>> list = returnMeterService.findBySearch(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByMap")
    @POST
    public String findByMap(@FormParam("foperator")String foperator,@FormParam("fcustomer")String fcustomer,@FormParam("fmetername")String fmetername,
                            @FormParam("frinvono")String frinvono,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
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
        if (frinvono!=null&&!frinvono.equals(""))
        {
            condition.put("frinvono",frinvono);
        }
        if(sTime!=null&&!sTime.equals("")){
            condition.put("startTime",sTime+" 00:00:00");
        }
        if(eTime!=null&&!eTime.equals("")){
            condition.put("endTime",eTime+" 59:59:59");
        }
        List<Map<String,Object>> list = returnMeterService.findByMap(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findLastData")
    @POST
    public String findLastData() {
       List<Map<String,Object>> list = returnMeterService.findLastData();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
