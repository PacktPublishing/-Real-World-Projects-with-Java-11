package server.networking;

import global.NetworkingSettings;
import server.database.DatabaseManager;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class RegisteringServer extends Server{

    public RegisteringServer() throws IOException {
        super(NetworkingSettings.REGISTERING_PORT);
    }

    @Override
    void handleOutput(String output, SocketChannel client) {
        try {
            int signInData = DatabaseManager.getSignInData(output);
            String[] loginCredentials = output.split(NetworkingSettings.SENDING_SEPARATOR_REGEX);
            int userId;
            if (signInData == 2) {
                userId = DatabaseManager.registerAccount(loginCredentials[0], loginCredentials[1]);
            } else if (signInData == 1){
                userId = DatabaseManager.getUserId(loginCredentials[0]);
            } else {
                userId = -1;
            }
            writeToClient(String.valueOf(userId), client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
