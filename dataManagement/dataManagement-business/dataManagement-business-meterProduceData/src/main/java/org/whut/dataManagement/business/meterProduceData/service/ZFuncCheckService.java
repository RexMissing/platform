package org.whut.dataManagement.business.meterProduceData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterProduceData.entity.ZFuncCheck;
import org.whut.dataManagement.business.meterProduceData.mapper.ZFuncCheckMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
@DataSource("NetBarCode")
public class ZFuncCheckService {
    @Autowired
    private ZFuncCheckMapper zFuncCheckMapper;

    public List<ZFuncCheck> list(String fmetercode){
        if(fmetercode==null || fmetercode.trim().equals("")){
            return null;
        }
        else
            return zFuncCheckMapper.list(fmetercode);
    }
}
