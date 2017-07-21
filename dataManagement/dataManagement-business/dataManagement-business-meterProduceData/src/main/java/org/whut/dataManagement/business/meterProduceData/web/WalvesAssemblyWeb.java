package org.whut.dataManagement.business.meterProduceData.web;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.meterProduceData.entity.WalvesAssembly;
import org.whut.dataManagement.business.meterProduceData.service.WalvesAssemblyService;
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
@Path("/walvesassembly")
public class WalvesAssemblyWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(OCWalvesServiceWeb.class);
    @Autowired
    private WalvesAssemblyService walvesAssemblyService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/list")
    @POST
    public String list(@FormParam("fvalvecode")String fvalvecode)
    {
        if (fvalvecode == null) {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
        List<WalvesAssembly> list = walvesAssemblyService.getListByCode(fvalvecode);
        List<WalvesAssembly> walvesAssemblyList = new ArrayList<WalvesAssembly>();
        for(WalvesAssembly walvesAssembly:list)
        {
            WalvesAssembly subwalvesassembly = new WalvesAssembly();
            subwalvesassembly.setFvalvecode(walvesAssembly.getFvalvecode());
            subwalvesassembly.setFmetercode(walvesAssembly.getFmetercode());
            subwalvesassembly.setFonlycode(walvesAssembly.getFonlycode());
            walvesAssemblyList.add(subwalvesassembly);
        }
        if (walvesAssemblyList.toArray().length==0)  {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询不到结果!");
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(walvesAssemblyList,JsonResultUtils.Code.SUCCESS);
    }
}
