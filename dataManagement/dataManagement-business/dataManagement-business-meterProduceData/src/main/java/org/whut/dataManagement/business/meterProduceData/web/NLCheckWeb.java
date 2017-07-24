package org.whut.dataManagement.business.meterProduceData.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.meterProduceData.entity.NLCheck;
import org.whut.dataManagement.business.meterProduceData.service.NLCheckService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@Component
@Path("/nlcheck")
public class NLCheckWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(OCWalvesServiceWeb.class);
    @Autowired
    private NLCheckService nlCheckService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/list")
    public String list(@FormParam("fmetercode")String fmetercode){
        if (fmetercode == null) {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        List<NLCheck> list = nlCheckService.list(fmetercode);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
