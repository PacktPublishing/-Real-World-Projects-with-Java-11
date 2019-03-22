package app.networking;


import app.ApplicationUtils;
import app.gui.main.customNodes.layouts.message.MessageLayout;
import global.NetworkingSettings;

import java.io.IOException;

public class MessageLoaderClient extends Client {

    public MessageLoaderClient(String ip) {
        super(ip, NetworkingSettings.MESSAGE_LOADING_PORT);
    }

    public void loadMessages(String otherUser) {
        try {
            sendMessage(ApplicationUtils.user + NetworkingSettings.SENDING_SEPARATOR_REGEX + otherUser +
                    NetworkingSettings.SENDING_SEPARATOR_REGEX + MessageLayout.lastMessageId);
            String fromServer = receiveMessage();
            fromServer = fromServer.substring(NetworkingSettings.START_OF_LIST.length(),
                    fromServer.length() - NetworkingSettings.END_OF_LIST.length());
            String[] messages = fromServer.split(NetworkingSettings.LIST_SEPARATOR_REGEX);
            for (String message:messages) {
                if (!message.isEmpty()) {
                    new MessageLayout(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
