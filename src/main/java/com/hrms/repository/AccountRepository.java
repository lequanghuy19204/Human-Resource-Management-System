package com.hrms.repository;

import com.hrms.config.MongoDBConnection;
import com.hrms.model.Account;
import jakarta.enterprise.context.ApplicationScoped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AccountRepository {
    private final MongoCollection<Document> collection;

    public AccountRepository() {
        this.collection = MongoDBConnection.getDatabase().getCollection("Account");
    }

    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        collection.find().forEach(doc -> accounts.add(documentToAccount(doc)));
        return accounts;
    }

    public Account findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToAccount(doc) : null;
    }

    public Account findByUsername(String username) {
        Document doc = collection.find(Filters.eq("username", username)).first();
        return doc != null ? documentToAccount(doc) : null;
    }

    public Account create(Account account) {
        Document doc = accountToDocument(account);
        collection.insertOne(doc);
        account.set_id(doc.getObjectId("_id").toString());
        return account;
    }

    public boolean update(ObjectId id, Account account) {
        Document doc = accountToDocument(account);
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    private Account documentToAccount(Document doc) {
        Account account = new Account();
        account.set_id(doc.getObjectId("_id").toString());

        ObjectId employeeId = doc.getObjectId("employee_id");
        if (employeeId != null) {
            account.setEmployee_id(employeeId.toString());
        }

        account.setUsername(doc.getString("username"));
        account.setPassword(doc.getString("password"));
        return account;
    }

    private Document accountToDocument(Account account) {
        Document doc = new Document();
        if (account.get_id() != null) {
            doc.append("_id", new ObjectId(account.get_id()));
        }

        if (account.getEmployee_id() != null) {
            doc.append("employee_id", new ObjectId(account.getEmployee_id()));
        }

        doc.append("username", account.getUsername());
        doc.append("password", account.getPassword());
        return doc;
    }
}