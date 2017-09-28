package org.whut.dataManagement.business.userFunctionRole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.userFunctionRole.entity.Depart;
import org.whut.dataManagement.business.userFunctionRole.entity.DepartUser;
import org.whut.dataManagement.business.userFunctionRole.entity.QueryNumber;
import org.whut.dataManagement.business.userFunctionRole.entity.RoleNo;
import org.whut.dataManagement.business.userFunctionRole.mapper.DepartUserMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@DataSource("datamanagement")
public class DepartUserService {
    @Autowired
    DepartUserMapper departUserMapper;
    public List<DepartUser> getalllist(String departNo){
        return departUserMapper.getalllist(departNo);
    }
    public List<DepartUser> getlist(QueryNumber queryNumber){
        return departUserMapper.getlist(queryNumber);
    }
    public List<DepartUser> getUser(String username){return departUserMapper.getUser(username);}
    public void addDepart(Depart depart){
        departUserMapper.addDepart(depart);
    }
    public int checkDepartNo(String departNo){
        return departUserMapper.checkDepartNo(departNo);
    }
    public int checkDepartName(String departName){
        return departUserMapper.checkDepartName(departName);
    }
    public String findDepartNoByUser(String username){return departUserMapper.findDepartNoByUser(username);}
    public int upRoleById(RoleNo roleNo){
        return departUserMapper.upRoleById(roleNo);
    }
}
