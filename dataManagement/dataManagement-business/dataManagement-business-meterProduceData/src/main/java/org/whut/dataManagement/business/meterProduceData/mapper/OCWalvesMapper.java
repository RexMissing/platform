package org.whut.dataManagement.business.meterProduceData.mapper;

import org.whut.dataManagement.business.meterProduceData.entity.OCWalves;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public interface OCWalvesMapper extends AbstractMapper<OCWalves> {
    public List<OCWalves> getListByCode(String fvalvecode);

}
