package org.whut.dataManagement.business.userFunctionRole.web;

import org.jvnet.hk2.annotations.ContractsProvided;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.userFunctionRole.service.UserCheckService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2017/7/27.
 */
@Component
@Path("/userCheck")
public class UserCheckWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(UserCheckWeb.class);
    @Autowired
    private UserCheckService userCheckService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/checkname")
    @POST
    public String UsernameCheck(@FormParam("name")String name){
        int count = userCheckService.usernameCheck(name);
        if(count == 0)
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        else
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "用户已存在！");
    }
}
