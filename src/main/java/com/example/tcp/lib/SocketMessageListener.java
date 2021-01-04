package com.example.tcp.lib;

import java.nio.channels.SocketChannel;

public interface SocketMessageListener {
    void onReceive(SocketChannel channel, byte[] data);
}
