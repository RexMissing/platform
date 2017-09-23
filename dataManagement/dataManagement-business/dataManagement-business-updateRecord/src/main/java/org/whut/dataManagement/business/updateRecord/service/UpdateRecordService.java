package org.whut.dataManagement.business.updateRecord.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.updateRecord.entity.UpdateRecord;
import org.whut.dataManagement.business.updateRecord.mapper.UpdateRecordMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
@DataSource("datamanagement")
public class UpdateRecordService {
    @Autowired
    private UpdateRecordMapper updateRecordMapper;
    public void add(UpdateRecord updateRecord) {
       updateRecordMapper.add(updateRecord);
    }
    public List<Map<String,Object>> findByCondition(Map<String,Object> condition)
    {
        return updateRecordMapper.findByCondition(condition);
    }
}
