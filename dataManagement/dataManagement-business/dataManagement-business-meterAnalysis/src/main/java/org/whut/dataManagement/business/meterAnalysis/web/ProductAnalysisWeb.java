package org.whut.dataManagement.business.meterAnalysis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.meterAnalysis.entity.MeterAnalysis;
import org.whut.dataManagement.business.meterAnalysis.service.MeterAnalysisService;
import org.whut.dataManagement.business.meterAnalysis.service.ProductAnalysisService;
import org.whut.dataManagement.business.meterAnalysis.service.ReturnValveAndNameService;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/8.
 */
@Component
@Path("/productAnalysis")
public class ProductAnalysisWeb {
    @Autowired
    private ProductAnalysisService productAnalysisService;
    @Autowired
    private ReturnValveAndNameService returnValveAndNameService;

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
        flag = productAnalysisService.ifSamecode(meterAnalysis);
        if (flag == 1){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"同一天分析表号相同!");
        }
        productAnalysisService.add(meterAnalysis);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list(@FormParam("curFuncRole") String curFuncRole,@FormParam("fanalysor") String fanalysor) {
        List<MeterAnalysis> list;
        if (curFuncRole.equals("1") || curFuncRole.equals("2")||curFuncRole.equals("3")){
            list = productAnalysisService.getList();
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else if (curFuncRole.equals("4")){
            list = productAnalysisService.getListByFanalysor(fanalysor);
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else{
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "权限不够!");
        }
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString")String jsonString) {
        MeterAnalysis meterAnalysis = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,MeterAnalysis.class);
        int result = productAnalysisService.delete(meterAnalysis);
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fmetercode",meterAnalysis.getFmetercode());
        List<Map<String,Object>> list = returnValveAndNameService.findValveAndName(map);
        if (list.size()==0)  {
            meterAnalysis.setFmetername("");
            meterAnalysis.setFvalvename("");
            productAnalysisService.update(meterAnalysis);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "未找到修改的表具编号!");
        }
        meterAnalysis.setFmetername(list.get(0).get("fmetername").toString());
        meterAnalysis.setFvalvename(list.get(0).get("fvalvecode").toString());
        productAnalysisService.update(meterAnalysis);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
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
        List<MeterAnalysis> meterlist = productAnalysisService.findBySearch(condition);
        if (meterlist.size()==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(meterlist,JsonResultUtils.Code.SUCCESS);
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
        List<Map<String,Object>> list = productAnalysisService.findByCondition(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
