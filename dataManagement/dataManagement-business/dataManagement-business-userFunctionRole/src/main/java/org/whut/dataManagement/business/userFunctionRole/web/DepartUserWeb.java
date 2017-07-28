package org.whut.dataManagement.business.userFunctionRole.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.dataManagement.business.userFunctionRole.entity.Depart;
import org.whut.dataManagement.business.userFunctionRole.entity.DepartUser;
import org.whut.dataManagement.business.userFunctionRole.entity.QueryNumber;
import org.whut.dataManagement.business.userFunctionRole.entity.UserDepartNo;
import org.whut.dataManagement.business.userFunctionRole.service.DepartUserService;
import org.whut.platform.fundamental.logger.PlatformLogger;
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

}
