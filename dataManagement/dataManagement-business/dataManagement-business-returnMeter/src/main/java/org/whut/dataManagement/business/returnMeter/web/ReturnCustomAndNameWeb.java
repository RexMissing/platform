package org.whut.dataManagement.business.returnMeter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnCustomAndNameMapper;
import org.whut.dataManagement.business.returnMeter.service.ReturnCustomAndNameService;
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
 * Created by Administrator on 2017/8/30 0030.
 */
@Component
@Path("/returnCustomAndName")
public class ReturnCustomAndNameWeb {
    private static final PlatformLogger logger = PlatformLogger.getLogger(ReturnCustomAndNameMapper.class);

    @Autowired
    private ReturnCustomAndNameService returnCustomAndNameService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findCustomAndName")
    @POST
    public String findCustomAndName(@FormParam("fdbatchid")String fdbatchid)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fdbatchid!=null&&!fdbatchid.equals(""))
        {
            condition.put("fdbatchid",fdbatchid);
        }
        List<Map<String,Object>> list = returnCustomAndNameService.findCustomAndName(condition);
        System.out.print(list);
        return JsonResultUtils.getObjectResultByStringAsDefault(list, JsonResultUtils.Code.SUCCESS);
    }
}
