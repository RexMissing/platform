package org.whut.dataManagement.business.inspection.mapper;

import org.whut.dataManagement.business.inspection.entity.Inspection;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface InspectionAirMapper extends AbstractMapper<Inspection> {
    String findAirByCode(String fmetercode);
}
