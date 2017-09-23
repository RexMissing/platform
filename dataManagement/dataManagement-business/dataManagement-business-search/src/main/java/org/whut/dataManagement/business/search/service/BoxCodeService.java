package org.whut.dataManagement.business.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.search.entity.BoxCode;
import org.whut.dataManagement.business.search.mapper.BoxCodeMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
@DataSource("NetBarCode")
public class BoxCodeService{
    @Autowired
    private BoxCodeMapper boxCodeMapper;

    public List<Map<String, Object>> getBoxCode(Map<String, Object> condition) {
        return boxCodeMapper.getBoxCode(condition);
    }
}
