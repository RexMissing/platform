package org.whut.dataManagement.business.meterProduceData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterProduceData.entity.NLCheck;
import org.whut.dataManagement.business.meterProduceData.mapper.NLCheckMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@DataSource("NetBarCode")
public class NLCheckService {
    @Autowired
    private NLCheckMapper nlCheckMapper;

    public List<NLCheck> list(String fmetercode){
        if(fmetercode==null || fmetercode.trim().equals("")){
            return null;
        }
        else
            return nlCheckMapper.list(fmetercode);
    }
}
