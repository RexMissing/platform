package org.whut.dataManagement.business.meterAnalysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterAnalysis.entity.MeterAnalysis;
import org.whut.dataManagement.business.meterAnalysis.mapper.MeterAnalysisMapper;
import org.whut.dataManagement.business.meterAnalysis.mapper.ProductAnalysisMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/8.
 */
@DataSource("datamanagement")
public class ProductAnalysisService {
    @Autowired
    private ProductAnalysisMapper productAnalysisMapper;
    public int ifSamecode(MeterAnalysis meterAnalysis){
        return productAnalysisMapper.ifSamecode(meterAnalysis);
    }
    public void add(MeterAnalysis meterAnalysis) {
        productAnalysisMapper.add(meterAnalysis);
    }
    public int delete(MeterAnalysis meterAnalysis) {
        return productAnalysisMapper.delete(meterAnalysis);
    }
    public void update(MeterAnalysis meterAnalysis) {
        productAnalysisMapper.update(meterAnalysis) ;
    }
    public List<MeterAnalysis> getList() {
        return productAnalysisMapper.getList();
    }
    public List<MeterAnalysis> getListByFanalysor(String fanalysor) {
        return productAnalysisMapper.getListByFanalysor(fanalysor);
    }
    public List<MeterAnalysis> findBySearch(Map<String,Object> condition)
    {
        return productAnalysisMapper.findBySearch(condition);
    }
}
