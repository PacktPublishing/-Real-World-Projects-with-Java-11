package server.networking;

import global.NetworkingSettings;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class SendingServer extends Server {

    public SendingServer() throws IOException {
        super(NetworkingSettings.SENDING_PORT);
    }

    @Override
    void handleOutput(String output, SocketChannel client) {}
}
