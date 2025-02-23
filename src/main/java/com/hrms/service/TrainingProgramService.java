package com.hrms.service;

import com.hrms.model.TrainingProgram;
import jakarta.ejb.Local;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface TrainingProgramService {
    List<TrainingProgram> findAll();

    TrainingProgram findById(ObjectId id);

    TrainingProgram create(TrainingProgram program);

    boolean update(ObjectId id, TrainingProgram program);

    boolean delete(ObjectId id);
}