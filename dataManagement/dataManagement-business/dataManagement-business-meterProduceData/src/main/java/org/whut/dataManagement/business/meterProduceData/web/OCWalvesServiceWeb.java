package org.whut.dataManagement.business.meterProduceData.web;

import org.apache.ibatis.annotations.Param;
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
    @Path("/list")
    @POST
    public String list(@FormParam("fvalvecode")String fvalvecode)
    {
        if (fvalvecode == null) {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        List<OCWalves> list = ocWalvesService.getListByCode(fvalvecode);
        List<OCWalves> ocWalvesList = new ArrayList<OCWalves>();
        for(OCWalves ocWalves:list)
        {
            OCWalves subocwalves = new OCWalves();
            subocwalves.setFvalvecode(ocWalves.getFvalvecode());
            subocwalves.setFpressure(ocWalves.getFpressure());
            subocwalves.setFrecord(ocWalves.getFrecord());
            subocwalves.setFstate(ocWalves.getFstate());
            ocWalvesList.add(subocwalves);
        }
        if (ocWalvesList.toArray().length==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(ocWalvesList,JsonResultUtils.Code.SUCCESS);
    }
}
