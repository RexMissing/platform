package org.whut.dataManagement.business.search.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.search.entity.BoxBatch;
import org.whut.dataManagement.business.search.service.BoxBatchService;
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
 * Created by Administrator on 2017/8/7 0007.
 */
@Component
@Path("/boxBatch")
public class BoxBatchWeb {
    private static final PlatformLogger logger = PlatformLogger.getLogger(BoxBatchWeb.class);
    @Autowired
    private BoxBatchService boxBatchService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/getBoxBatchInfo")
    @POST
    public String getBoxBatchInfo(@FormParam("fboxcode") String fboxcode)
    {
        Map<String,Object> condition = new HashMap<String, Object>();
        if(fboxcode!=null&&!fboxcode.equals(""))
        {
            condition.put("fboxcode",fboxcode);
        }
        List<Map<String,Object>> list = boxBatchService.getBatchInfo(condition);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
