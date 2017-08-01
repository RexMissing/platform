package org.whut.dataManagement.business.userFunctionRole.mapper;

import org.whut.dataManagement.business.userFunctionRole.entity.Depart;
import org.whut.dataManagement.business.userFunctionRole.entity.DepartUser;
import org.whut.dataManagement.business.userFunctionRole.entity.QueryNumber;
import org.whut.dataManagement.business.userFunctionRole.entity.RoleNo;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public interface DepartUserMapper extends AbstractMapper<DepartUser> {
    public List<DepartUser> getalllist(String departNo);
    public List<DepartUser> getlist(QueryNumber queryNumber);
    public List<DepartUser> getUser(String username);
    public void addDepart(Depart depart);
    public int checkDepartNo(String departNo);
    public int checkDepartName(String departName);
    public String findDepartNoByUser(String username);
    public int upRoleById(RoleNo roleNo);
}
