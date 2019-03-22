package server.database.managing;

public interface DatabaseVariables {
    String USERS_TABLE = "users";
    String MESSAGES_TABLE = "messages";
    String CHATS_TABLE = "chats";
    String USERNAME_COLUMN = "username";
    String PASSWORD_COLUMN = "password";
    String USER_ID_COLUMN = "userId";
    String MESSAGE_ID_COLUMN = "messageId";
    String CHAT_ID_COLUMN = "chatId";
    String SENDER_COLUMN = "senderId";
    String RECEIVER_COLUMN = "receiverId";
    String MESSAGE_COLUMN = "message";
    String CHATTER1_COLUMN = "chatter1Id";
    String CHATTER2_COLUMN = "chatter2Id";
    String USERS_TABLE_COLUMNS = "(" + USERNAME_COLUMN + ", " + PASSWORD_COLUMN + ")";
    String MESSAGES_TABLE_COLUMNS = "(" + SENDER_COLUMN + ", " + RECEIVER_COLUMN + ", " + MESSAGE_COLUMN + ")";
    String CHATS_TABLE_COLUMNS = "(" + CHATTER1_COLUMN + ", " + CHATTER2_COLUMN + ")";
    int MAX_FIELD_LENGTH = 255; // from the varchar(255)
}
