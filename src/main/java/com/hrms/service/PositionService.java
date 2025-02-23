package com.hrms.service;

import com.hrms.model.Position;
import jakarta.ejb.Local;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface PositionService {
    List<Position> findAll();

    Position findById(ObjectId id);

    Position create(Position position);

    boolean update(ObjectId id, Position position);

    boolean delete(ObjectId id);
}