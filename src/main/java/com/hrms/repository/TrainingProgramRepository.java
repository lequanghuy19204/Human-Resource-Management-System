package com.hrms.repository;

import com.hrms.config.MongoDBConnection;
import com.hrms.model.TrainingProgram;
import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TrainingProgramRepository {
    private final MongoCollection<Document> collection;

    public TrainingProgramRepository() {
        this.collection = MongoDBConnection.getDatabase().getCollection("TrainingProgram");
    }

    public List<TrainingProgram> findAll() {
        List<TrainingProgram> programs = new ArrayList<>();
        collection.find().forEach(doc -> programs.add(documentToTrainingProgram(doc)));
        return programs;
    }

    public List<TrainingProgram> findByCompanyId(ObjectId companyId) {
        List<TrainingProgram> programs = new ArrayList<>();
        collection.find(Filters.eq("company_id", companyId))
                .forEach(doc -> programs.add(documentToTrainingProgram(doc)));
        return programs;
    }

    public TrainingProgram findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToTrainingProgram(doc) : null;
    }

    public TrainingProgram create(TrainingProgram program) {
        Document doc = trainingProgramToDocument(program);
        collection.insertOne(doc);
        program.set_id(doc.getObjectId("_id").toString());
        return program;
    }

    public boolean update(ObjectId id, TrainingProgram program) {
        Document doc = trainingProgramToDocument(program);
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    private TrainingProgram documentToTrainingProgram(Document doc) {
        TrainingProgram program = new TrainingProgram();
        program.set_id(doc.getObjectId("_id").toString());
        program.setName(doc.getString("name"));
        program.setDescription(doc.getString("description"));
        program.setStart(doc.getDate("start"));
        program.setEnd(doc.getDate("end"));
        program.setLocation(doc.getString("location"));
        program.setTrainer_id(doc.getObjectId("trainer_id").toString());
        
        if (doc.containsKey("company_id")) {
            program.setCompany_id(doc.getObjectId("company_id").toString());
        }

        List<ObjectId> participantObjectIds = (List<ObjectId>) doc.get("participant_ids");
        if (participantObjectIds != null) {
            List<String> participantIds = participantObjectIds.stream()
                    .map(ObjectId::toString)
                    .collect(Collectors.toList());
            program.setParticipant_ids(participantIds);
        }
        return program;
    }

    private Document trainingProgramToDocument(TrainingProgram program) {
        Document doc = new Document();
        if (program.get_id() != null) {
            doc.append("_id", new ObjectId(program.get_id()));
        }
        doc.append("name", program.getName());
        doc.append("description", program.getDescription());
        doc.append("start", program.getStart());
        doc.append("end", program.getEnd());
        doc.append("location", program.getLocation());
        doc.append("trainer_id", new ObjectId(program.getTrainer_id()));
        
        if (program.getCompany_id() != null) {
            doc.append("company_id", new ObjectId(program.getCompany_id()));
        }

        if (program.getParticipant_ids() != null) {
            List<ObjectId> participantIds = program.getParticipant_ids().stream()
                    .map(ObjectId::new)
                    .collect(Collectors.toList());
            doc.append("participant_ids", participantIds);
        }
        return doc;
    }
}