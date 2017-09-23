package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnByCodeMapper;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/21 0021.
 */
@DataSource("NetBarCode")
public class ReturnByCodeService {
    @Autowired
    private ReturnByCodeMapper returnByCodeMapper;
    public List<Map<String,Object>> findByCode(Map<String,Object> condition)
    {
        return returnByCodeMapper.findByCode(condition);
    }
}
