package uk.ac.gtvl2.commands;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.EditorView;

import java.util.Optional;

/**
 * Created by leniglo on 20/11/15.
 */
public class QuitCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        return true;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(view.getTranslation("QUIT"));
                alert.setHeaderText(null);
                alert.setContentText(view.getTranslation("QUIT_CONFIRM"));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    view.stop();
                    Platform.exit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
