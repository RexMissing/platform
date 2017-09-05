package org.whut.dataManagement.business.userFunctionRole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.userFunctionRole.entity.Depart;
import org.whut.dataManagement.business.userFunctionRole.mapper.DepartMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10 0010.
 */
@DataSource("datamanagement")
public class DepartService{
    @Autowired
    private DepartMapper departMapper;
    public List<Map<String,String>> getList()
    {
        return departMapper.getList();
    }

    public List<Map<String, Object>> findDepart(String departNo) {
        return departMapper.findDepart(departNo);
    }

    public void add(Depart depart) {
        departMapper.add(depart);
    }

    public int update(Depart depart) {
        return departMapper.update(depart);
    }

    public int delete(Depart depart) {
        return departMapper.delete(depart);
    }
}
