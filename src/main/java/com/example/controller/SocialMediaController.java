package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account newAccount =  accountService.register(account);
        if(newAccount.getAccountId() == null) {
            return ResponseEntity.status(409).build();
        }
        if(newAccount != null) {
            return ResponseEntity.status(200)
                                 .body(newAccount);
        }
        else
            return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account existingAccount = accountService.loginToAccount(account);
        if (existingAccount != null) {
            return ResponseEntity.ok().body(existingAccount);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        if(newMessage != null) {
            return ResponseEntity.status(200)
                                 .body(newMessage);
        }
        else
            return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getListOfMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getListOfMessageById(@PathVariable int messageId) {
        Message messagesById = messageService.getMessagesById(messageId);
        return ResponseEntity.status(200).body(messagesById);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        int deletedMessage = messageService.deleteMessageById(messageId);
        if(deletedMessage == 1){
            return ResponseEntity.ok().body(deletedMessage);
        }
        else
            return ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        int updatedMessage = messageService.updateMessageById(messageId, message.getMessageText());
        if(updatedMessage == 1) {
            return ResponseEntity.ok().body(updatedMessage);
        }
        else
            return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesPostedByUser(@PathVariable int accountId) {
        List<Message> messagesByUserId = messageService.getMessagesByUserId(accountId);
        return ResponseEntity.ok().body(messagesByUserId);
    }

}
