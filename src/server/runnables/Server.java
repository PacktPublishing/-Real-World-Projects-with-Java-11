package server.runnables;

import server.database.DatabaseKeepUp;
import server.database.DatabaseManager;
import server.networking.*;

import java.io.IOException;
import java.sql.SQLException;

public class Server {

    private static RegisteringServer registeringServer;
    private static SendingServer sendingServer;
    private static ChatRetrievingServer chatRetrievingServer;
    private static ChatManagingServer chatManagingServer;
    private static MessageLoaderServer messageLoaderServer;
    private static PingServer pingServer;
    private static DatabaseKeepUp databaseKeepUp;
    private static boolean connectedToDatabase = false;

    private static void init() {
        try {
            DatabaseManager.init();
            registeringServer = new RegisteringServer();
            registeringServer.start();
            sendingServer = new SendingServer();
            sendingServer.start();
            chatRetrievingServer = new ChatRetrievingServer();
            chatRetrievingServer.start();
            chatManagingServer = new ChatManagingServer();
            chatManagingServer.start();
            messageLoaderServer = new MessageLoaderServer();
            messageLoaderServer.start();
            pingServer = new PingServer();
            pingServer.start();
            databaseKeepUp = new DatabaseKeepUp();
            databaseKeepUp.start();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            cleanUp();
        }
    }

    private static void update(){
        DatabaseManager.updateMessages(sendingServer);
        DatabaseManager.manageChats(chatManagingServer);
        connectedToDatabase = DatabaseManager.isConnectedToDatabase();
    }

    private static void cleanUp() {
        try {
            registeringServer.cleanUp();
            System.out.println("Cleaned registeringServer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sendingServer.cleanUp();
            System.out.println("Cleaned sendingServer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            chatRetrievingServer.cleanUp();
            System.out.println("Cleaned chatRetrievingServer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            chatManagingServer.cleanUp();
            System.out.println("Cleaned chatManagingServer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            messageLoaderServer.cleanUp();
            System.out.println("Cleaned messageLoaderServer");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            pingServer.cleanUp();
        } catch (IOException e) {
            System.out.println("Cleaned pingServer");
        }

        databaseKeepUp.cleanUp();
        System.out.println("Cleaned databaseKeepUp");
        DatabaseManager.cleanUp();
    }

    public static boolean isConnectedToDatabase() {
        return connectedToDatabase;
    }

    public static void main(String[] args) throws InterruptedException {
        Server.init();
        while (registeringServer.isAlive() && sendingServer.isAlive() && chatRetrievingServer.isAlive() &&
                chatManagingServer.isAlive() && messageLoaderServer.isAlive() && pingServer.isAlive() &&
                databaseKeepUp.isAlive()) {
            Server.update();
            Thread.sleep(10);
        }
        Server.cleanUp();
    }
}
