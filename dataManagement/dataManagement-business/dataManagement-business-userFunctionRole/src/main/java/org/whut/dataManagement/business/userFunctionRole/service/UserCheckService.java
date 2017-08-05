package org.whut.dataManagement.business.userFunctionRole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.userFunctionRole.mapper.UserCheckMapper;

/**
 * Created by Administrator on 2017/7/27.
 */
public class UserCheckService {
    @Autowired
    private UserCheckMapper userCheckMapper;

    public int usernameCheck(String name){
        if(name == null || name.trim().equals("")){
            return 0;
        }
        else
            return userCheckMapper.usernameCheck(name);
    }
}
