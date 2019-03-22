package app;

import app.networking.*;
import global.NetworkingSettings;

import java.io.IOException;
import java.util.Random;

public class ApplicationUtils {
    public static int user;
    public static RegisteringClient registeringClient;
    public static SendingClient sendingClient;
    public static ChatRetrievingClient chatRetrievingClient;
    public static ChatManagingClient chatManagingClient;
    public static MessageLoaderClient messageLoaderClient;
    private static PingClient pingClient;
    private static boolean connectedToServer = false;

    @SuppressWarnings("SpellCheckingInspection")
    public static void init() {
        Random r = new Random();
        user = r.nextInt();
        registeringClient = new RegisteringClient(NetworkingSettings.SERVER_IP);
        sendingClient = new SendingClient(NetworkingSettings.SERVER_IP);
        chatRetrievingClient = new ChatRetrievingClient(NetworkingSettings.SERVER_IP);
        chatManagingClient = new ChatManagingClient(NetworkingSettings.SERVER_IP);
        messageLoaderClient = new MessageLoaderClient(NetworkingSettings.SERVER_IP);
        pingClient = new PingClient(NetworkingSettings.SERVER_IP);
    }

    public static void update(){
        try {
            registeringClient.connect();
            sendingClient.connect();
            chatRetrievingClient.connect();
            chatManagingClient.connect();
            messageLoaderClient.connect();
            pingClient.connect();
            connectedToServer = pingClient.pingServer() < NetworkingSettings.MAX_PING;
        } catch (IOException e) {
            e.printStackTrace();
            connectedToServer = false;
        }
    }

    public static void cleanUp() {
        try {
            registeringClient.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sendingClient.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            chatRetrievingClient.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            chatManagingClient.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            messageLoaderClient.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            pingClient.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnectedToServer() {
        return connectedToServer;
    }
}
