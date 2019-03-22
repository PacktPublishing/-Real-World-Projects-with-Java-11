package app.gui.main.customNodes.layouts.chat;

import app.gui.GUISettings;
import app.gui.main.customNodes.layouts.message.MessageLayout;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;

public class ChatLayout extends HBox {

    private static Map<Integer, ChatLayout> allLayouts = new HashMap<>();
    public static ChatLayout currentlySelectedChat = null;

    private String chatName;
    private int chatId;
    private String displayName;

    public ChatLayout(String chatName, int chatId) {
        super();
        this.chatName = chatName;
        this.chatId = chatId;
        this.displayName = chatName;

        setLooks();
        setOnClick();
        addChatAlphabetically(this);
    }

    private void setLooks() {
        getChildren().add(new Label(displayName + " :: " + chatId));
        setStyle(GUISettings.CHAT_STYLE);
    }

    private void setOnClick() {
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                onChatClicked(this, MessageLayout.displayedMessages);
            }
        });
    }

    private static void onChatClicked(ChatLayout chatClicked, Map<Integer, MessageLayout> messageList) {
        if (onChatClick(chatClicked)) {
            messageList.clear();
        }
    }

    private static boolean onChatClick(ChatLayout chatClicked) {
        if (chatClicked != currentlySelectedChat) {
            MessageLayout.lastMessageId = -1;
            MessageLayout.displayedMessages.clear();
            currentlySelectedChat = chatClicked;
            return true;
        }
        return false;
    }

    private static void addChatAlphabetically(ChatLayout chat) {
        if (allLayouts.isEmpty()) allLayouts.put(chat.chatId, chat);
        else {
            Map<Integer, ChatLayout> newMap = new HashMap<>();
            boolean added = false;
            for (ChatLayout layout:allLayouts.values()) {
                if (!added && biggerAlphabetically(chat.displayName.toUpperCase(), layout.displayName.toUpperCase())) {
                    newMap.put(layout.chatId, layout);
                } else {
                    if (!added) {
                        newMap.put(chat.chatId, chat);
                        added = true;
                    }
                    newMap.put(layout.chatId, layout);
                }
            }
            newMap.put(chat.chatId, chat);
            allLayouts = newMap;
        }
    }

    private static boolean biggerAlphabetically(String a, String b) {
        if (a.isEmpty()) return false;
        else if (b.isEmpty()) return true;
        else if (a.charAt(0) == b.charAt(0)) return biggerAlphabetically(a.substring(1), b.substring(1));
        return a.charAt(0) > b.charAt(0);
    }

    public static void onChatSearchChange(String chatSearch, ListView<ChatLayout> chatsList) {
        chatsList.getItems().clear();
        for (ChatLayout layout:allLayouts.values()) {
            if (layout.displayName.contains(chatSearch)) {
                chatsList.getItems().add(layout);
            }
        }
    }

    public static boolean canAddChat(String chatName) {
        for (ChatLayout chat:allLayouts.values()) {
            if (chat.chatName.equals(chatName)) return false;
        }
        return true;
    }

    public String getChatName() {
        return chatName;
    }
}