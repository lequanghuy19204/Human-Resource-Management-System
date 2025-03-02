package com.hrms.impl;

import com.hrms.model.Account;
import com.hrms.repository.AccountRepository;
import com.hrms.service.AccountService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;

@Stateless
public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountRepository repository;

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Account findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Account create(Account account) {
        return repository.create(account);
    }

    @Override
    public boolean update(ObjectId id, Account account) {
        return repository.update(id, account);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}