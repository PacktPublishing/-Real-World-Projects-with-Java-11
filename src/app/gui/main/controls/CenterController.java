package app.gui.main.controls;

import app.ApplicationUtils;
import app.gui.main.customNodes.layouts.chat.ChatLayout;
import app.gui.main.customNodes.layouts.message.MessageLayout;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ConcurrentModificationException;

public class CenterController {

    public static void sendMessageCheck(KeyEvent keyEvent, TextField field) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            ApplicationUtils.sendingClient.sendMessage(field.getText(),
                    ChatLayout.currentlySelectedChat.getChatName());
            field.clear();
        }
    }

    public static void updateMessages(ListView<MessageLayout> messages) {
        messages.getItems().clear();
        for (int messageId:MessageLayout.displayedMessages.keySet()) {
            messages.getItems().add(MessageLayout.displayedMessages.get(messageId));
        }
    }
}
