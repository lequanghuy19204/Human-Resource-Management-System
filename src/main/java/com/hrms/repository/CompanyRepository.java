package com.hrms.repository;

import com.hrms.config.MongoDBConnection;
import com.hrms.model.Company;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private final MongoCollection<Document> collection;

    public CompanyRepository() {
        this.collection = MongoDBConnection.getDatabase().getCollection("Company");
    }

    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        collection.find().forEach(doc -> companies.add(documentToCompany(doc)));
        return companies;
    }

    public Company findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToCompany(doc) : null;
    }

    public Company create(Company company) {
        Document doc = companyToDocument(company);
        collection.insertOne(doc);
        company.set_id(doc.getObjectId("_id").toString());
        return company;
    }

    public boolean update(ObjectId id, Company company) {
        Document doc = companyToDocument(company);
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    private Company documentToCompany(Document doc) {
        Company company = new Company();
        company.set_id(doc.getObjectId("_id").toString());
        company.setName(doc.getString("name"));
        company.setDescription(doc.getString("description"));
        return company;
    }

    private Document companyToDocument(Company company) {
        Document doc = new Document();
        if (company.get_id() != null) {
            doc.append("_id", new ObjectId(company.get_id()));
        }
        doc.append("name", company.getName());
        doc.append("description", company.getDescription());
        return doc;
    }
}