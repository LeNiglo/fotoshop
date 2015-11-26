package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.EditorView;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by leniglo on 20/11/15.
 */
public class SaveCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (model.getCurrentImage() == null) {
            view.showError(view.getTranslation("NO_PIC"));
            return false;
        }
        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("SAVE_ERROR"));
            return false;
        }

        try {
            FileOutputStream outputFile = new FileOutputStream(command.getWord(1));
            ImageIO.write(model.getCurrentImage(), "jpg", outputFile);
            view.showMessage(view.getTranslation("SAVE_SUCCESS", command.getWord(1)));
        } catch (SecurityException | IOException e) {
            view.showError(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean undo(Editor model, EditorView view, EditorController controller) {
        return false;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return null;
    }
}
