package org.whut.dataManagement.business.meterAnalysis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.meterAnalysis.entity.MeterAnalysis;
import org.whut.dataManagement.business.meterAnalysis.service.MeterAnalysisService;
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
 * Created by Administrator on 2017/7/27 0027.
 */
@Component
@Path("/meterAnalysis")
public class MeterAnalysisWeb {
    @Autowired
    private MeterAnalysisService meterAnalysisService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("jsonString")String jsonString)throws ParseException
    {
        if(jsonString==null||jsonString.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能为空");
        }
        MeterAnalysis meterAnalysis = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,MeterAnalysis.class);
        if(meterAnalysis==null)
        {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能是空!");
        }
        meterAnalysis.setFdatetime(new java.sql.Date(System.currentTimeMillis()));
        int flag = 0;
        flag = meterAnalysisService.ifSamecode(meterAnalysis);
        if (flag == 1){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"同一天分析表号相同!");
        }
        meterAnalysisService.add(meterAnalysis);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString")String jsonString) {
        MeterAnalysis meterAnalysis = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,MeterAnalysis.class);
        int result = meterAnalysisService.delete(meterAnalysis);
        if(result>0)
        {
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
        MeterAnalysis meterAnalysis = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,MeterAnalysis.class);
        if(meterAnalysis==null)
        {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能是空!");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("fmetercode",meterAnalysis.getFmetercode());
        List<Map<String,String>> list = meterAnalysisService.findValveAndName(map);
        if (list.size()==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "修改的表具编号未清点!");
        }
        meterAnalysis.setFmetername(list.get(0).get("fmetername"));
        meterAnalysis.setFvalvename(list.get(0).get("fvalvecode"));
        meterAnalysisService.update(meterAnalysis);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list(@FormParam("curFuncRole") String curFuncRole,@FormParam("fanalysor") String fanalysor) {
        List<MeterAnalysis> list;
        if (curFuncRole.equals("1") || curFuncRole.equals("2")||curFuncRole.equals("3")){
            list = meterAnalysisService.getList();
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else if (curFuncRole.equals("4")){
            list = meterAnalysisService.getListByFanalysor(fanalysor);
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else{
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "权限不够!");
        }
    }

    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("/getCusByCode")
    @POST
    public String getCusByCode(@FormParam("fmetercode") String fmetercode){
        String cus = meterAnalysisService.getCusByCode(fmetercode);
        String reportMis = meterAnalysisService.getReportMisByCode(fmetercode);
        String meterreading = meterAnalysisService.getReadingByCode(fmetercode);
        Map<String, String> mapcus = new HashMap<String, String>();
        Map<String, String> mapmis = new HashMap<String, String>();
        Map<String, String> mapreading = new HashMap<String, String>();
        mapcus.put("fcustomer",cus);
        mapmis.put("freportmisfune",reportMis);
        mapreading.put("fmeterreading",meterreading);
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        list.add(mapcus);
        list.add(mapmis);
        list.add(mapreading);
        if (list.toArray().length==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }


    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByCondition")
    @POST
    public String findByCondition(@FormParam("fmetercode")String fmetercode,@FormParam("fmetername")String fmetername,@FormParam("fdepartment")String fdepartment,
                                  @FormParam("fanalysor")String fanalysor, @FormParam("fcustomer")String fcustomer,@FormParam("freportmisfune")String freportmisfune,@FormParam("fconfirmmisfune")String fconfirmmisfune,
                                  @FormParam("fmisfunedescrib")String fmisfunedescrib, @FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        if(fmetername!=null&&!fmetername.equals(""))
        {
            condition.put("fmetername",fmetername);
        }
        if(fdepartment!=null&&!fdepartment.equals(""))
        {
            condition.put("fdepartment",fdepartment);
        }
        if(fanalysor!=null&&!fanalysor.equals(""))
        {
            condition.put("fanalysor",fanalysor);
        }
        if(fcustomer!=null&&!fcustomer.equals(""))
        {
            condition.put("fcustomer",fcustomer);
        }
        if(freportmisfune!=null&&!freportmisfune.equals(""))
        {
            condition.put("freportmisfune",freportmisfune);
        }
        if(fconfirmmisfune!=null&&!fconfirmmisfune.equals(""))
        {
            condition.put("fconfirmmisfune",fconfirmmisfune);
        }
        if(fmisfunedescrib!=null&&!fmisfunedescrib.equals(""))
        {
            condition.put("fmisfunedescrib",fmisfunedescrib);
        }
        if(sTime!=null&&!sTime.equals("")){
            condition.put("startTime",sTime+" 00:00:00");
        }
        if(eTime!=null&&!eTime.equals("")){
            condition.put("endTime",eTime+" 23:59:59");
        }
        List<Map<String,Object>> list = meterAnalysisService.findByCondition(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findBySearch")
    @POST
    public String findBySearch(@FormParam("fmetercode")String fmetercode,
                               @FormParam("fmetername")String fmetername,
                               @FormParam("curFuncRole") String curFuncRole,
                               @FormParam("fanalysor") String fanalysor)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        if(fmetername!=null&&!fmetername.equals(""))
        {
            condition.put("fmetername",fmetername);
        }
        if (curFuncRole.equals("4")){
            condition.put("fanalysor",fanalysor);
        }
        List<MeterAnalysis> meterlist = meterAnalysisService.findBySearch(condition);
        if (meterlist.size()==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(meterlist,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findValveAndName")
    @POST
    public String findValveAndName(@FormParam("fmetercode")String fmetercode)
    {
        Map<String,String> condition = new HashMap<String, String>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        List<Map<String,String>> list = meterAnalysisService.findValveAndName(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

}
