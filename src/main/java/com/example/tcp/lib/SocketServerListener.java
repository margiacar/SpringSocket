package com.example.tcp.lib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Component
public class SocketServerListener {

    @Autowired
    private ServerSocketChannel serverChannel;

    @Autowired
    private Selector selector;

    @Autowired
    private SocketServerReader reader;

    @PostConstruct
    private void startServer() {
        new Thread(()-> {
            try {
                startListener();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startListener() throws IOException {
        while (serverChannel.isOpen()) {
            // wait for events
            selector.select();

            //work on selected keys
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();

                // this is necessary to prevent the same key from coming up
                // again the next time around.
                keys.remove();

                if (!key.isValid()) continue;

                if (key.isAcceptable()) this.accept(key);
                else if (key.isReadable())reader.read(key);
                else if(key.isWritable()){
                    ByteBuffer buffer = ByteBuffer.wrap("client response".getBytes());
                    buffer.flip();
                    ((SocketChannel)key.channel()).write(buffer);
                    buffer.clear();
                    key.channel().register(selector, SelectionKey.OP_READ);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel)key.channel()).accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }
}