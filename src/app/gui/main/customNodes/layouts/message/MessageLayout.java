package app.gui.main.customNodes.layouts.message;

import app.gui.GUISettings;
import global.NetworkingSettings;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageLayout extends HBox {

    public static int lastMessageId = -1;
    public static Map<Integer, MessageLayout> displayedMessages = new HashMap<>();

    private int messageId;

    public MessageLayout(String toParse) {
        this(Integer.parseInt(toParse.split(NetworkingSettings.MESSAGE_SEPARATOR_REGEX)[2]),
                toParse.split(NetworkingSettings.MESSAGE_SEPARATOR_REGEX)[0].startsWith(NetworkingSettings.SENT_MESSAGE),
                toParse.split(NetworkingSettings.MESSAGE_SEPARATOR_REGEX)[1]);
    }

    private MessageLayout(int messageId, boolean sent, String message) {
        super(new Label(message + " ID: " + messageId + " Sent: " + sent));
        this.messageId = messageId;
        lastMessageId = messageId;

        setLooks();
        addMessageById(this);
    }

    private void setLooks() {
        setStyle(GUISettings.MESSAGE_STYLE);
    }

    private static void addMessageById(MessageLayout message) {
        if (displayedMessages.isEmpty()) {
            displayedMessages.put(message.messageId, message);
        } else {
            Map<Integer, MessageLayout> newMap = new HashMap<>();
            List<MessageLayout> mapLayouts = new ArrayList<>(displayedMessages.values());
            boolean added = false;
            for (int i = 0; i < mapLayouts.size(); i++) {
                newMap.put(mapLayouts.get(i).messageId, mapLayouts.get(i));
                if (i + 1 == mapLayouts.size()) {
                    newMap.put(message.messageId, message);
                    break;
                } else if (!added && mapLayouts.get(i).messageId < message.messageId && mapLayouts.get(i + 1).messageId > message.messageId) {
                    newMap.put(message.messageId, message);
                    added = true;
                }
            }
            displayedMessages = newMap;
        }
    }

    public int getMessageId() {
        return messageId;
    }
}
