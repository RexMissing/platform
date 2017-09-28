package org.whut.dataManagement.business.userFunctionRole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.userFunctionRole.entity.UserDepartNo;
import org.whut.dataManagement.business.userFunctionRole.entity.UserPassword;
import org.whut.dataManagement.business.userFunctionRole.mapper.UserPasswordMapper;
import org.whut.platform.business.user.security.MD5Encoder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
@DataSource("datamanagement")
public class UserPasswordService {
    @Autowired
    private UserPasswordMapper userPasswordMapper;

    public int updatePassword(UserPassword userPassword){
        if(userPassword.getUsername()==null || userPassword.getUsername().trim().equals("")){
            return 0;
        }
        else if(userPassword.getPassword()!=null && !userPassword.getPassword().equals("")){
            userPassword.setPassword(MD5Encoder.GetMD5Code(userPassword.getPassword()));
            return userPasswordMapper.updatePassword(userPassword);
        }
        else
            return 0;

    }

    public List<UserDepartNo> findDepartNoByUsername(String up_username){
        if(up_username == null || up_username.trim().equals("")){
            return null;
        }
        else {
            return userPasswordMapper.getDepartNo(up_username);
        }
    }
}
