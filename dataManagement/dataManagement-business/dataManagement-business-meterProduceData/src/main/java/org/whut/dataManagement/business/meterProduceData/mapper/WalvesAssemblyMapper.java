package org.whut.dataManagement.business.meterProduceData.mapper;

import org.whut.dataManagement.business.meterProduceData.entity.WalvesAssembly;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
public interface WalvesAssemblyMapper extends AbstractMapper<WalvesAssembly> {
    public List<WalvesAssembly> getListByCode(String fvalvecode);
}
