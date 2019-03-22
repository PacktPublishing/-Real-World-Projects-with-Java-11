package app.networking;

import global.NetworkingSettings;

import java.io.IOException;

public class RegisteringClient extends Client{

    private int userId;

    public RegisteringClient(String ip) {
        super(ip, NetworkingSettings.REGISTERING_PORT);
    }

    public boolean canLogIn(){
        try {
            userId = Integer.valueOf(receiveMessage());
            return userId != -1;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void register(String username, String password) {
        sendMessage(username + NetworkingSettings.SENDING_SEPARATOR_REGEX + password);
    }

    public int getUserId() {
        return userId;
    }
}
