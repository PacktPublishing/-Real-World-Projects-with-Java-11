package global;

public interface NetworkingSettings {
    int SENDING_PORT = 5000;
    int REGISTERING_PORT = 5001;
    int CHAT_RETRIEVING_PORT = 5002;
    int CHAT_MANAGING_PORT = 5003;
    int MESSAGE_LOADING_PORT = 5004;
    int PING_PORT = 5006;
    String END_OF_SERVER = "SERVER_KILL()";
    int MAX_PACKET_SIZE = 1024;
    String SENDING_SEPARATOR_REGEX = ":__:__:__:__:"; // example: userThatSends:userThatReceives:message
    String LIST_SEPARATOR_REGEX = "::~_~::";
    String MESSAGE_SEPARATOR_REGEX = "~~::~~";
    String START_OF_LIST = "^_^!";
    String END_OF_LIST = "!^_^";
    String SERVER_IP = "localhost";
    String SENT_MESSAGE = "sent";
    String RECEIVED_MESSAGE = "received";
    String DISCONNECT_STRING = "DISCONNECT_ME()";
    long MAX_PING = 15000;
}
