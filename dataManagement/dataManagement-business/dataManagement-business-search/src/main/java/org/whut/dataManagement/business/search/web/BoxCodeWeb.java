package org.whut.dataManagement.business.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.search.entity.BoxCode;
import org.whut.dataManagement.business.search.service.BoxCodeService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonMapper;
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
 * Created by Administrator on 2017/8/3 0003.
 */
@Component
@Path("/boxCodeSearch")
public class BoxCodeWeb {
    private static final PlatformLogger logger = PlatformLogger.getLogger(BoxCodeWeb.class);

    @Autowired
    private BoxCodeService boxCodeService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findByCondition")
    @POST
    public String findByCondition(@FormParam("fmetercode")String fmetercode)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fmetercode!=null&&!fmetercode.equals(""))
        {
            condition.put("fmetercode",fmetercode);
        }
        List<Map<String,Object>> list = boxCodeService.getBoxCode(condition);
        System.out.print(list);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
