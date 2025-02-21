package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.dao.PerformanceDao;
import com.example.entity.Performance;
import java.util.List;

@Stateless
public class PerformanceServiceBean {

    @Inject
    private PerformanceDao performanceDao;

    public void addPerformance(Performance performance) {
        performanceDao.add(performance);
    }

    public void updatePerformance(Performance performance) {
        performanceDao.update(performance);
    }

    public void removePerformance(String id) {
        performanceDao.removeById(id);
    }

    public boolean hasPerformance(String id) {
        return performanceDao.has(id);
    }

    public List<Performance> getAllPerformances() {
        return performanceDao.getAll();
    }

    public List<Performance> getPerformancesByEmployee(String employeeId) {
        return performanceDao.getWhere("employeeId", employeeId);
    }
}
