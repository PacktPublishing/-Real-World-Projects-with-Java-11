package server.database;

import java.sql.SQLException;

public class DatabaseKeepUp extends Thread {

    private boolean stop;

    public DatabaseKeepUp() {
        this.stop = false;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                DatabaseManager.pingDatabase();
                Thread.sleep(DatabaseSettings.PING_INTERVAL);
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cleanUp() {
        stop = true;
    }
}
