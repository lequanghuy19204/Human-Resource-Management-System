package com.hrms.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.hrms.model.Task;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TaskRepository {
    private final MongoCollection<Document> collection;

    public TaskRepository() {
        // Lấy collection "Task" từ cơ sở dữ liệu MongoDB
        this.collection = com.hrms.config.MongoDBConnection.getDatabase().getCollection("Task");
    }

    // Lấy toàn bộ danh sách Task từ database
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        for (Document doc : collection.find()) {
            tasks.add(documentToTask(doc));
        }
        return tasks;
    }

    // Tìm Task theo ObjectId
    public Task findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToTask(doc) : null;
    }

    // Tạo mới một Task
    public Task create(Task task) {
        Document doc = taskToDocument(task);
        collection.insertOne(doc);
        // Gán ID được MongoDB tự tạo cho đối tượng Task
        task.set_id(doc.getObjectId("_id").toString());
        return task;
    }

    // Cập nhật Task theo ObjectId
    public boolean update(ObjectId id, Task task) {
        Document doc = taskToDocument(task);
        doc.put("_id", id); // Giữ nguyên ID của tài liệu cần thay thế
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    // Xóa Task theo ObjectId
    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    // Phương thức chuyển đổi Document (MongoDB) thành Task
    private Task documentToTask(Document doc) {
        Task task = new Task();
        task.set_id(doc.getObjectId("_id").toString());
        task.setName(doc.getString("name"));
        task.setDescription(doc.getString("description"));

        // Chuyển đổi danh sách ObjectId từ `assigned_to` sang String
        List<String> assignedTo = doc.getList("assigned_to", ObjectId.class).stream()
                .map(ObjectId::toString)
                .toList();
        task.setAssigned_to(assignedTo);

        task.setStatus(doc.getString("status"));
        return task;
    }

    // Phương thức chuyển đối tượng Task sang Document (MongoDB)
    private Document taskToDocument(Task task) {
        Document doc = new Document();
        if (task.get_id() != null) {
            doc.put("_id", new ObjectId(task.get_id()));
        }
        doc.put("name", task.getName());
        doc.put("description", task.getDescription());

        // Chuyển đổi danh sách String sang ObjectId cho `assigned_to`
        List<ObjectId> assignedTo = task.getAssigned_to().stream()
                .map(ObjectId::new)
                .toList();
        doc.put("assigned_to", assignedTo);

        doc.put("status", task.getStatus());
        return doc;
    }
}