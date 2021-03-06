package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.models.EnumCommand;
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

            view.showMessage(view.getTranslation("LOOK_SUCCESS", model.getCurrentImage().getName(), model.getFiltersAsList().stream().map(Filter::getName).collect(Collectors.joining(", "))));
            new ListCacheCommand().run(model, view, controller, command);
            return false;
        }
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> run(model, view, controller, new Command(EnumCommand.LOOK, view.getBundle()));
    }
}
