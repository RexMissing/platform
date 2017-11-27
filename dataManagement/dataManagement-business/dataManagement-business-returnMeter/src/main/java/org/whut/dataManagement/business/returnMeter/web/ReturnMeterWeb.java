package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.dataManagement.business.returnMeter.service.ConfirmCodeService;
import org.whut.dataManagement.business.returnMeter.service.DispatchService;
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
import java.text.SimpleDateFormat;
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
    @Autowired
    private DispatchService dispatchService;
    @Autowired
    private ConfirmCodeService confirmCodeService;

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
        //添加同一批次不能有同一表号的判断
        int flag = 0;
        flag = returnMeterService.ifSamecode(returnMeter);
        if (flag == 1){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"同一批次中表号相同!");
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
        if (list.size() == 0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "表具编号修改错误!");
        }
        else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String datestr = null;
            Date date = new Date();
            if(returnMeter.getFmetercode().length()==12){
                datestr = "20" + returnMeter.getFmetercode().substring(3,5) + "-" + returnMeter.getFmetercode().substring(5,7) + "-" + "01";
            }
            else if(returnMeter.getFmetercode().length()==13){
                datestr = "20" + returnMeter.getFmetercode().substring(4,6) + "-" + returnMeter.getFmetercode().substring(6,8) + "-" + "01";
            }
            else {
                datestr = null;
            }
            try {
                date = sdf.parse(datestr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            returnMeter.setFproducetime(new java.sql.Date(date.getTime()));
            returnMeter.setFmetername(list.get(0).get("fmetername").toString());
            returnMeter.setFvalvecode(list.get(0).get("fvalvecode").toString());
            returnMeter.setFmeterdirection(list.get(0).get("fmeterdirection").toString());
            returnMeterService.update(returnMeter);
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list(@FormParam("curFuncRole") String curFuncRole,@FormParam("fanalysor") String fanalysor) {
        List<Map<String,Object>> list;
        if (curFuncRole.equals("1")||curFuncRole.equals("2")||curFuncRole.equals("3")){
            list=returnMeterService.list();
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else if (curFuncRole.equals("4")){
            list = returnMeterService.findByfanalysor(fanalysor);
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"查询不到结果！");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getBatchlist")
    @POST
    public String getBatchlist() {
        List<Map<String,Object>> list = returnMeterService.getBatchlist();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getAllBatchlist")
    @POST
    public String getAllBatchlist() {
        List<Map<String,Object>> list = returnMeterService.getAllBatchlist();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByCondition")
    @POST
    public String findByCondition(@FormParam("freturnbatch")String freturnbatch,@FormParam("fmetercode")String fmetercode,@FormParam("foperator")String foperator,@FormParam("fcustomer")String fcustomer,@FormParam("fmetername")String fmetername,@FormParam("fmeterdirection")String fmeterdirection,@FormParam("freportmisfune")String freportmisfune,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(freturnbatch!=null&&!freturnbatch.equals(""))
        {
            condition.put("freturnbatch",freturnbatch);
        }
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
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
        if (fmeterdirection!=null&&!fmeterdirection.equals(""))
        {
            condition.put("fmeterdirection",fmeterdirection);
        }
        if (freportmisfune!=null&&!freportmisfune.equals(""))
        {
            condition.put("freportmisfune",freportmisfune);
        }
        if(sTime!=null&&!sTime.equals("")){
            condition.put("startTime",sTime+" 00:00:00");
        }
        if(eTime!=null&&!eTime.equals("")){
            condition.put("endTime",eTime+" 23:59:59");
        }
        List<Map<String,Object>> list=returnMeterService.findByCondition(condition);
        System.out.print(list);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findBySearch")
    @POST
    public String findBySearch(@FormParam("curFuncRole") String curFuncRole,@FormParam("fanalysor") String fanalysor,@FormParam("freturnbatch")String freturnbatch,@FormParam("fmetercode")String fmetercode,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
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
            condition.put("endTime",eTime+" 23:59:59");
        }
        if (curFuncRole.equals("4")){
            condition.put("fanalysor",fanalysor);
        }
        List<Map<String,Object>> list = returnMeterService.findBySearch(condition);
        if (list.size()==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
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
            condition.put("endTime",eTime+" 23:59:59");
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

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByDispatch")
    @POST
    public String findByDispatch(@FormParam("fmetercode")String fmetercode)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        List<Map<String,Object>> list=dispatchService.findByDispatch(condition);
        if (list.size()==0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到预报故障!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/returnMeterNum")
    @POST
    public String returnMeterNum(@FormParam("fcustomer")String fcustomer,@FormParam("fmetername")String fmetername,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fcustomer!=null&&!fcustomer.equals(""))
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
        List<Map<String,Object>> list1=returnMeterService.returnMeterNum(condition);
        List<Map<String,Object>> list2=returnMeterService.returnAnalysisNum(condition);
        int num = Integer.parseInt(list1.get(0).get("returnmeternum").toString())-Integer.parseInt(list2.get(0).get("analysisnum").toString());
        Map<String,Object> condition1 = new HashMap<String, Object>();
        condition1.put("notanalysisnum",num);
        condition1.put("returnbatchnum",list1.get(0).get("returnbatchnum"));
        condition1.put("returnmeternum",list1.get(0).get("returnmeternum"));
        condition1.put("analysisnum",list2.get(0).get("analysisnum"));
        List<Map<String,Object>> list3 = new ArrayList<Map<String, Object>>();
        list3.add(condition1);
        return JsonResultUtils.getObjectResultByStringAsDefault(list3,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/confirmCode")
    @POST
    public String confirmCode(@FormParam("fmetercodemark")String fmetercodemark)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercodemark!=null&&!fmetercodemark.equals(""))
        {
            condition.put("fmetercodemark",fmetercodemark);
        }
        List<Map<String,Object>> list=confirmCodeService.confirmFMeterCode(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getReturnNum")
    @POST
    public String getReturnNum(@FormParam("freturnbatch")String freturnbatch)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(freturnbatch!=null&&!freturnbatch.equals(""))
        {
            condition.put("freturnbatch",freturnbatch);
        }
        List<Map<String,Object>> list=returnMeterService.getReturnNum(condition);
        System.out.print(list);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }



//    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
//    @Path("/returnAnalysisNum")
//    @POST
//    public String returnAnalysisNum(@FormParam("fcustomer")String fcustomer,@FormParam("fmetername")String fmetername,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
//    {
//        Map<String,Object> condition = new HashMap<String, Object>();
//        if(fcustomer!=null&&!fcustomer.equals(""))
//        {
//            condition.put("fcustomer",fcustomer);
//        }
//        if (fmetername!=null&&!fmetername.equals(""))
//        {
//            condition.put("fmetername",fmetername);
//        }
//        if(sTime!=null&&!sTime.equals("")){
//            condition.put("startTime",sTime+" 00:00:00");
//        }
//        if(eTime!=null&&!eTime.equals("")){
//            condition.put("endTime",eTime+" 59:59:59");
//        }
//        List<Map<String,Object>> list=returnMeterService.returnAnalysisNum(condition);
//        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
//    }
}
