package app.networking;


import app.ApplicationUtils;
import server.database.dataStructures.MessageConstructor;
import global.NetworkingSettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatRetrievingClient extends Client{

    private final Map<Integer, String> chats;

    public ChatRetrievingClient(String ip) {
        super(ip, NetworkingSettings.CHAT_RETRIEVING_PORT);
        this.chats = new HashMap<>();
    }

    public void killServer() {
        sendMessage(NetworkingSettings.END_OF_SERVER);
    }

    public Map<Integer, String> retrieveChats() throws IOException {
        sendMessage(String.valueOf(ApplicationUtils.user));
        return MessageConstructor.disassembleChats(receiveMessage(), chats);
    }
}
