package app.networking;

import app.ApplicationUtils;
import global.NetworkingSettings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

abstract class Client {
    private SocketChannel client;
    private final InetSocketAddress hostAddress;

    Client(String ip, int port) {
        this.hostAddress = new InetSocketAddress(ip, port);
    }

    public void connect() throws IOException {
        if (!ApplicationUtils.isConnectedToServer()) {
            client = SocketChannel.open(hostAddress);
        }
    }

    void sendMessage(String message) {
        try {
            byte[] messageBytes = message.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(messageBytes);
            client.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String receiveMessage() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(NetworkingSettings.MAX_PACKET_SIZE);
        client.read(buffer);
        return new String(buffer.array()).trim();
    }

    public void cleanUp() throws IOException {
        if (ApplicationUtils.isConnectedToServer()) {
            sendMessage(NetworkingSettings.DISCONNECT_STRING);
            client.close();
        }
    }
}
