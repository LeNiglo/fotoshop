package uk.ac.gtvl2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;
import uk.ac.gtvl2.views.ConsoleView;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.util.Optional;

/**
 * Created by leniglo on 20/11/15.
 */
public class Main {
    public static void main(String[] args) {
        final String envConsole = System.getenv("CONSOLE");
        final boolean isConsole = envConsole != null && envConsole.equals("1");

        if (isConsole) {
            EditorView view = new ConsoleView();
        } else {
            Application.launch(GuiView.class, args);
        }

    }

    public static boolean exitRequested(EditorView view, WindowEvent event) {
        if (!view.isConsole()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(view.getTranslation("QUIT"));
            alert.setHeaderText(null);
            alert.setContentText(view.getTranslation("QUIT_CONFIRM"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    view.stop();
                    Platform.exit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            if (event != null)
                event.consume();
            return false;
        } else {
            return true;
        }
    }
}
