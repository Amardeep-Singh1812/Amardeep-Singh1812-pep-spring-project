package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

/**
 * Repository interface for Message entity.
 * Provides methods to interact with the database for message-related operations.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Retrieves a list of messages posted by a specific user.
     *
     * @param postedBy the ID of the user who posted the messages.
     * @return a list of messages posted by the specified user.
     */
    List<Message> findByPostedBy(int postedBy);
}
