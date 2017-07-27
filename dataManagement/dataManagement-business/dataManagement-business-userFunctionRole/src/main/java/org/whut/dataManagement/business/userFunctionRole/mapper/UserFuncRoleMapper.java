package org.whut.dataManagement.business.userFunctionRole.mapper;

import org.whut.dataManagement.business.userFunctionRole.entity.UserFuncRole;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public interface UserFuncRoleMapper extends AbstractMapper<UserFuncRole>{
    public List<UserFuncRole> getlist(String username);
}
