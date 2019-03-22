package server.networking;

import server.database.dataStructures.Message;
import server.database.dataStructures.MessageConstructor;
import global.NetworkingSettings;
import server.database.DatabaseManager;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.List;

public class MessageLoaderServer extends Server {

    public MessageLoaderServer() throws IOException {
        super(NetworkingSettings.MESSAGE_LOADING_PORT);
    }

    @Override
    void handleOutput(String output, SocketChannel client) {
        try {
            List<Message> toSendMessages = DatabaseManager.retrieveAllMessages(output);
            String toSendString = MessageConstructor.assembleMessages(toSendMessages);
            writeToClient(toSendString, client);
        } catch (SQLException | NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
}
