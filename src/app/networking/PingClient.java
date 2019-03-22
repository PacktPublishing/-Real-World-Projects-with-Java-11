package app.networking;

import app.ApplicationUtils;
import global.NetworkingSettings;

import java.io.IOException;
import java.util.Date;

public class PingClient extends Client{
    public PingClient(String ip) {
        super(ip, NetworkingSettings.PING_PORT);
    }

    public long pingServer() throws IOException {
        long currentTime = new Date().getTime();
        sendMessage(String.valueOf(ApplicationUtils.user));
        return Long.parseLong(receiveMessage()) - currentTime;
    }
}
