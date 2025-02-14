package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * Controller class for handling social media-related operations.
 * Provides endpoints for user registration, login, message creation, retrieval, deletion, and update.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    /**
     * Constructor for dependency injection.
     * @param accountService Service layer for account-related operations.
     * @param messageService Service layer for message-related operations.
     */
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Registers a new user account.
     * @param account Account details from the request body.
     * @return ResponseEntity containing the created Account or an error status.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account newAccount = accountService.register(account);
        if (newAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (newAccount.getAccountId() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(newAccount);
    }

    /**
     * Authenticates a user based on provided credentials.
     * @param account Login credentials from the request body.
     * @return ResponseEntity containing the authenticated Account or an unauthorized status.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account existingAccount = accountService.loginToAccount(account);
        if (existingAccount != null) {
            return ResponseEntity.ok(existingAccount);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Creates a new message.
     * @param message Message details from the request body.
     * @return ResponseEntity containing the created Message or an error status.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        if (newMessage != null) {
            return ResponseEntity.ok(newMessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Retrieves all messages.
     * @return ResponseEntity containing a list of all messages.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getListOfMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.ok(allMessages);
    }

    /**
     * Retrieves a specific message by its ID.
     * @param messageId ID of the message.
     * @return ResponseEntity containing the message if found.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Message messageById = messageService.getMessagesById(messageId);
        return ResponseEntity.ok(messageById);
    }

    /**
     * Deletes a message by its ID.
     * @param messageId ID of the message to delete.
     * @return ResponseEntity containing the deletion status.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        int deletedMessage = messageService.deleteMessageById(messageId);
        if (deletedMessage == 1) {
            return ResponseEntity.ok(deletedMessage);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing message.
     * @param messageId ID of the message to update.
     * @param message Updated message details from the request body.
     * @return ResponseEntity containing the update status.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        int updatedMessage = messageService.updateMessageById(messageId, message.getMessageText());
        if (updatedMessage == 1) {
            return ResponseEntity.ok(updatedMessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Retrieves all messages posted by a specific user.
     * @param accountId ID of the account.
     * @return ResponseEntity containing a list of messages posted by the user.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesPostedByUser(@PathVariable int accountId) {
        List<Message> messagesByUserId = messageService.getMessagesByUserId(accountId);
        return ResponseEntity.ok(messagesByUserId);
    }
}
