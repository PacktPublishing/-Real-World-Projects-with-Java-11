package app.gui.main.controls;

import app.ApplicationUtils;
import app.gui.main.customNodes.layouts.chat.ChatLayout;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LeftController {

    private static Map<Integer, String> chats = new HashMap<>();

    public static void onSearchChange(TextField chatSearch, ListView<ChatLayout> chatsList) {
        ChatLayout.onChatSearchChange(chatSearch.getText(), chatsList);
    }

    public static void updateChats() {
        try {
            chats = ApplicationUtils.chatRetrievingClient.retrieveChats();
        } catch (IOException e) {
            System.out.println("Couldn't retrieve chats");
        }
    }

    public static void updateChatsUI(ListView<ChatLayout> chatsList, TextField chatSearch) {
        for (Map.Entry<Integer, String> chat:chats.entrySet()) {
            addChat(chat.getValue(), chat.getKey(), chatsList, chatSearch);
        }
    }

    private static void addChat(String chatName, int chatId, ListView<ChatLayout> chatsList, TextField chatSearch) {
        if (ChatLayout.canAddChat(chatName)) {
            new ChatLayout(chatName, chatId);
            chatSearch.clear();
            ChatLayout.onChatSearchChange("", chatsList);
        }
    }
}
