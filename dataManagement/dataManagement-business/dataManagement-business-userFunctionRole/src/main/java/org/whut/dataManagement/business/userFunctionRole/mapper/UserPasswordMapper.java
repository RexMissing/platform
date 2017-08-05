package org.whut.dataManagement.business.userFunctionRole.mapper;

import org.whut.dataManagement.business.userFunctionRole.entity.UserDepartNo;
import org.whut.dataManagement.business.userFunctionRole.entity.UserPassword;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public interface UserPasswordMapper extends AbstractMapper<UserPassword> {
    public int updatePassword(UserPassword userPassword);
    public List<UserDepartNo> getDepartNo(String up_username);
}
