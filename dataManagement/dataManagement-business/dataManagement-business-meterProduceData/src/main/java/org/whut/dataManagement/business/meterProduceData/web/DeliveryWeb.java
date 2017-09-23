package org.whut.dataManagement.business.meterProduceData.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.meterProduceData.service.DeliveryService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@Component
@Path("/delivery")
public class DeliveryWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(DeliveryWeb.class);
    @Autowired
    private DeliveryService deliveryService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/findByCondition")
    public String findByCondition(@FormParam("fmetercode")String fmetercode)
    {
        if (fmetercode==null||fmetercode.trim().equals(""))
        {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        List<Map<String,Object>> list = deliveryService.findByCondition(fmetercode);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
