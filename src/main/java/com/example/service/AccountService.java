package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // public AccountService(AccountRepository accountRepository) {
    //     this.accountRepository = accountRepository;
    // }

    public Account register(Account account) {
        if(account.getUsername() == null || account.getPassword().length() < 4)
            return null;
        
        Account existingAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        
        if(existingAccount != null)
            return account;

        return accountRepository.save(account);
    }

    public Account loginToAccount(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
