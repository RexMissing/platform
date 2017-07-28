package org.whut.dataManagement.business.userFunctionRole.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.userFunctionRole.entity.*;
import org.whut.dataManagement.business.userFunctionRole.service.UserFuncRoleService;
import org.whut.platform.business.user.security.MD5Encoder;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
        List<UserFuncRole> list = userFuncRoleService.getlist(username);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/listdepart")
    public String listdepart(){
        List<Depart> list = userFuncRoleService.getalldepart();
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/listrole")
    public String getRoleList(@FormParam("role") int role){
        List<FuncRole> resultlist = new ArrayList<FuncRole>();
        List<FuncRole> list = userFuncRoleService.getallrole(role);
        if(role == 2){
            resultlist = list;
        }
        else if (role == 1){
            FuncRole funcRole = new FuncRole();
            funcRole.setFuncRole(1);
            funcRole.setFuncName("系统管理员");
            resultlist.add(funcRole);
            for(FuncRole flist : list){
                FuncRole tmp = new FuncRole();
                tmp.setFuncRole(flist.getFuncRole());
                tmp.setFuncName(flist.getFuncName());
                resultlist.add(tmp);
            }
        }
        return JsonResultUtils.getObjectResultByStringAsDefault(resultlist,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @POST
    @Path("/adduser")
    public String adduser(@FormParam("name")String name,
                          @FormParam("password")String password,
                          @FormParam("sex")String sex,
                          @FormParam("userrole")int funcrole,
                          @FormParam("depart")String departNo,
                          @FormParam("fname")String fname)
    {
        FUser fuser = new FUser();
        String image = null;
        fuser.setUsername(name);
        fuser.setPassword(MD5Encoder.GetMD5Code(password));
        fuser.setSex(sex);
        fuser.setRole("ROLE_USER");
        fuser.setAppid(1);
        fuser.setStatus("启用");
        fuser.setImage(image);
        fuser.setFuncrole(funcrole);
        fuser.setDepartNo(departNo);
        fuser.setFname(fname);
        userFuncRoleService.adduser(fuser);
        long userid = userFuncRoleService.getIdByName(name);
        UserAuth userAuth = new UserAuth();
        userAuth.setUserid(userid);
        userAuth.setAuthid(1);
        userAuth.setUsername(name);
        userAuth.setAythorityName("ROLE_USER");
        userFuncRoleService.adduserAuthorith(userAuth);
        return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(), "添加成功!");
    }
}
