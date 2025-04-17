package com.hrms.impl;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.hrms.service.TaskService;
import com.hrms.repository.TaskRepository;
import com.hrms.model.Task;
import org.bson.types.ObjectId;

import java.util.List;

@Stateless
public class TaskServiceImpl implements TaskService {
    @Inject
    private TaskRepository repository;

    public TaskServiceImpl() { }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Task> findByCompanyId(ObjectId id) {return repository.findByCompanyId(id);}

    @Override
    public Task findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Task create(Task task) {
        return repository.create(task);
    }

    @Override
    public boolean update(ObjectId id, Task task) {
        return repository.update(id, task);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}