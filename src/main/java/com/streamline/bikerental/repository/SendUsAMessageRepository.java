package com.streamline.bikerental.repository;

import com.streamline.bikerental.model.SendUsAMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendUsAMessageRepository extends JpaRepository<SendUsAMessage, Long> {
}

