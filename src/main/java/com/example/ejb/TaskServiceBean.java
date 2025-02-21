package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.dao.TaskDao;
import com.example.entity.Task;
import java.util.List;

@Stateless
public class TaskServiceBean {

    @Inject
    private TaskDao taskDao;

    public void addTask(Task task) {
        taskDao.add(task);
    }

    public void updateTask(Task task) {
        taskDao.update(task);
    }

    public void removeTask(String id) {
        taskDao.removeById(id);
    }

    public boolean hasTask(String id) {
        return taskDao.has(id);
    }

    public List<Task> getAllTasks() {
        return taskDao.getAll();
    }

    public List<Task> getTasksByStatus(String status) {
        return taskDao.getWhere("status", status);
    }
}
