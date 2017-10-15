package org.whut.dataManagement.business.inspection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.inspection.entity.Inspection;
import org.whut.dataManagement.business.inspection.mapper.InspectionMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */
@DataSource("datamanagement")
public class InspectionService {
    @Autowired
    private InspectionMapper inspectionMapper;
    public void add(Inspection inspection) {
        inspectionMapper.add(inspection);
    }
    public List<Map<String,Object>> list() {
        return inspectionMapper.getlist();
    }
    public List<Map<String,Object>> findLastBatch()
    {
        return inspectionMapper.findLastBatch();
    }
    public List<Map<String,Object>> getBatchlist()
    {
        return inspectionMapper.getBatchlist();
    }
    public List<Map<String,Object>> findBySearch(Map<String,Object> condition)
    {
        return inspectionMapper.findBySearch(condition);
    }
    public List<Map<String,Object>> findByfoperator(String foperator) {
        return inspectionMapper.findByfoperator(foperator);
    }
    public int delete(Inspection inspection) {
        return inspectionMapper.delete(inspection);
    }
    public List<Map<String,Object>> getAllBatchList()
    {
        return inspectionMapper.getAllBatchList();
    }
    public List<Map<String,Object>> findAllBySearch(Map<String,Object> condition)
    {
        return inspectionMapper.findAllBySearch(condition);
    }
}
