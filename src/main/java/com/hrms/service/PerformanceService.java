package com.hrms.service;

import jakarta.ejb.Local;
import com.hrms.model.Performance;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface PerformanceService {
    List<Performance> findAll();

    Performance findById(ObjectId objectId);

    Performance create(Performance performance);

    boolean update(ObjectId objectId, Performance performance);

    boolean delete(ObjectId objectId);
}