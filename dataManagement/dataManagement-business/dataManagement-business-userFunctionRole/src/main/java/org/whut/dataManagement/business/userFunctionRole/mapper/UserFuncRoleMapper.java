package org.whut.dataManagement.business.userFunctionRole.mapper;

import org.whut.dataManagement.business.userFunctionRole.entity.*;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public interface UserFuncRoleMapper extends AbstractMapper<UserFuncRole>{
    public List<UserFuncRole> getlist(String username);
    public List<Depart> getalldepart();
    public List<FuncRole> getallrole(int role);
    public void adduser(FUser fuser);
    public long getIdByName(String username);
    public void adduserAuthority(UserAuth userAuth);
}
