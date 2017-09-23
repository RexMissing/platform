package org.whut.dataManagement.business.updateRecord.mapper;

import org.whut.dataManagement.business.updateRecord.entity.UpdateRecord;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
public interface UpdateRecordMapper extends AbstractMapper<UpdateRecord> {
    public List<Map<String,Object>> findByCondition(Map<String,Object> condition);
}
