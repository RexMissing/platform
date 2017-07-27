package org.whut.dataManagement.business.userFunctionRole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.userFunctionRole.entity.UserFuncRole;
import org.whut.dataManagement.business.userFunctionRole.mapper.UserFuncRoleMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

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
}
