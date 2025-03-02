package com.hrms.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class MongoDBConnection {
    private static final String CONNECTION_STRING = "mongodb+srv://lequanghuy33333:3eH5qzUunwA3pqV8@humanresourcemanagement.l4udx.mongodb.net/?retryWrites=true&w=majority&appName=HumanResourceManagementSystem";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    @Produces
    @ApplicationScoped
    public static MongoDatabase getDatabase() {
        if (database == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase("Human-Resource-Management-System");
        }
        return database;
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}