package com.hrms.service;

import jakarta.ejb.Local;
import com.hrms.model.Reward;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface RewardService {
    List<Reward> findAll();

    Reward findById(ObjectId objectId);

    Reward create(Reward reward);

    boolean update(ObjectId objectId, Reward reward);

    boolean delete(ObjectId objectId);
}