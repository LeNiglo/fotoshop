package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.models.EnumCommand;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leniglo on 20/11/15.
 */
public class RemoveCacheCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("REMOVE_ERROR1"));
        }
        if (!model.removeCache(command.getWord(1))) {
            view.showError(view.getTranslation("REMOVE_ERROR2"));
        }
        if (!view.isConsole())
            ((GuiView) view).update();
        return false;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> {
            List<String> words = new ArrayList<>();
            words.add(EnumCommand.REMOVE.getText(view.getBundle()));
            words.add(((GuiView) view).getCacheSelected());
            run(model, view, controller, new Command(words, view.getBundle()));
            ((GuiView) view).clearCacheTextField();
        };
    }
}
