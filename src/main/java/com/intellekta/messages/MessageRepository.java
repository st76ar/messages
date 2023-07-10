package com.intellekta.messages;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageJpa, Integer> {

        List<MessageJpa> findBySenderMessage(String sender);


}
