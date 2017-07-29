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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String add(@FormParam("jsonString")String jsonString)
    {
        if(jsonString==null||jsonString.trim().equals("")){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能为空");
        }
        MeterAnalysis meterAnalysis = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,MeterAnalysis.class);
        if(meterAnalysis==null)
        {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"参数不能是空!");
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
        meterAnalysisService.update(meterAnalysis);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list() {
        List<Map<String,String>> list = meterAnalysisService.getList();
        if (list.toArray().length==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        System.out.print(list);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }
}
