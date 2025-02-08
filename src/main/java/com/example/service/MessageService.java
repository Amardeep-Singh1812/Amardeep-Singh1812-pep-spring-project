package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    // public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
    //     this.messageRepository = messageRepository;
    //     this.accountRepository = accountRepository;
    // }

    public Message createMessage(Message message) {
        if(message.getMessageText().isEmpty() || message.getMessageText().length() > 255){
            return null;
        }
        if (accountRepository.findById(message.getPostedBy()).isPresent())
            return messageRepository.save(message);
        else
            return null;
    } 

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessagesById(int messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    public int deleteMessageById(int messageId){
        if(messageRepository.findById(messageId).isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        else
            return 0;
    }

    public int updateMessageById(int messageId, String messageText){
        if(messageText.isEmpty() || messageText.length() > 255){
            return 0;
        }
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        else
            return 0;
    }

    public List<Message> getMessagesByUserId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
