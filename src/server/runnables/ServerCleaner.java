package server.runnables;

import server.database.DatabaseManager;

import java.sql.SQLException;

class ServerCleaner {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseManager.cleanDatabase();
    }
}
