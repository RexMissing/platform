package org.whut.dataManagement.business.returnMeter.mapper;

import org.whut.dataManagement.business.returnMeter.entity.YearsProductions;

import java.sql.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/5.
 */
public interface ReturnGraphMapper {
    int graphReturnAccuYear(YearsProductions yearsProductions);
    int graphReturnYears(YearsProductions yearsProductions);
    Date getMinYear(Map<String,String> conditions);
}
