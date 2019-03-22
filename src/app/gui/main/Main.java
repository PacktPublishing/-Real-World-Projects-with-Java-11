package app.gui.main;

import app.ApplicationUtils;
import app.gui.GUISettings;
import app.gui.GUIUtils;
import app.gui.popups.login.LoginWindow;
import app.gui.main.controls.CenterController;
import app.gui.main.controls.LeftController;
import app.gui.main.controls.TopController;
import app.gui.main.customNodes.layouts.chat.ChatLayout;
import app.gui.main.customNodes.layouts.message.MessageLayout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    public TextField chatSearch;
    public TextField messageBox;
    public ListView<ChatLayout> chatsList;
    public ListView<MessageLayout> messageList;
    public Label serverStatus;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle(GUISettings.APPLICATION_NAME);
        primaryStage.setScene(new Scene(root, GUISettings.SCREEN_WIDTH, GUISettings.SCREEN_HEIGHT));

        controlSetup(root);
        LoginWindow.createWindow(primaryStage);
        ApplicationUtils.init();
        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(GUISettings.CHAT_UPDATE_INTERVAL), event ->
                update()));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    @SuppressWarnings("unchecked")
    private void controlSetup(Parent root) {
        chatSearch = (TextField) root.lookup("#chatSearch");
        GUIUtils.addTextLimiter(chatSearch);
        GUIUtils.makeSQLSafe(chatSearch);

        messageBox = (TextField) root.lookup("#messageBox");
        GUIUtils.addTextLimiter(messageBox);
        GUIUtils.makeSQLSafe(messageBox);

        chatsList = (ListView) root.lookup("#chatsList");
        messageList = (ListView) root.lookup("#messageList");
        serverStatus = TopController.getServerStatus(root);
    }

    public void onSearchChange() {
        LeftController.onSearchChange(chatSearch, chatsList);
    }

    public void sendMessageCheck(KeyEvent keyEvent) {
        CenterController.sendMessageCheck(keyEvent, messageBox);
    }

    public void addChatCheck() {
        TopController.createChatDialog();
    }

    private void updateFields() {
        messageBox.setDisable(ChatLayout.currentlySelectedChat == null);
    }

    private void update() {
        ApplicationUtils.update();
        updateFields();
        new Thread(LeftController::updateChats).start();
        LeftController.updateChatsUI(chatsList, chatSearch);
        if (ChatLayout.currentlySelectedChat != null) {
            new Thread(() ->
                    ApplicationUtils.messageLoaderClient.loadMessages(
                            ChatLayout.currentlySelectedChat.getChatName()))
                    .start();
        }
        CenterController.updateMessages(messageList);
        serverStatus.setText(String.valueOf(ApplicationUtils.isConnectedToServer()));
    }

    @Override
    public void stop() {
        ApplicationUtils.cleanUp();
    }
}
