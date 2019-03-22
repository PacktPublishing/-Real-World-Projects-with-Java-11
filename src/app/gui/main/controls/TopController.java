package app.gui.main.controls;

import app.gui.popups.chats.AddChatWindow;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopController {

    public static void createChatDialog() {
        AddChatWindow.createWindowSafe();
    }

    public static Label getServerStatus(Parent root) {
        HBox top = (HBox) root.getChildrenUnmodifiable().get(0);
        return (Label) top.getChildrenUnmodifiable().get(1);
    }
}
