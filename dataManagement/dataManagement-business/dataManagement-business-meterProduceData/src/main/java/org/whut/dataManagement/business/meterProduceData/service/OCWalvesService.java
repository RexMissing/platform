package org.whut.dataManagement.business.meterProduceData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterProduceData.entity.OCWalves;
import org.whut.dataManagement.business.meterProduceData.mapper.OCWalvesMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
@DataSource("NetBarCode")
public class OCWalvesService{
    @Autowired
    private OCWalvesMapper ocWalvesMapper;
    public List<OCWalves> getListByCode(String fvalvecode)
    {
        if(fvalvecode==null || fvalvecode.trim().equals("")){
            return null;
        }
        List<OCWalves> list = ocWalvesMapper.getListByCode(fvalvecode);
        return list;
    }

}
