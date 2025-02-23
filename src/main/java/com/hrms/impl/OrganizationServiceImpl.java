package com.hrms.impl;

import com.hrms.model.Organization;
import com.hrms.repository.OrganizationRepository;
import com.hrms.service.OrganizationService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.List;

@Stateless
public class OrganizationServiceImpl implements OrganizationService {

    @Inject
    private OrganizationRepository repository;

    @Override
    public List<Organization> findAll() {
        return repository.findAll();
    }

    @Override
    public Organization findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Organization create(Organization organization) {
        return repository.create(organization);
    }

    @Override
    public boolean update(ObjectId id, Organization organization) {
        return repository.update(id, organization);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}