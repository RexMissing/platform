package org.whut.dataManagement.business.search.mapper;

import org.whut.dataManagement.business.search.entity.BoxCode;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public interface BoxCodeMapper extends AbstractMapper<BoxCode> {
    public List<Map<String,Object>> getBoxCode(Map<String,Object> condition);
}
