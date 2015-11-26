package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.EditableImage;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.EditorView;

/**
 * Created by leniglo on 20/11/15.
 */
public class GetCacheCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("GET_ERROR"));
        }
        EditableImage image = model.getCache(command.getWord(1));
        if (image != null) {
            model.setCurrentImage(image);
        } else {
            view.showError(view.getTranslation("GET_ERROR"));
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
