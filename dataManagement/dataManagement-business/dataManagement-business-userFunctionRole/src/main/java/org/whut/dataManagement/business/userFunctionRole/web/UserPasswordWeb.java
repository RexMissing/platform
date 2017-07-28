package org.whut.dataManagement.business.userFunctionRole.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.userFunctionRole.entity.UserDepartNo;
import org.whut.dataManagement.business.userFunctionRole.entity.UserPassword;
import org.whut.dataManagement.business.userFunctionRole.service.UserPasswordService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
@Component
@Path("/updatepassword")
public class UserPasswordWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(UserFuncRoleWeb.class);
    @Autowired
    private UserPasswordService userPasswordService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/update")
    public String updatePassword(@FormParam("jsonString") String jsonString){
        UserPassword userPassword = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,UserPassword.class);
        int count = userPasswordService.updatePassword(userPassword);
        if(count == 1)
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS);
        else
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "修改失败！");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/checkDepartNo")
    public String findDepartNoByUsername(@FormParam("up_username") String up_username){
        List<UserDepartNo> list = userPasswordService.findDepartNoByUsername(up_username);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

}
