package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.EditorView;

/**
 * Created by leniglo on 20/11/15.
 */
public class RemoveCacheCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("REMOVE_ERROR"));
        }
        if (!model.removeCache(command.getWord(1))) {
            view.showError(view.getTranslation("REMOVE_ERROR"));
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
