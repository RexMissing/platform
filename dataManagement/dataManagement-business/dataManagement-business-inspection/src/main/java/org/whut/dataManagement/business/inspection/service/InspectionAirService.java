package org.whut.dataManagement.business.inspection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.inspection.mapper.InspectionAirMapper;

/**
 * Created by Administrator on 2017/10/11.
 */
@DataSource("NetBarCode")
public class InspectionAirService {
    @Autowired
    private InspectionAirMapper inspectionAirMapper;
    public String findAirByCode(String fmetercode){
        return inspectionAirMapper.findAirByCode(fmetercode);
    }
}
