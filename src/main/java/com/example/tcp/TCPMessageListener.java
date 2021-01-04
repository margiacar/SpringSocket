package com.example.tcp;

import com.example.tcp.lib.SocketMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.UUID;

@Component
public class TCPMessageListener implements SocketMessageListener {

    @Autowired
    private Map<UUID, SocketChannel> socketChannelMap;

    @Override
    public void onReceive(SocketChannel channel, byte[] data) {
        UUID uuid = UUID.randomUUID();
        socketChannelMap.put(uuid, channel);

        System.out.printf("Message Received[%s]: %s", uuid.toString(), new String(data));
    }
}
