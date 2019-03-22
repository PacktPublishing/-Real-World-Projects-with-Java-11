package server.networking;

import global.NetworkingSettings;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class PingServer extends Server {

    public PingServer() throws IOException {
        super(NetworkingSettings.PING_PORT);
    }

    @Override
    void handleOutput(String output, SocketChannel client) {
        try {
            writeToClient(String.valueOf(new Date().getTime()), client);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }
}
