package org.whut.dataManagement.business.meterProduceData.mapper;

import org.whut.dataManagement.business.meterProduceData.entity.NLCheck;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface NLCheckMapper extends AbstractMapper<NLCheck> {
    public List<NLCheck> list(String fmetercode);
}
