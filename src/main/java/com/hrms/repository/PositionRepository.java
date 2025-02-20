package com.hrms.repository;

import com.hrms.config.MongoDBConnection;
import com.hrms.model.Position;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PositionRepository {
    private final MongoCollection<Document> collection;

    public PositionRepository() {
        this.collection = MongoDBConnection.getDatabase().getCollection("Position");
    }

    public List<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        collection.find().forEach(doc -> positions.add(documentToPosition(doc)));
        return positions;
    }

    public Position findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToPosition(doc) : null;
    }

    public Position create(Position position) {
        Document doc = positionToDocument(position);
        collection.insertOne(doc);
        position.setId(doc.getObjectId("_id").toString());
        return position;
    }

    public boolean update(ObjectId id, Position position) {
        Document doc = positionToDocument(position);
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    private Position documentToPosition(Document doc) {
        Position position = new Position();
        position.setId(doc.getObjectId("_id").toString());
        position.setOrganizationId(doc.getObjectId("organization_id").toString());
        position.setName(doc.getString("name"));
        position.setDescription(doc.getString("description"));

        Object baseSalary = doc.get("base_salary");
        if (baseSalary instanceof Integer) {
            position.setBaseSalary(((Integer) baseSalary).doubleValue());
        } else if (baseSalary instanceof Double) {
            position.setBaseSalary((Double) baseSalary);
        } else {
            position.setBaseSalary(0.0);
        }

        position.setPermissions((List<String>) doc.get("permissions"));
        return position;
    }

    private Document positionToDocument(Position position) {
        Document doc = new Document();
        if (position.getId() != null) {
            doc.append("_id", new ObjectId(position.getId()));
        }
        doc.append("organization_id", new ObjectId(position.getOrganizationId()));
        doc.append("name", position.getName());
        doc.append("description", position.getDescription());
        doc.append("base_salary", position.getBaseSalary());
        doc.append("permissions", position.getPermissions());
        return doc;
    }
}