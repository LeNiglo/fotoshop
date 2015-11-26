package uk.ac.gtvl2.commands;

import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.models.Filter;
import uk.ac.gtvl2.views.EditorView;

import java.util.stream.Collectors;

/**
 * Created by leniglo on 20/11/15.
 */
public class LookCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (model.getCurrentImage() == null) {
            view.showMessage(view.getTranslation("LOOK_NULL"));
            return false;
        } else {

            view.showMessage(view.getTranslation("LOOK_SUCCESS", model.getCurrentImage().getName(), model.getFilters().stream().map(Filter::getName).collect(Collectors.joining(", "))));
            new ListCacheCommand().run(model, view, controller, command);
            return false;
        }
    }

    @Override
    public boolean undo(Editor model, EditorView view, EditorController controller) {
        return false;
    }
}
