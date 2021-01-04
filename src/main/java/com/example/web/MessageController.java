package com.example.web;

import com.example.tcp.TCPMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
public class MessageController {
    @Autowired
    private TCPMessageProducer producer;


    @GetMapping("/sendMessage")
    public void sendMessage(@RequestParam String uuid, @RequestParam String message) throws IOException {
        producer.send(UUID.fromString(uuid), message.getBytes());
    }
}
