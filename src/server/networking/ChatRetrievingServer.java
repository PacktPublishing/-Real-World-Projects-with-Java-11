package server.networking;

import server.database.dataStructures.MessageConstructor;
import global.NetworkingSettings;
import server.database.DatabaseManager;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Map;

public class ChatRetrievingServer extends Server {

    public ChatRetrievingServer() throws IOException {
        super(NetworkingSettings.CHAT_RETRIEVING_PORT);
    }

    @Override
    void handleOutput(String output, SocketChannel client) {
        try {
            Map<Integer, String> toSend = DatabaseManager.retrieveChats(output);
            String toSendString = MessageConstructor.assembleStrings(toSend);
            writeToClient(toSendString, client);
        } catch (SQLException | NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
}
