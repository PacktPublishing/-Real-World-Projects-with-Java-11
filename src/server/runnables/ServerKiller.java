package server.runnables;

import app.networking.*;
import global.NetworkingSettings;

import java.io.IOException;

class ServerKiller {
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatRetrievingClient chatRetrievingClient = new ChatRetrievingClient(NetworkingSettings.SERVER_IP);
        chatRetrievingClient.connect();
        try {
            chatRetrievingClient.killServer();
        } catch (StringIndexOutOfBoundsException ignored){}
        Thread.sleep(1000);
        chatRetrievingClient.cleanUp();
    }
}
