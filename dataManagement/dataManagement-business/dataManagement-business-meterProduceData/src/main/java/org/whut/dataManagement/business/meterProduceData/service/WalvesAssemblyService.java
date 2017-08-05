package org.whut.dataManagement.business.meterProduceData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterProduceData.entity.WalvesAssembly;
import org.whut.dataManagement.business.meterProduceData.mapper.WalvesAssemblyMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
@DataSource("NetBarCode")
public class WalvesAssemblyService {
    @Autowired
    private WalvesAssemblyMapper walvesAssemblyMapper;
    public List<WalvesAssembly> getListByCode(String fvalvecode)
    {
        if(fvalvecode==null || fvalvecode.trim().equals("")){
            return null;
        }
        List<WalvesAssembly> list = walvesAssemblyMapper.getListByCode(fvalvecode);
        return list;
    }
}
