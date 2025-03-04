package com.hrms.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.hrms.model.Reward;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RewardRepository {
    private final MongoCollection<Document> collection;

    public RewardRepository() {
        // Kết nối tới collection "Reward" trong cơ sở dữ liệu MongoDB
        this.collection = com.hrms.config.MongoDBConnection.getDatabase().getCollection("Reward");
    }

    // Lấy toàn bộ danh sách Reward từ database
    public List<Reward> findAll() {
        List<Reward> rewards = new ArrayList<>();
        for (Document doc : collection.find()) {
            rewards.add(documentToReward(doc));
        }
        return rewards;
    }

    // Tìm Reward theo ObjectId
    public Reward findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToReward(doc) : null;
    }

    // Tạo mới một Reward
    public Reward create(Reward reward) {
        Document doc = rewardToDocument(reward);
        collection.insertOne(doc);
        // Gán ID của tài liệu MongoDB mới tạo vào đối tượng Reward
        reward.set_id(doc.getObjectId("_id").toString());
        return reward;
    }

    // Cập nhật Reward theo ObjectId
    public boolean update(ObjectId id, Reward reward) {
        Document doc = rewardToDocument(reward);
        doc.put("_id", id); // Giữ ID hiện tại
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    // Xóa Reward theo ObjectId
    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    // Chuyển đổi Document (MongoDB) thành Reward
    private Reward documentToReward(Document doc) {
        Reward reward = new Reward();
        reward.set_id(doc.getObjectId("_id").toString());
        reward.setEmployee_id(doc.getObjectId("employee_id").toString());
        reward.setCompleted_tasks(doc.getInteger("completed_tasks", 0));
        reward.setPerformance_score(doc.getDouble("performance_score"));
        reward.setReward(doc.getDouble("reward"));
        reward.setType(doc.getString("type"));
        return reward;
    }

    // Chuyển đổi Reward thành Document (MongoDB)
    private Document rewardToDocument(Reward reward) {
        Document doc = new Document();
        if (reward.get_id() != null) {
            doc.put("_id", new ObjectId(reward.get_id())); // Chuyển từ String sang ObjectId
        }
        doc.put("employee_id", new ObjectId(reward.getEmployee_id()));
        doc.put("completed_tasks", reward.getCompleted_tasks());
        doc.put("performance_score", reward.getPerformance_score());
        doc.put("reward", reward.getReward());
        doc.put("type", reward.getType());
        return doc;
    }
}