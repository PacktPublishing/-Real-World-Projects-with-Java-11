package server.database;

import server.database.dataStructures.Message;
import global.NetworkingSettings;
import server.database.managing.DatabaseSearcher;
import server.database.managing.DatabaseUpdater;
import server.database.managing.DatabaseVariables;
import server.networking.ChatManagingServer;
import server.networking.SendingServer;

import java.sql.*;
import java.util.*;

public class DatabaseManager {

    private static Connection con;
    private static PreparedStatement st;

    public static void init() throws SQLException, ClassNotFoundException {
        con = DatabaseUtils.getConnection();
        DatabaseUtils.createUsersTable(con);
        DatabaseUtils.createMessagesTable(con);
        DatabaseUtils.createChatsTable(con);
    }

    static void pingDatabase() throws SQLException {
        st = con.prepareStatement(DatabaseSearcher.getUserQuery("="));
        st.executeQuery();
    }

    public static void cleanDatabase() throws SQLException, ClassNotFoundException {
        con = DatabaseUtils.getConnection();
        DatabaseUtils.dropTable(con, DatabaseVariables.USERS_TABLE);
        DatabaseUtils.dropTable(con, DatabaseVariables.CHATS_TABLE);
        DatabaseUtils.dropTable(con, DatabaseVariables.MESSAGES_TABLE);
        cleanUp();
    }

    public static void printDatabase() throws SQLException, ClassNotFoundException {
        con = DatabaseUtils.getConnection();
        DatabaseUtils.printTable(con, DatabaseVariables.USERS_TABLE);
        DatabaseUtils.printTable(con, DatabaseVariables.CHATS_TABLE);
        DatabaseUtils.printTable(con, DatabaseVariables.MESSAGES_TABLE);
        cleanUp();
    }

    public static int getSignInData(String output) { // 0: can't 1: logging in 2: registering
        String[] userParts = output.split(NetworkingSettings.SENDING_SEPARATOR_REGEX);
        try {
            return canSignIn(userParts[0], userParts[1]);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int canSignIn(String username, String password) throws SQLException {
        st = con.prepareStatement(DatabaseSearcher.getUserQuery(username));
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            rs.close();
            return 2;
        } else if (password.equals(rs.getString(DatabaseVariables.PASSWORD_COLUMN))) {
            rs.close();
            return 1;
        } else {
            rs.close();
            return 0;
        }
    }

    public static int getUserId(String username) {
        try {
            st = con.prepareStatement(DatabaseSearcher.getUserQuery(username));
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt(DatabaseVariables.USER_ID_COLUMN);
        } catch (SQLException e) {
            return -1;
        }
    }

    public static boolean userValidForChat(String senderId, String receiver) {
        int receiverId = getUserId(receiver);
        return receiverId != Integer.valueOf(senderId) && userExists(receiverId);
    }

    private static boolean userExists(int userId) {
        return userId != -1;
    }

    private static String getUsername(String id) {
        return getUsername(Integer.valueOf(id));
    }

    private static String getUsername(int id) {
        try {
            st = con.prepareStatement(DatabaseSearcher.getUserQuery(id));
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getString(DatabaseVariables.USERNAME_COLUMN);
        } catch (SQLException e) {
            e.printStackTrace();
            return "-notBugFeature-";
        }
    }

    public static int registerAccount(String username, String password) {
        try {
            return addUserToDatabase(username, password);
        } catch (IndexOutOfBoundsException | SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void updateMessages(SendingServer server) {
        updateMessages(server.getPendingStrings());
    }

    private static void updateMessages(List<String> pendingStrings) {
        for (String message:pendingStrings) {
            try {
                String[] messageParts = message.split(NetworkingSettings.SENDING_SEPARATOR_REGEX);
                addMessageToDatabase(String.valueOf(messageParts[0]), String.valueOf(getUserId(messageParts[1])), messageParts[2]);
            } catch (IndexOutOfBoundsException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void manageChats(ChatManagingServer server) {
        manageChats(server.getPendingStrings());
    }

    private static void manageChats(List<String> pendingStrings) {
        for (String chat:pendingStrings) {
            try {
                String[] chatParts = chat.split(NetworkingSettings.SENDING_SEPARATOR_REGEX);
                int receiverId = getUserId(chatParts[1]);
                if (userExists(receiverId) && Integer.valueOf(chatParts[0]) != receiverId)
                    addChatToDatabase(chatParts[0], String.valueOf(receiverId));
            } catch (IndexOutOfBoundsException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<Integer, String> retrieveChats(String chattingUser1) throws SQLException {
        st = con.prepareStatement(DatabaseSearcher.getChatsQuery(chattingUser1));
        ResultSet rs = st.executeQuery();
        Map<Integer, String> ret = new HashMap<>();
        while (rs.next()) {
            String chatter1 = rs.getString(DatabaseVariables.CHATTER1_COLUMN);
            String chatter2 = rs.getString(DatabaseVariables.CHATTER2_COLUMN);
            ret.put(rs.getInt(DatabaseVariables.CHAT_ID_COLUMN), chatter1.equals(chattingUser1) ?
                    getUsername(chatter2) : getUsername(chatter1));
        }
        rs.close();
        return ret;
    }

    public static List<Message> retrieveAllMessages(String message) throws SQLException {
        String[] messageParts = message.split(NetworkingSettings.SENDING_SEPARATOR_REGEX);
        return retrieveAllMessages(messageParts[0], String.valueOf(getUserId(messageParts[1])), messageParts[2]);
    }

    private static List<Message> retrieveAllMessages(String sender, String receiver, String lastMessageId)
            throws SQLException {
        st = con.prepareStatement(DatabaseSearcher.getAllMessagesOfChatQuery(sender, receiver, lastMessageId));
        ResultSet rs = st.executeQuery();
        List<Message> ret = new ArrayList<>();
        while (rs.next()) {
            ret.add(new Message(rs.getInt(DatabaseVariables.SENDER_COLUMN) == Integer.valueOf(sender),
                    rs.getString(DatabaseVariables.MESSAGE_COLUMN),
                    rs.getInt(DatabaseVariables.MESSAGE_ID_COLUMN)));
        }
        rs.close();
        return ret;
    }

    private static int addUserToDatabase(String username, String password) throws SQLException {
        st = con.prepareStatement(DatabaseUpdater.addUserQuery(username, password));
        st.executeUpdate();
        return getUserId(username);
    }

    private static void addMessageToDatabase(String sendingUser, String receivingUser, String message) throws SQLException {
        st = con.prepareStatement(DatabaseUpdater.addMessageToChatQuery(sendingUser, receivingUser, message));
        st.executeUpdate();
    }

    private static void addChatToDatabase(String chattingUser1, String chattingUser2) throws SQLException {
        st = con.prepareStatement(DatabaseUpdater.addChatQuery(chattingUser1, chattingUser2));
        st.executeUpdate();
    }

    public static void cleanUp() {
        if (st != null)
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static boolean isConnectedToDatabase(){
        return con != null;
    }
}
