package org.whut.dataManagement.business.meterAnalysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterAnalysis.entity.MeterAnalysis;
import org.whut.dataManagement.business.meterAnalysis.mapper.MeterAnalysisMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
@DataSource("datamanagement")
public class MeterAnalysisService {
    @Autowired
    private MeterAnalysisMapper meterAnalysisMapper;
    public void add(MeterAnalysis meterAnalysis) {
        meterAnalysisMapper.add(meterAnalysis);
    }

    public void update(MeterAnalysis meterAnalysis) {
        meterAnalysisMapper.update(meterAnalysis) ;
    }

    public int delete(MeterAnalysis meterAnalysis) {
        return meterAnalysisMapper.delete(meterAnalysis);
    }

    public List<Map<String,String>> getList() {
        return meterAnalysisMapper.getList();
    }
    public List<Map<String,String>> getConfirmList()
    {
        return meterAnalysisMapper.getConfirmList();
    }
 }
