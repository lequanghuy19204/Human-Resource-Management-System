package com.hrms.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.hrms.model.Performance;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PerformanceRepository {
    private final MongoCollection<Document> collection;

    public PerformanceRepository() {
        // Kết nối tới collection "Performance" trong cơ sở dữ liệu MongoDB
        this.collection = com.hrms.config.MongoDBConnection.getDatabase().getCollection("Performance");
    }

    // Lấy toàn bộ danh sách Performance từ database
    public List<Performance> findAll() {
        List<Performance> performances = new ArrayList<>();
        for (Document doc : collection.find()) {
            performances.add(documentToPerformance(doc));
        }
        return performances;
    }

    // Tìm Performance theo ObjectId
    public Performance findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToPerformance(doc) : null;
    }

    // Tạo mới một Performance
    public Performance create(Performance performance) {
        Document doc = performanceToDocument(performance);
        collection.insertOne(doc);
        // Gán ID của tài liệu MongoDB mới tạo vào đối tượng Performance
        performance.set_id(doc.getObjectId("_id").toString());
        return performance;
    }

    // Cập nhật Performance theo ObjectId
    public boolean update(ObjectId id, Performance performance) {
        Document doc = performanceToDocument(performance);
        doc.put("_id", id); // Giữ ID hiện tại
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    // Xóa Performance theo ObjectId
    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    // Phương thức chuyển Document (MongoDB) thành Performance
    private Performance documentToPerformance(Document doc) {
        Performance performance = new Performance();
        performance.set_id(doc.getObjectId("_id").toString());
        performance.setEmployee_id(doc.getObjectId("employee_id").toString());
        performance.setPerformance_score(doc.getDouble("performance_score"));
        performance.setComplete_rate(doc.getDouble("complete_rate"));
        performance.setOntime_rate(doc.getDouble("ontime_rate"));
        performance.setQuality_score(doc.getInteger("quality_score"));
        return performance;
    }

    // Phương thức chuyển đối tượng Performance sang Document (MongoDB)
    private Document performanceToDocument(Performance performance) {
        Document doc = new Document();
        if (performance.get_id() != null) {
            doc.put("_id", new ObjectId(performance.get_id())); // Chuyển từ String sang ObjectId
        }
        doc.put("employee_id", new ObjectId(performance.getEmployee_id()));
        doc.put("performance_score", performance.getPerformance_score());
        doc.put("complete_rate", performance.getComplete_rate());
        doc.put("ontime_rate", performance.getOntime_rate());
        doc.put("quality_score", performance.getQuality_score());
        return doc;
    }
}