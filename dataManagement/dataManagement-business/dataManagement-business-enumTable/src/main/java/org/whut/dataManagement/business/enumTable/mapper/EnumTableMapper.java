package org.whut.dataManagement.business.enumTable.mapper;

import org.whut.dataManagement.business.enumTable.entity.EnumTable;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public interface EnumTableMapper extends AbstractMapper<EnumTable> {
    public List<Map<String,String>> getList();
    public List<Map<String,String>> getAllList();
}
