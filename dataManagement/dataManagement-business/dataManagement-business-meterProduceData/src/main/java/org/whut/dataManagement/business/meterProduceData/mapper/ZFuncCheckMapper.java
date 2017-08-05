package org.whut.dataManagement.business.meterProduceData.mapper;

import org.whut.dataManagement.business.meterProduceData.entity.ZFuncCheck;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
public interface ZFuncCheckMapper extends AbstractMapper<ZFuncCheck> {
    public List<ZFuncCheck> list(String fmetercode);
}
