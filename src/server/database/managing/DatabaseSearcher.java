package server.database.managing;

public class DatabaseSearcher {

    public static String getAllMessagesOfChatQuery(String sender, String receiver, String lastMessageId) {
        return "SELECT " + DatabaseVariables.MESSAGE_COLUMN + ", " + DatabaseVariables.MESSAGE_ID_COLUMN + ", " +
                DatabaseVariables.SENDER_COLUMN + " FROM " + DatabaseVariables.MESSAGES_TABLE + " WHERE (" +
                DatabaseVariables.MESSAGE_ID_COLUMN + " > " + lastMessageId + ") AND (" +
                DatabaseVariables.SENDER_COLUMN + " = " + sender + " AND (" + DatabaseVariables.RECEIVER_COLUMN +
                " = " + receiver + ") OR (" + DatabaseVariables.SENDER_COLUMN + " = " + receiver + " AND " +
                DatabaseVariables.RECEIVER_COLUMN + " = " + sender + "))";
    }

    public static String getChatsQuery(String chattingUser1) {
        return "SELECT * FROM " + DatabaseVariables.CHATS_TABLE +
                " WHERE " + DatabaseVariables.CHATTER1_COLUMN + " = \"" + chattingUser1 + "\" OR " +
                DatabaseVariables.CHATTER2_COLUMN + " = \"" + chattingUser1 + "\"";
    }

    public static String getUserQuery(String username) {
        return "SELECT " + DatabaseVariables.USERNAME_COLUMN + ", " + DatabaseVariables.PASSWORD_COLUMN + ", " +
                DatabaseVariables.USER_ID_COLUMN + " FROM " + DatabaseVariables.USERS_TABLE + " WHERE " +
                DatabaseVariables.USERNAME_COLUMN + " = \"" + username + "\"";
    }

    public static String getUserQuery(int userId) {
        return "SELECT " + DatabaseVariables.USERNAME_COLUMN + ", " + DatabaseVariables.USER_ID_COLUMN + " FROM " +
                DatabaseVariables.USERS_TABLE + " WHERE " + DatabaseVariables.USER_ID_COLUMN + " = " + userId;
    }
}
