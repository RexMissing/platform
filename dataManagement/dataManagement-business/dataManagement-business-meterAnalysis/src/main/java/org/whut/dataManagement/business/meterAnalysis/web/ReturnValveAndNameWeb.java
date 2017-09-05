package org.whut.dataManagement.business.meterAnalysis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.meterAnalysis.service.ReturnValveAndNameService;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
@Component
@Path("/returnValveAndName")
public class ReturnValveAndNameWeb {

    @Autowired
    private ReturnValveAndNameService returnValveAndNameService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findValveAndName")
    @POST
    public String findValveAndName(@FormParam("fmetercode")String fmetercode)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        List<Map<String,Object>> list = returnValveAndNameService.findValveAndName(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
