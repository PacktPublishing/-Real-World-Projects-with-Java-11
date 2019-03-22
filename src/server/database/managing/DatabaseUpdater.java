package server.database.managing;

public class DatabaseUpdater {

    public static String addUserQuery(String username, String password) {
        return "INSERT INTO " + DatabaseVariables.USERS_TABLE + DatabaseVariables.USERS_TABLE_COLUMNS +
                " VALUES " + "(\"" + username + "\", \"" + password + "\")";
    }

    public static String addMessageToChatQuery(String sender, String receiver, String message) {
        return "INSERT INTO " + DatabaseVariables.MESSAGES_TABLE + DatabaseVariables.MESSAGES_TABLE_COLUMNS +
                " VALUES " + "(" + sender + ", " + receiver + ", \"" + message + "\")";
    }

    public static String addChatQuery(String chattingUser1, String chattingUser2) {
        return "INSERT INTO " + DatabaseVariables.CHATS_TABLE + DatabaseVariables.CHATS_TABLE_COLUMNS +
                " VALUES " + "(" + chattingUser1 + ", " + chattingUser2 + ")";
    }
}
