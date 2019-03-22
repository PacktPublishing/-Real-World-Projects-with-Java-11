package app.gui;

import javafx.scene.control.TextField;
import server.database.managing.DatabaseVariables;

public class GUIUtils {
    
    public static void addTextLimiter(final TextField textField) {
        addTextLimiter(textField, DatabaseVariables.MAX_FIELD_LENGTH);
    }
    
    private static void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
        });
    }
    
    public static void makeSQLSafe(final TextField textField) {
        textField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!isSQLSafe(newValue)) {
                textField.setText(oldValue);
            }
        });
    }

    private static boolean isSQLSafe(String text) {
        return !text.contains("-") && !text.contains("\\");
    }
}
