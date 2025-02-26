package com.hrms.repository;

import com.hrms.config.MongoDBConnection;
import com.hrms.model.Organization;
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
public class OrganizationRepository {
    private final MongoCollection<Document> collection;

    public OrganizationRepository() {
        this.collection = MongoDBConnection.getDatabase().getCollection("Organization");
    }

    public List<Organization> findAll() {
        List<Organization> organizations = new ArrayList<>();
        collection.find().forEach(doc -> organizations.add(documentToOrganization(doc)));
        return organizations;
    }

    public Organization findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToOrganization(doc) : null;
    }

    public Organization create(Organization organization) {
        Document doc = organizationToDocument(organization);
        collection.insertOne(doc);
        organization.setId(String.valueOf((ObjectId) doc.get("_id")));
        return organization;
    }

    public boolean update(ObjectId id, Organization organization) {
        Document doc = organizationToDocument(organization);
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    public List<Organization> findByCompanyId(ObjectId companyId) {
        List<Organization> organizations = new ArrayList<>();
        collection.find(Filters.eq("company_id", companyId))
                .forEach(doc -> organizations.add(documentToOrganization(doc)));
        return organizations;
    }

    private Organization documentToOrganization(Document doc) {
        Organization org = new Organization();
        org.setId(doc.getObjectId("_id").toString());
        org.setCompanyId(doc.getObjectId("company_id").toString());
        org.setName(doc.getString("name"));
        return org;
    }

    private Document organizationToDocument(Organization org) {
        Document doc = new Document();
        if (org.getId() != null) {
            doc.append("_id", new ObjectId(org.getId()));
        }
        doc.append("company_id", new ObjectId(org.getCompanyId()));
        doc.append("name", org.getName());
        return doc;
    }
}