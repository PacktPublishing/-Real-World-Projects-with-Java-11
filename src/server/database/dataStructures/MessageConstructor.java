package server.database.dataStructures;

import global.NetworkingSettings;

import java.util.List;
import java.util.Map;

public class MessageConstructor {

    public static Map<Integer, String> disassembleChats(String chats, Map<Integer, String> chatList) {
        chatList.clear();
        chats = chats.substring(NetworkingSettings.START_OF_LIST.length(), chats.length() -
                NetworkingSettings.END_OF_LIST.length());
        String[] chatsArray = chats.split(NetworkingSettings.LIST_SEPARATOR_REGEX);
        for (String chat:chatsArray) {
            if (!chat.isEmpty()) {
                String[] chatParts = chat.split(NetworkingSettings.SENDING_SEPARATOR_REGEX);
                chatList.put(Integer.parseInt(chatParts[0]), chatParts[1]);
            }
        }
        return chatList;
    }

    public static String assembleStrings(Map<Integer, String> strings) {
        StringBuilder ret = new StringBuilder();
        for (Map.Entry chat:strings.entrySet()) {
            ret.append(NetworkingSettings.LIST_SEPARATOR_REGEX).append(chat.getKey())
                    .append(NetworkingSettings.SENDING_SEPARATOR_REGEX).append(chat.getValue());
        }

        return NetworkingSettings.START_OF_LIST + ret + NetworkingSettings.END_OF_LIST;
    }

    public static String assembleMessages(List<Message> messages) {
        StringBuilder ret = new StringBuilder();
        for (Message message:messages) {
            ret.append(message.toString()).append(NetworkingSettings.LIST_SEPARATOR_REGEX);
        }
        return NetworkingSettings.START_OF_LIST + ret + NetworkingSettings.END_OF_LIST;
    }
}
