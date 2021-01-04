package com.example.tcp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class TPCConfiguration {

    @Bean
    public Map<UUID, SocketChannel> getSocketChannelMap(){
        return new HashMap<>();
    }
}
