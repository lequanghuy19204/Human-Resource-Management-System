package com.hrms.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.hrms.model.Salary;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    // Tìm Salary theo ID (dưới dạng chuỗi)
    public Salary findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToSalary(doc) : null;
    }

    // Tạo mới một Salary
    public Salary create(Salary salary) {
        Document doc = salaryToDocument(salary);
        collection.insertOne(doc);
        // Gán ID được MongoDB tạo cho đối tượng Salary (dưới dạng chuỗi)
        salary.set_id(doc.getObjectId("_id").toString());
        return salary;
    }

    // Cập nhật Salary theo ID (dưới dạng chuỗi)
    public boolean update(ObjectId id, Salary salary) {
        Document doc = salaryToDocument(salary);
        doc.put("_id", id); // Chuyển đổi ID từ chuỗi sang ObjectId
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    // Xóa Salary theo ID (dưới dạng chuỗi)
    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    public Salary getByMonth(int month, int year, String employeeId) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        Date from = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        Bson filter = Filters.and(
                Filters.gte("payment_date", from),
                Filters.lte("payment_date", to),
                Filters.eq("employee_id", employeeId)
        );

        Document doc = collection.find(filter).first();

        return doc != null ? documentToSalary(doc) : null;
    }



    // Phương thức chuyển đổi Document (MongoDB) thành Salary
    private Salary documentToSalary(Document doc) {
        Salary salary = new Salary();
        salary.set_id(doc.getObjectId("_id").toString()); // Chuyển ObjectId thành chuỗi
        salary.setEmployee_id(doc.getObjectId("employee_id").toString()); // Chuyển ObjectId thành chuỗi
        salary.setWorking_days(doc.getInteger("working_days", 0));
        salary.setPayment_date(doc.getDate("payment_date"));

        return salary;
    }


    // Phương thức chuyển đối tượng Salary sang Document (MongoDB)
    private Document salaryToDocument(Salary salary) {
        Document doc = new Document();
        if (salary.get_id() != null && !salary.get_id().isEmpty()) {
            doc.put("_id", new ObjectId(salary.get_id())); // Chuyển đổi chuỗi thành ObjectId
        }
        doc.put("employee_id", new ObjectId(salary.getEmployee_id())); // Chuyển đổi chuỗi thành ObjectId
        doc.put("working_days", salary.getWorking_days());
        doc.put("payment_date", salary.getPayment_date());
        return doc;
    }
}