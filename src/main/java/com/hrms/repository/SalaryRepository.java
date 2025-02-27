package com.hrms.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.hrms.model.Salary;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SalaryRepository {
    private final MongoCollection<Document> collection;

    public SalaryRepository() {
        // Lấy collection "Salary" từ cơ sở dữ liệu MongoDB
        this.collection = com.hrms.config.MongoDBConnection.getDatabase().getCollection("Salary");
    }

    // Lấy toàn bộ danh sách Salary từ database
    public List<Salary> findAll() {
        List<Salary> salaries = new ArrayList<>();
        for (Document doc : collection.find()) {
            salaries.add(documentToSalary(doc));
        }
        return salaries;
    }

    // Tìm Salary theo ObjectId
    public Salary findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToSalary(doc) : null;
    }

    // Tạo mới một Salary
    public Salary create(Salary salary) {
        Document doc = salaryToDocument(salary);
        collection.insertOne(doc);
        salary.set_id(doc.getObjectId("_id").toString());
        return salary;
    }

    // Cập nhật Salary theo ObjectId
    public boolean update(ObjectId id, Salary salary) {
        Document doc = salaryToDocument(salary);
        doc.put("_id", id); // Giữ nguyên ID của tài liệu cần thay thế
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    // Xóa Salary theo ObjectId
    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    // Chuyển đổi Document (MongoDB) thành Salary
    private Salary documentToSalary(Document doc) {
        Salary salary = new Salary();
        salary.set_id(doc.getObjectId("_id").toString());

        // Kiểm tra và chuyển đổi `employee_id`
        Object employeeIdObj = doc.get("employee_id");
        if (employeeIdObj instanceof ObjectId) {
            salary.setEmployee_id(employeeIdObj.toString());
        }

        salary.setWorking_days(doc.getInteger("working_days", 0));
        salary.setSalary(doc.getDouble("salary"));
        salary.setPayment_date(doc.getDate("payment_date"));
        return salary;
    }

    // Chuyển đổi Salary thành Document (MongoDB)
    private Document salaryToDocument(Salary salary) {
        Document doc = new Document();

        // Chuyển đổi `_id`
        if (salary.get_id() != null && !salary.get_id().isEmpty()) {
            doc.put("_id", new ObjectId(salary.get_id()));
        }

        // Chuyển đổi `employee_id`
        if (salary.getEmployee_id() != null && !salary.getEmployee_id().isEmpty()) {
            doc.put("employee_id", new ObjectId(salary.getEmployee_id()));
        }

        doc.put("working_days", salary.getWorking_days());
        doc.put("salary", salary.getSalary());
        doc.put("payment_date", salary.getPayment_date());
        return doc;
    }
}
