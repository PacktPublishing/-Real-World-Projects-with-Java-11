package server.database.dataStructures;

import global.NetworkingSettings;

public class Message {
    private final boolean sent;
    private final String message;
    private final int id;

    public Message(boolean sent, String message, int id) {
        this.sent = sent;
        this.id = id;
        this.message = message;
    }

    @Override
    public String toString() {
        return (sent ? NetworkingSettings.SENT_MESSAGE : NetworkingSettings.RECEIVED_MESSAGE) +
                NetworkingSettings.MESSAGE_SEPARATOR_REGEX + message + NetworkingSettings.MESSAGE_SEPARATOR_REGEX + id;
    }
}
