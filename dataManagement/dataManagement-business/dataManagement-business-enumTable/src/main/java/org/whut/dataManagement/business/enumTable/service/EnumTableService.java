package org.whut.dataManagement.business.enumTable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.enumTable.entity.EnumTable;
import org.whut.dataManagement.business.enumTable.mapper.EnumTableMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
@DataSource("datamanagement")
public class EnumTableService {
    @Autowired
    private EnumTableMapper enumTableMapper;

    public List<Map<String, String>> getMisList() {
        return enumTableMapper.getMisList();
    }
    public List<Map<String, String>> getMisInfoList(String misType) {
        return enumTableMapper.getMisInfoList(misType);
    }
    public List<Map<String, String>> getMisInfoAllList() {
        return enumTableMapper.getMisInfoAllList();
    }
    public List<Map<String, String>> getCusList() {
        return enumTableMapper.getCusList();
    }
    public List<Map<String, String>> getEleList() {
        return enumTableMapper.getEleList();
    }
    public List<Map<String, String>> getMeterList() {
        return enumTableMapper.getMeterList();
    }

    public List<Map<String, String>> getAllList() {
        return enumTableMapper.getAllList();
    }

    public void add(EnumTable enumTable) {
        enumTableMapper.add(enumTable);
    }

    public int update(EnumTable enumTable) {
        return enumTableMapper.update(enumTable);
    }

    public int delete(EnumTable enumTable) {
        return enumTableMapper.delete(enumTable);
    }

    public List<Map<String, Object>> findBySearch(Map<String, Object> condition)
    {
        return enumTableMapper.findBySearch(condition);
    }
}
