package app.networking;


import app.ApplicationUtils;
import global.NetworkingSettings;

public class SendingClient extends Client{

    public SendingClient(String ip) {
        super(ip, NetworkingSettings.SENDING_PORT);
    }

    public void sendMessage(String message, String toSendTo) {
        sendMessage(ApplicationUtils.user + NetworkingSettings.SENDING_SEPARATOR_REGEX +
                toSendTo + NetworkingSettings.SENDING_SEPARATOR_REGEX + message);
    }
}
