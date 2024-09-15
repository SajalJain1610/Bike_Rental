package com.streamline.bikerental.service;

import com.streamline.bikerental.dto.SendUsAMessageDTO;
import com.streamline.bikerental.model.SendUsAMessage;
import com.streamline.bikerental.repository.SendUsAMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendUsAMessageService {

    @Autowired
    private SendUsAMessageRepository repository;

    public SendUsAMessage sendMessage(SendUsAMessageDTO messageDTO) {
        SendUsAMessage message = new SendUsAMessage();
        message.setName(messageDTO.getName());
        message.setEmail(messageDTO.getEmail());
        message.setMessage(messageDTO.getMessage());
        return repository.save(message);
    }

    public SendUsAMessage getMessageById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public List<SendUsAMessage> getAllMessages() {
        return repository.findAll();
    }

    public void deleteMessage(Long id) {
        repository.deleteById(id);
    }
}

