package app.gui.popups.chats;

import app.ApplicationUtils;
import app.gui.GUIUtils;
import app.gui.popups.CustomPopupWindow;
import global.NetworkingSettings;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddChatWindow extends CustomPopupWindow {

    public static void createWindowSafe() {
        new AddChatWindow().createWindow();
    }

    private AddChatWindow() {
        super(AddChatWindow.class.getResource("addChat.fxml"), "Add Chat");
    }

    @Override
    protected void viewManaging(Parent root, Stage primaryStage) {
        Button cancel = (Button) root.lookup("#cancel");
        Button create = (Button) root.lookup("#create");
        TextField otherUser = (TextField) root.lookup("#user");
        Label chatStatus = (Label) root.lookup("#chatStatus");
        GUIUtils.addTextLimiter(otherUser);
        GUIUtils.makeSQLSafe(otherUser);

        cancel.setOnAction(event -> closeWindow(primaryStage));
        create.setOnAction(event -> {
            if (otherUser.getText().length() > 0) {
                if (addChat(otherUser.getText(), chatStatus)) {
                    closeWindow(primaryStage);
                }
            }
        });
    }

    private static boolean addChat(String chattingUser2, Label chatStatus) {
        try {
            String status = ApplicationUtils.chatManagingClient.addChat(chattingUser2);
            if (!status.equals(NetworkingSettings.SENDING_SEPARATOR_REGEX)) {
                chatStatus.setText("Can't chat with " + chattingUser2);
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            chatStatus.setText("An error occurred");
            return false;
        }
    }
}
