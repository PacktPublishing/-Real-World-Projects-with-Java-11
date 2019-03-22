package server.networking;

import global.NetworkingSettings;
import server.database.DatabaseManager;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ChatManagingServer extends Server {

    public ChatManagingServer() throws IOException {
        super(NetworkingSettings.CHAT_MANAGING_PORT);
    }

    @Override
    void handleOutput(String output, SocketChannel client) {
        String invalidUsers = "";
        String[] users = output.split(NetworkingSettings.SENDING_SEPARATOR_REGEX);
        for (int i = 1; i < users.length; i++) {
            if (!DatabaseManager.userValidForChat(users[0], users[i])) {
                invalidUsers += users[i] + NetworkingSettings.SENDING_SEPARATOR_REGEX;
            }
        }
        try {
            if (!invalidUsers.isEmpty()) {
                writeToClient(invalidUsers.substring(0, invalidUsers.length() - NetworkingSettings.SENDING_SEPARATOR_REGEX.length()), client);
            } else {
                writeToClient(NetworkingSettings.SENDING_SEPARATOR_REGEX, client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
