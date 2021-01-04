package com.example.tcp;

import com.example.tcp.lib.SocketMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.UUID;

@Component
public class TCPMessageProducer {

    @Autowired
    private Map<UUID, SocketChannel> socketChannelMap;

    @Autowired
    private Selector selector;

    public void send(UUID uuid, byte[] data) throws IOException {
        SocketChannel channel = socketChannelMap.get(uuid);
        channel.register(selector, SelectionKey.OP_WRITE);
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
        System.out.println("Message Sent: " + new String(data));
        channel.register(selector, SelectionKey.OP_READ);
    }
}
