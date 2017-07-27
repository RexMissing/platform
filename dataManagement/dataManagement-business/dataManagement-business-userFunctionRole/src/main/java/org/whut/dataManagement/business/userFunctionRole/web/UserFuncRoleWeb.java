package org.whut.dataManagement.business.userFunctionRole.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.userFunctionRole.entity.UserFuncRole;
import org.whut.dataManagement.business.userFunctionRole.service.UserFuncRoleService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
@Component
@Path("/userfunction")
public class UserFuncRoleWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(UserFuncRoleWeb.class);
    @Autowired
    private UserFuncRoleService userFuncRoleService;
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @GET
    @Path("/currentFuncRole")
    public String getUserFuncRole(){
        String username = null;
        Object credential = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(credential instanceof UserDetails){
            UserDetails userDetails = (UserDetails) credential;
            username = userDetails.getUsername();
        }else{
            username = (String)credential;
        }
        logger.info("current user is "+username);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(username);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        List<UserFuncRole> list = userFuncRoleService.getlist(username);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }
}
