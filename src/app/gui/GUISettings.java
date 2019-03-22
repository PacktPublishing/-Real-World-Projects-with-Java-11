package app.gui;

public interface GUISettings {
    float CHAT_UPDATE_INTERVAL = 1;
    String APPLICATION_NAME = "UniChat";
    int SCREEN_WIDTH = 800;
    int SCREEN_HEIGHT = 500;
    String CHAT_STYLE = "-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: #FF0080;";
    String MESSAGE_STYLE = "-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: #FF0080;";
}
