package com.hrms.service;

import com.hrms.model.Company;
import jakarta.ejb.Local;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface CompanyService {
    List<Company> findAll();

    Company findById(ObjectId id);

    Company create(Company company);

    boolean update(ObjectId id, Company company);

    boolean delete(ObjectId id);
}