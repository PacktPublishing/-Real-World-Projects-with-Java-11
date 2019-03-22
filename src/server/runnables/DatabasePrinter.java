package server.runnables;

import server.database.DatabaseManager;

import java.sql.SQLException;

public class DatabasePrinter {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseManager.printDatabase();
    }
}
