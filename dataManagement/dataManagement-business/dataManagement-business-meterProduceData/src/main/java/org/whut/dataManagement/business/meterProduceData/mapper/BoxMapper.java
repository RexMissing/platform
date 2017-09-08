package org.whut.dataManagement.business.meterProduceData.mapper;

import javax.ws.rs.FormParam;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public interface BoxMapper {
    public List<Map<String,Object>> findByCondition(@FormParam("fmetercode")String fmetercode);
}
