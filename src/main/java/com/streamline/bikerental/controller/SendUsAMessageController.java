package com.streamline.bikerental.controller;

import com.streamline.bikerental.dto.SendUsAMessageDTO;
import com.streamline.bikerental.model.SendUsAMessage;
import com.streamline.bikerental.service.SendUsAMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class SendUsAMessageController {

    @Autowired
    private SendUsAMessageService service;

    @PostMapping
    public ResponseEntity<SendUsAMessage> sendMessage(@RequestBody SendUsAMessageDTO messageDTO) {
        SendUsAMessage message = service.sendMessage(messageDTO);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SendUsAMessage> getMessageById(@PathVariable Long id) {
        SendUsAMessage message = service.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<SendUsAMessage>> getAllMessages() {
        List<SendUsAMessage> messages = service.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        service.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}

