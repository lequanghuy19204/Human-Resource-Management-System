package com.hrms.service;

import com.hrms.model.Organization;
import jakarta.ejb.Local;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface OrganizationService {
    List<Organization> findAll();

    Organization findById(ObjectId id);

    Organization create(Organization organization);

    boolean update(ObjectId id, Organization organization);

    boolean delete(ObjectId id);
}