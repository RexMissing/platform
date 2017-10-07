package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnCustomAndNameMapper;
import org.whut.dataManagement.business.returnMeter.service.ReturnByCodeService;
import org.whut.platform.fundamental.logger.PlatformLogger;
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
 * Created by Administrator on 2017/9/21 0021.
 */
@Component
@Path("/returnByCode")
public class ReturnByCodeWeb {
    private static final PlatformLogger logger = PlatformLogger.getLogger(ReturnByCodeWeb.class);
    @Autowired
    private ReturnByCodeService returnByCodeService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByCode")
    @POST
    public String findByCode(@FormParam("fmetercode")String fmetercode)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        List<Map<String,Object>> list = returnByCodeService.findByCode(condition);
        System.out.print(list);
        if (list.size()==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到该表具编号!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }
}
