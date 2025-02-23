package com.hrms.repository;

import com.hrms.config.MongoDBConnection;
import com.hrms.model.Employee;
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
public class EmployeeRepository {
    private final MongoCollection<Document> collection;

    public EmployeeRepository() {
        this.collection = MongoDBConnection.getDatabase().getCollection("Employee");
    }

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        collection.find().forEach(doc -> employees.add(documentToEmployee(doc)));
        return employees;
    }

    public Employee findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToEmployee(doc) : null;
    }

    public Employee create(Employee employee) {
        Document doc = employeeToDocument(employee);
        collection.insertOne(doc);
        employee.set_id(doc.getObjectId("_id").toString());
        return employee;
    }

    public boolean update(ObjectId id, Employee employee) {
        Document doc = employeeToDocument(employee);
        UpdateResult result = collection.replaceOne(Filters.eq("_id", id), doc);
        return result.getModifiedCount() > 0;
    }

    public boolean delete(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    private Employee documentToEmployee(Document doc) {
        Employee employee = new Employee();
        employee.set_id(doc.getObjectId("_id").toString());
        employee.setName(doc.getString("name"));
        employee.setPosition_id(doc.getObjectId("position_id").toString());
        employee.setOrganization_id(doc.getObjectId("organization_id").toString());
        employee.setCompany_id(doc.getObjectId("company_id").toString());
        employee.setManager_id(doc.getObjectId("manager_id").toString());
        employee.setPhone(doc.getString("phone"));
        employee.setStatus(doc.getString("status"));
        employee.setOvertime_hours(doc.getInteger("overtime_hours", 0));
        employee.setLate_hours(doc.getInteger("late_hours", 0));
        employee.setAbsent_days(doc.getInteger("absent_days", 0));
        employee.setAccount_id(doc.getObjectId("account_id").toString());
        return employee;
    }

    private Document employeeToDocument(Employee employee) {
        Document doc = new Document();
        if (employee.get_id() != null) {
            doc.append("_id", new ObjectId(employee.get_id()));
        }
        doc.append("name", employee.getName());
        doc.append("position_id", new ObjectId(employee.getPosition_id()));
        doc.append("organization_id", new ObjectId(employee.getOrganization_id()));
        doc.append("company_id", new ObjectId(employee.getCompany_id()));
        doc.append("manager_id", new ObjectId(employee.getManager_id()));
        doc.append("phone", employee.getPhone());
        doc.append("status", employee.getStatus());
        doc.append("overtime_hours", employee.getOvertime_hours());
        doc.append("late_hours", employee.getLate_hours());
        doc.append("absent_days", employee.getAbsent_days());
        doc.append("account_id", new ObjectId(employee.getAccount_id()));
        return doc;
    }
}