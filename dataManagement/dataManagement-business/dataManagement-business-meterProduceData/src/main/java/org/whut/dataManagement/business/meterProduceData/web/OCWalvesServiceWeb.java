package org.whut.dataManagement.business.meterProduceData.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.meterProduceData.entity.OCWalves;
import org.whut.dataManagement.business.meterProduceData.service.OCWalvesService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/20 0020.
 */
@Component
@Path("/ocwalves")
public class OCWalvesServiceWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(OCWalvesServiceWeb.class);
    @Autowired
    private OCWalvesService ocWalvesService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("list/{fvalvecode}")
    @GET
    public String list(@PathParam("fvalvecode")String fvalvecode)
    {
        if (fvalvecode == null) {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        List list;
        try {
            list = ocWalvesService.getListByCode(fvalvecode);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage());
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
