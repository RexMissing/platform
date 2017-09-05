package org.whut.dataManagement.business.enumTable.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.enumTable.entity.EnumTable;
import org.whut.dataManagement.business.enumTable.service.EnumTableService;
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
 * Created by Administrator on 2017/7/27 0027.
 */
@Component
@Path("/enumTable")
public class EnumTableWeb {
    @Autowired
    private EnumTableService enumTableService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("jsonString") String jsonString)throws ParseException {
        if (jsonString == null || jsonString.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空");
        }
        EnumTable enumTable = JsonMapper.buildNonDefaultMapper().fromJson(jsonString, EnumTable.class);
        if (enumTable == null) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能是空!");
        }
        enumTableService.add(enumTable);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString")String jsonString) {
        EnumTable enumTable = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,EnumTable.class);
        int result = enumTableService.delete(enumTable);
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
        EnumTable enumTable = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,EnumTable.class);
        if(enumTable==null)
        {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能是空!");
        }
        enumTableService.update(enumTable);
        return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/alllist")
    @POST
    public String alllist()
    {
        List<Map<String,String>> alllist = enumTableService.getAllList();
        if(alllist.toArray().length==0)
        {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(alllist, JsonResultUtils.Code.SUCCESS);
    }


    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list()
    {
        List<Map<String,String>> list = enumTableService.getList();
        if (list.toArray().length==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findBySearch")
    @POST
    public String findBySearch(@FormParam("fenumname")String fenumname,@FormParam("fenumtype") String fenumtype,@FormParam("fenumvalue") int fenumvalue)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fenumname!=null&&!fenumname.equals(""))
        {
            condition.put("fenumname",fenumname);
        }
        if(fenumtype!=null&&!fenumtype.equals(""))
        {
            condition.put("fenumtype",fenumtype);
        }
        condition.put("fenumvalue",fenumvalue);
        List<Map<String,Object>> list = enumTableService.findBySearch(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
