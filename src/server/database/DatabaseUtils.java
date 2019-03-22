package server.database;

import server.database.managing.DatabaseVariables;

import java.sql.*;

class DatabaseUtils {
    static Connection getConnection() throws ClassNotFoundException, SQLException {
        String driver = DatabaseSettings.DRIVER;
        String url = DatabaseSettings.DATABASE_URL;
        String username = DatabaseSettings.DATABASE_USERNAME;
        String password = DatabaseSettings.DATABASE_PASSWORD;
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        System.out.println("Connected");
        return con;
    }

    static void createUsersTable(Connection con) {
        createTable(con, DatabaseVariables.USERS_TABLE, DatabaseVariables.USER_ID_COLUMN + " int NOT NULL AUTO_INCREMENT, " +
                DatabaseVariables.USERNAME_COLUMN + " varchar(255)," +
                DatabaseVariables.PASSWORD_COLUMN + " varchar(255), PRIMARY KEY(" + DatabaseVariables.USER_ID_COLUMN + ")");
    }

    static void createMessagesTable(Connection con) {
        createTable(con, DatabaseVariables.MESSAGES_TABLE, DatabaseVariables.MESSAGE_ID_COLUMN +
                " int NOT NULL AUTO_INCREMENT, " + DatabaseVariables.SENDER_COLUMN + " int, " +
                DatabaseVariables.RECEIVER_COLUMN + " int, " + DatabaseVariables.MESSAGE_COLUMN +
                " varchar(255), PRIMARY KEY(" + DatabaseVariables.MESSAGE_ID_COLUMN + ")");
    }

    static void createChatsTable(Connection con) {
        createTable(con, DatabaseVariables.CHATS_TABLE, DatabaseVariables.CHAT_ID_COLUMN + " int NOT NULL AUTO_INCREMENT, " +
                DatabaseVariables.CHATTER1_COLUMN + " int, " + DatabaseVariables.CHATTER2_COLUMN +
                " int, PRIMARY KEY(" + DatabaseVariables.CHAT_ID_COLUMN + ")");
    }

    private static void createTable(Connection con, String tableName, String columns) {
        try {
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName +
                    "(" + columns + ")");
            create.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("table created / already exists.");
        }
    }

    static void dropTable(Connection con, String tableName) {
        try {
            PreparedStatement drop = con.prepareStatement("DROP TABLE IF EXISTS " + tableName);
            drop.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("table dropped / never existed.");
        }
    }

    static void printTable(Connection con, String tableName) {
        System.out.println("Printing table: " + tableName);
        try {
            PreparedStatement print = con.prepareStatement("SELECT * FROM " + tableName);
            ResultSet rs = print.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for(int i = 1 ; i <= columnsNumber; i++){
                    System.out.print(rs.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("__________________________________________");
    }
}
