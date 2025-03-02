package com.hrms.service;

import com.hrms.model.Account;
import jakarta.ejb.Local;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface AccountService {
    List<Account> findAll();

    Account findById(ObjectId id);

    Account findByUsername(String username);

    Account create(Account account);

    boolean update(ObjectId id, Account account);

    boolean delete(ObjectId id);
}