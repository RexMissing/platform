package org.whut.dataManagement.business.inspection.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.inspection.entity.Inspection;
import org.whut.dataManagement.business.inspection.service.InspectionAirService;
import org.whut.dataManagement.business.inspection.service.InspectionService;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by Administrator on 2017/10/10.
 */
@Component
@Path("/inspection")
public class InspectionWeb {
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private InspectionAirService inspectionAirService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(
            @FormParam("finspectionbatch") String finspectionbatch,
            @FormParam("fmetercode") String fmetercode,
            @FormParam("fair") String fgastightness,
            @FormParam("foperator") String foperator,
            @FormParam("departName") String fdepartment
    ) {
        Inspection inspection = new Inspection();
        inspection.setFinspectionbatch(finspectionbatch);
        inspection.setFmetercode(fmetercode);
        inspection.setFdepartment(fdepartment);
        inspection.setFoperator(foperator);
        Date date = new Date();
        inspection.setFdate(new java.sql.Date(date.getTime()));
        inspection.setFexterior("1");
        inspection.setFvoltage(4.8);
        inspection.setFgas(2.9);
        inspection.setFmisoperation("1");
        inspection.setFgascontrol("1");
        inspection.setFdatakeep("1");
        inspection.setFgasaccumulated(5.9);
        inspection.setFexteriorprotect(4.8);
        inspection.setFdefend("1");
        inspection.setFmdisturbance("1");
        double maxelec = (int)(20 * Math.random() + 30);
        inspection.setFmaxelec(maxelec);
        if(fgastightness == null || fgastightness.equals(""))
            inspection.setFgastightness(null);
        inspection.setFgastightness(fgastightness);
        inspection.setFairtightness("1");
        double switcherror = (int)(20 * Math.random() + 10);
        inspection.setFswitcherror(switcherror);
        inspectionService.add(inspection);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list(@FormParam("curFuncRole") String curFuncRole,@FormParam("foperator") String foperator) {
        List<Map<String,Object>> list;
        if (curFuncRole.equals("1")||curFuncRole.equals("2")||curFuncRole.equals("3")){
            list=inspectionService.list();
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else if (curFuncRole.equals("4")){
            list = inspectionService.findByfoperator(foperator);
            if (list.toArray().length==0)  {
                return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
            }
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"查询不到结果！");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findLastBatch")
    @POST
    public String findLastData() {
        List<Map<String,Object>> list = inspectionService.findLastBatch();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getBatchlist")
    @POST
    public String getBatchlist() {
        List<Map<String,Object>> list = inspectionService.getBatchlist();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findBySearch")
    @POST
    public String findBySearch(@FormParam("curFuncRole") String curFuncRole,@FormParam("foperator") String foperator,@FormParam("finspectionbatch")String finspectionbatch,@FormParam("fmetercode")String fmetercode,@FormParam("sTime")String sTime,@FormParam("eTime")String eTime)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(finspectionbatch!=null&&!finspectionbatch.equals(""))
        {
            condition.put("finspectionbatch",finspectionbatch);
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
            condition.put("foperator",foperator);
        }
        List<Map<String,Object>> list = inspectionService.findBySearch(condition);
        if (list.size()==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString")String jsonString) {
        Inspection inspection = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,Inspection.class);
        int result=inspectionService.delete(inspection);
        if(result>0){
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS) ;
        }
        else {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findAirByCode")
    @POST
    public String findAirByCode(@FormParam("fmetercode") String fmetercode){
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        String fair = inspectionAirService.findAirByCode(fmetercode);
        if (fair == null || fair.equals(""))
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        Map<String, String> fairmap = new HashMap<String, String>();
        fairmap.put("fair",fair);
        list.add(fairmap);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }
}
