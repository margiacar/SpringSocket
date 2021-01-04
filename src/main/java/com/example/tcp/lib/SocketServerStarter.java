package com.example.tcp.lib;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class SocketServerStarter {

    @Value("${tcp.server.port}")
    private int serverPort;

    @Bean
    public Selector getSelector() throws IOException {
        return Selector.open();
    }

    @Bean
    public ServerSocketChannel getServerChannel(Selector selector) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress("localhost", serverPort));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started...");

        return serverChannel;
    }


}