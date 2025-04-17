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
import java.util.Arrays;

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

    public List<Employee> findByCompanyId(ObjectId companyId) {
        List<Employee> employees = new ArrayList<>();
        collection.find(Filters.eq("company_id", companyId))
                .forEach(doc -> employees.add(documentToEmployee(doc)));
        return employees;
    }

    public List<Employee> findManagersByCompanyId(ObjectId companyId) {
        List<Employee> managers = new ArrayList<>();
        Document query = new Document("company_id", companyId)
                .append("permissions", new Document("$in", Arrays.asList(
                        "MANAGE_ORGANIZATION", "MANAGE_EMPLOYEES", "MANAGE_TASKS",
                        "MANAGE_SALARY", "MANAGE_TRAINING", "MANAGE_PERFORMANCE")));

        collection.find(query).forEach(doc -> managers.add(documentToEmployee(doc)));
        return managers;
    }

    private Employee documentToEmployee(Document doc) {
        Employee employee = new Employee();
        employee.set_id(doc.getObjectId("_id").toString());
        employee.setName(doc.getString("name"));
        employee.setPosition_name(doc.getString("position_name"));
        employee.setPermissions((List<String>) doc.get("permissions"));

        ObjectId orgId = doc.getObjectId("organization_id");
        if (orgId != null) {
            employee.setOrganization_id(orgId.toString());
        }

        ObjectId companyId = doc.getObjectId("company_id");
        if (companyId != null) {
            employee.setCompany_id(companyId.toString());
        }

        ObjectId managerId = doc.getObjectId("manager_id");
        if (managerId != null) {
            employee.setManager_id(managerId.toString());
        }

        employee.setPhone(doc.getString("phone"));
        
        employee.setTask_count(doc.getInteger("task_count", 0));
        employee.setCompleted_tasks(doc.getInteger("completed_tasks", 0));
        employee.setOntime_tasks(doc.getInteger("ontime_tasks", 0));
        
        Double qualityScore = doc.containsKey("quality_score") ? doc.getDouble("quality_score") : 0.0;
        employee.setQuality_score(qualityScore);
        
        Double performanceScore = doc.containsKey("performance_score") ? doc.getDouble("performance_score") : 0.0;
        employee.setPerformance_score(performanceScore);
        
        Double baseSalary = doc.containsKey("base_salary") ? doc.getDouble("base_salary") : 0.0;
        employee.setBase_salary(baseSalary);

        ObjectId accountId = doc.getObjectId("account_id");
        if (accountId != null) {
            employee.setAccount_id(accountId.toString());
        }

        return employee;
    }

    private Document employeeToDocument(Employee employee) {
        Document doc = new Document();
        if (employee.get_id() != null) {
            doc.append("_id", new ObjectId(employee.get_id()));
        }
        doc.append("name", employee.getName());
        doc.append("position_name", employee.getPosition_name());
        doc.append("permissions", employee.getPermissions());

        if (employee.getOrganization_id() != null) {
            doc.append("organization_id", new ObjectId(employee.getOrganization_id()));
        }

        if (employee.getCompany_id() != null) {
            doc.append("company_id", new ObjectId(employee.getCompany_id()));
        }

        if (employee.getManager_id() != null) {
            doc.append("manager_id", new ObjectId(employee.getManager_id()));
        }

        doc.append("phone", employee.getPhone());
        doc.append("task_count", employee.getTask_count());
        doc.append("completed_tasks", employee.getCompleted_tasks());
        doc.append("ontime_tasks", employee.getOntime_tasks());
        doc.append("quality_score", employee.getQuality_score());
        doc.append("performance_score", employee.getPerformance_score());
        doc.append("base_salary", employee.getBase_salary());

        if (employee.getAccount_id() != null) {
            doc.append("account_id", new ObjectId(employee.getAccount_id()));
        }

        return doc;
    }

    public List<Employee> findByOrganizationId(ObjectId organizationId) {
        List<Employee> employees = new ArrayList<>();
        collection.find(Filters.eq("organization_id", organizationId))
                .forEach(doc -> employees.add(documentToEmployee(doc)));
        return employees;
    }
}