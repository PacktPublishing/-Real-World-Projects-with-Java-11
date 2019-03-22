package app.gui.popups.login;

import app.ApplicationUtils;
import app.gui.GUISettings;
import app.gui.GUIUtils;
import app.gui.popups.CustomPopupWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginWindow extends CustomPopupWindow {

    private Stage original;

    public static void createWindow(Stage original) {
        new LoginWindow(original).createWindow();
    }

    private LoginWindow(Stage original) {
        super(LoginWindow.class.getResource("login.fxml"), "Register / Log In");
        this.original = original;
    }

    @Override
    protected void viewManaging(Parent root, Stage primaryStage) {
        Button create = (Button) root.lookup("#login");
        TextField username = (TextField) root.lookup("#username");
        GUIUtils.addTextLimiter(username);
        GUIUtils.makeSQLSafe(username);
        PasswordField password = (PasswordField) root.lookup("#password");
        GUIUtils.addTextLimiter(password);
        GUIUtils.makeSQLSafe(password);
        Label serverStatus = (Label) root.lookup("#serverStatus");
        Label loginStatus = (Label) root.lookup("#loginStatus");

        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(GUISettings.CHAT_UPDATE_INTERVAL), event ->
                update(serverStatus, username, password, create)));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

        create.setOnAction(event -> {
            if (register(username.getText(), password.getText())) {
                timeLine.stop();
                closeWindow(primaryStage);
                original.show();
            } else {
                loginStatus.setText("Incorrect credentials");
            }
        });
    }

    private static void update(Label status, Node... nodes){
        if (ApplicationUtils.isConnectedToServer()) {
            status.setText("Connected to server.");
            for (Node node:nodes)
                node.setDisable(false);
        } else {
            status.setText("Connecting to server...");
            for (Node node:nodes)
                node.setDisable(true);
        }
    }

    private static boolean register(String username, String password) {
        if (username.length() == 0 || password.length() == 0) return false;
        ApplicationUtils.registeringClient.register(username, password);
        if (ApplicationUtils.registeringClient.canLogIn()) {
            ApplicationUtils.user = ApplicationUtils.registeringClient.getUserId();
            return true;
        }
        return false;
    }
}
