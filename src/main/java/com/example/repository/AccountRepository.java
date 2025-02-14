package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

/**
 * Repository interface for Account entity.
 * Provides methods to interact with the database for account-related operations.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Finds an account by username.
     *
     * @param username the username of the account.
     * @return the Account entity if found, otherwise null.
     */
    Account findByUsername(String username);

    /**
     * Finds an account by username and password.
     * Used for authentication purposes.
     *
     * @param username the username of the account.
     * @param password the password of the account.
     * @return the Account entity if credentials match, otherwise null.
     */
    Account findByUsernameAndPassword(String username, String password);
}
