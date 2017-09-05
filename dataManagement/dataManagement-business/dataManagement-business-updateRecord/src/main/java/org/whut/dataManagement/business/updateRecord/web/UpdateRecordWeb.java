package org.whut.dataManagement.business.updateRecord.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.updateRecord.entity.UpdateRecord;
import org.whut.dataManagement.business.updateRecord.service.UpdateRecordService;
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
 * Created by Administrator on 2017/8/14 0014.
 */
@Component
@Path("/updateRecord")
public class UpdateRecordWeb {
    @Autowired
    private UpdateRecordService updateRecordService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("jsonString") String jsonString)throws ParseException {
        if (jsonString == null || jsonString.trim().equals("")) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能为空");
        }
         UpdateRecord updateRecord = JsonMapper.buildNonDefaultMapper().fromJson(jsonString, UpdateRecord.class);
        if (updateRecord == null) {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "参数不能是空!");
        }
        updateRecordService.add(updateRecord);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByCondition")
    @POST
    public String findByCondition(@FormParam("dataname") String dataname){
        Map<String,Object> condition = new HashMap<String, Object>();
        if(dataname!=null&&!dataname.equals(""))
        {
            condition.put("dataname",dataname);
        }
        List<Map<String,Object>> list = updateRecordService.findByCondition(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
