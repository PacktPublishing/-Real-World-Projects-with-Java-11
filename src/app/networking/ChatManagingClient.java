package app.networking;


import app.ApplicationUtils;
import global.NetworkingSettings;

import java.io.IOException;

public class ChatManagingClient extends Client{

    public ChatManagingClient(String ip) {
        super(ip, NetworkingSettings.CHAT_MANAGING_PORT);
    }

    public String addChat(String chattingUser2) throws IOException {
        sendMessage(ApplicationUtils.user + NetworkingSettings.SENDING_SEPARATOR_REGEX + chattingUser2);
        return receiveMessage();
    }
}
