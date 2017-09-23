package org.whut.dataManagement.business.returnMeter.mapper;

import org.whut.dataManagement.business.returnMeter.entity.MonthProductions;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public interface ReturnGraphMapper {
    int graphReturnYear(MonthProductions monthProductions);
    int graphReturnYears(MonthProductions monthProductions);
}
