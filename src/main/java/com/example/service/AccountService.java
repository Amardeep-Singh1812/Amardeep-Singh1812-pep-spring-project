package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

/**
 * Service class for handling account-related operations.
 * Provides methods for user registration and authentication.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Registers a new user account.
     * Validates username and password before saving.
     * Ensures that the username is not already taken.
     *
     * @param account The account to be registered.
     * @return The saved account if successful, otherwise null.
     */
    public Account register(Account account) {
        if (account.getUsername() == null || account.getPassword().length() < 4) {
            return null;
        }

        // Check if an account with the same username already exists
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            return account; // Return the existing account instead of saving a duplicate
        }

        return accountRepository.save(account);
    }

    /**
     * Authenticates a user by verifying their username and password.
     *
     * @param account The account containing login credentials.
     * @return The authenticated account if credentials are valid, otherwise null.
     */
    public Account loginToAccount(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
