package com.example.tcp.lib;

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
public class SocketServerReader {

    @Autowired
    private SocketMessageListener messageListener;

    @Autowired
    private Selector selector;

    @Autowired
    Map<UUID, SocketChannel> socketChannelMap;

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        int numRead = channel.read(buffer);

        if (numRead == -1) {
            channel.close();
            key.cancel();
            return;
        }

        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);

        messageListener.onReceive(channel, data);
        //channel.register(selector, SelectionKey.OP_WRITE);
        ByteBuffer buffer2 = ByteBuffer.allocate(2048);
        buffer2 = buffer2.wrap("client response".getBytes());
        channel.write(buffer2);
    }
}
