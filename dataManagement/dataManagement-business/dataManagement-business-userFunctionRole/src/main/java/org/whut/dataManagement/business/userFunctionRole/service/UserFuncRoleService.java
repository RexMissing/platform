package org.whut.dataManagement.business.userFunctionRole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.userFunctionRole.entity.*;
import org.whut.dataManagement.business.userFunctionRole.mapper.UserFuncRoleMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
@DataSource("datamanagement")
public class UserFuncRoleService {
    @Autowired
    private UserFuncRoleMapper userFuncRoleMapper;

    public List<UserFuncRole> getlist(String username){
        if(username==null || username.trim().equals("")){
            return null;
        }
        else{
            List<UserFuncRole> list = userFuncRoleMapper.getlist(username);
            return list;
        }
    }

    public List<Depart> getalldepart(){
        return userFuncRoleMapper.getalldepart();
    }

    public List<FuncRole> getallrole(int role){
        if(role != 1 && role != 2){
            return null;
        }
        else{
            return userFuncRoleMapper.getallrole(1);
        }
    }

    public void adduser(FUser fuser){
        userFuncRoleMapper.adduser(fuser);
    }

    public long getIdByName(String username){
        return userFuncRoleMapper.getIdByName(username);
    }
    public void adduserAuthorith(UserAuth userAuth){
        userFuncRoleMapper.adduserAuthority(userAuth);
    }
    public int getRoleByTd(long id){return userFuncRoleMapper.getRoleById(id);}

}
