package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

/**
 * Service class for handling message-related operations.
 * Provides methods for creating, retrieving, updating, and deleting messages.
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates a new message.
     * Validates message length and ensures the associated user exists before saving.
     *
     * @param message The message to be created.
     * @return The saved message if successful, otherwise null.
     */
    public Message createMessage(Message message) {
        if (message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            return null;
        }
        if (accountRepository.findById(message.getPostedBy()).isPresent()) {
            return messageRepository.save(message);
        }
        return null;
    }

    /**
     * Retrieves all messages.
     *
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId The ID of the message.
     * @return The message if found, otherwise null.
     */
    public Message getMessagesById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    /**
     * Deletes a message by its ID.
     *
     * @param messageId The ID of the message to delete.
     * @return 1 if the message was deleted, otherwise 0.
     */
    public int deleteMessageById(int messageId) {
        if (messageRepository.findById(messageId).isPresent()) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    /**
     * Updates a message by its ID.
     * Validates message length before updating.
     *
     * @param messageId The ID of the message to update.
     * @param messageText The new text for the message.
     * @return 1 if the update was successful, otherwise 0.
     */
    public int updateMessageById(int messageId, String messageText) {
        if (messageText.isEmpty() || messageText.length() > 255) {
            return 0;
        }
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    /**
     * Retrieves all messages posted by a specific user.
     *
     * @param accountId The ID of the user.
     * @return A list of messages posted by the specified user.
     */
    public List<Message> getMessagesByUserId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
