package app.gui.popups;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class CustomPopupWindow {

    private URL fxmlPath;
    private String title;

    protected CustomPopupWindow(URL fxmlPath, String title) {
        this.fxmlPath = fxmlPath;
        this.title = title;
    }

    protected void createWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(fxmlPath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        viewManaging(root, primaryStage);
    }

    protected abstract void viewManaging(Parent root, Stage primaryStage);

    protected static void closeWindow(Stage stage) {
        stage.close();
    }
}
