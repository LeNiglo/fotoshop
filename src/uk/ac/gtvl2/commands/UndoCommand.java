package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.models.EnumCommand;
import uk.ac.gtvl2.models.Filter;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

/**
 * Created by leniglo on 28/11/15.
 */
public class UndoCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (model.getFiltersAsList().size() > 0) {
            Filter previous = model.popFilter();

            model.setCurrentImage(previous.getPreviousImage());
            if (!view.isConsole()) {
                ((GuiView) view).update();
            }
            return false;
        } else {
            view.showError(view.getTranslation("UNDO_ERROR"));
            return false;
        }
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> run(model, view, controller, new Command(EnumCommand.UNDO, view.getBundle()));
    }
}
