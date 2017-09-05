package org.whut.dataManagement.business.userFunctionRole.mapper;

import org.whut.dataManagement.business.userFunctionRole.entity.Depart;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import javax.ws.rs.FormParam;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10 0010.
 */
public interface DepartMapper extends AbstractMapper<Depart> {
    public List<Map<String,String>> getList();
    public List<Map<String,Object>> findDepart(@FormParam("departNo")String departNo);
}
