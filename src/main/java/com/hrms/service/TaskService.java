package com.hrms.service;

import jakarta.ejb.Local;
import com.hrms.model.Task;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface TaskService {
    List<Task> findAll();

    List<Task> findByCompanyId(ObjectId id);

    Task findById(ObjectId objectId);

    Task create(Task task);

    boolean update(ObjectId objectId, Task task);

    boolean delete(ObjectId objectId);
}