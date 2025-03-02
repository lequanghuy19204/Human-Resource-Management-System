package com.hrms.impl;

import com.hrms.model.Company;
import com.hrms.repository.CompanyRepository;
import com.hrms.service.CompanyService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.List;

@Stateless
public class CompanyServiceImpl implements CompanyService {

    @Inject
    private CompanyRepository repository;

    @Override
    public List<Company> findAll() {
        return repository.findAll();
    }

    @Override
    public Company findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Company create(Company company) {
        return repository.create(company);
    }

    @Override
    public boolean update(ObjectId id, Company company) {
        return repository.update(id, company);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}