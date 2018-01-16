package org.whut.dataManagement.business.userFunctionRole.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.userFunctionRole.entity.*;
import org.whut.dataManagement.business.userFunctionRole.service.DepartUserService;
import org.whut.platform.fundamental.logger.PlatformLogger;
import org.whut.platform.fundamental.util.json.JsonMapper;
import org.whut.platform.fundamental.util.json.JsonResultUtils;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Component
@Path("/depart")
public class DepartUserWeb {
    private static PlatformLogger logger = PlatformLogger.getLogger(DepartUserWeb.class);
    @Autowired
    DepartUserService departUserService;

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/alluser")
    @POST
    public String getlist(@FormParam("departNo")String departNo,@FormParam("funcrole") int funcrole){
        if(funcrole == 0){
            List<DepartUser> list = departUserService.getalllist(departNo);
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else if(funcrole == 1 || funcrole == 2 || funcrole == 3 || funcrole == 4){
            QueryNumber queryNumber = new QueryNumber();
            queryNumber.setDepartNo(departNo);
            queryNumber.setFuncrole(funcrole);
            List<DepartUser> list = departUserService.getlist(queryNumber);
            return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
        }
        else
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询错误！");
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/departuser")
    @POST
    public String getDepartUser(@FormParam("departNo")String departNo){
        List<DepartUser> list = departUserService.getalllist(departNo);
        return JsonResultUtils.getObjectResultByStringAsDefault(list,JsonResultUtils.Code.SUCCESS);
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/findUser")
    @POST
    public String findDepartNoByUser(@FormParam("userNo")String userNo,@FormParam("departNo")String departNo){
        String queryDepartNo = departUserService.findDepartNoByUser(userNo);
        if (queryDepartNo.equals(departNo)){
            List<DepartUser> departUser = null;
            departUser = departUserService.getUser(userNo);
            return JsonResultUtils.getObjectResultByStringAsDefault(departUser,JsonResultUtils.Code.SUCCESS);
        }
        else
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(), "查询错误！");

    }


    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/add")
    @POST
    public String add(@FormParam("departNo") String departNo,@FormParam("departName") String departName){
        Depart depart = new Depart();
        depart.setDepartNo(departNo);
        depart.setDepartName(departName);
        if (departNo == null || departName == null || departNo.trim().equals("") || departName.trim().equals(""))
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"添加错误！");
        else {
            departUserService.addDepart(depart);
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"添加成功！");
        }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/checkNo")
    @POST
    public String checkDepartNo(@FormParam("departNo") String departNo){
        int count = departUserService.checkDepartNo(departNo);
        if(count == 0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"部门编号可用！");
        }
        else {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"部门编号不可用！");
        }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/checkName")
    @POST
    public String checkDepartName(@FormParam("departName") String departName){
        int count = departUserService.checkDepartName(departName);
        if(count == 0){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"部门名称可用！");
        }
        else {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"部门名称不可用！");
        }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/uprole")
    @POST
    public String upRoleById(@FormParam("upuserid") long userid,@FormParam("upfuncrole") int funcrole){
        RoleNo roleNo = new RoleNo();
        roleNo.setUserid(userid);
        roleNo.setFuncRole(funcrole);
        int count = departUserService.upRoleById(roleNo);
        if(count == 1){
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.SUCCESS.getCode(),"修改成功！");
        }
        else {
            return JsonResultUtils.getCodeAndMesByString(JsonResultUtils.Code.ERROR.getCode(),"修改失败！");
        }
    }

    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    @Path("/delete")
    @POST
    public String delete(@FormParam("jsonString")String jsonString) {
        DepartUser departUser = JsonMapper.buildNonDefaultMapper().fromJson(jsonString,DepartUser.class);
        int result = departUserService.delete(departUser);
        if(result>0)
        {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.SUCCESS) ;
        }
        else {
            return JsonResultUtils.getCodeAndMesByStringAsDefault(JsonResultUtils.Code.ERROR);
        }
    }


}
