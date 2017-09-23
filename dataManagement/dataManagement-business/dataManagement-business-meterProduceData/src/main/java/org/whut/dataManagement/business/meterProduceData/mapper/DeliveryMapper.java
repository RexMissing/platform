package org.whut.dataManagement.business.meterProduceData.mapper;

import javax.ws.rs.FormParam;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
public interface DeliveryMapper {
    public List<Map<String,Object>> findByCondition(@FormParam("fmetercode")String fmetercode);
}
