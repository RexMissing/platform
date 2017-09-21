package org.whut.dataManagement.business.enumTable.mapper;

import org.whut.dataManagement.business.enumTable.entity.EnumTable;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public interface EnumTableMapper extends AbstractMapper<EnumTable> {
    List<Map<String,String>> getMisList();
    List<Map<String,String>> getMisInfoList();
    List<Map<String,String>> getCusList();
    List<Map<String,String>> getEleList();
//    List<Map<String,String>> getMeterList();
    List<Map<String,String>> getAllList();
    List<Map<String,Object>> findBySearch(Map<String,Object> condition);
}
