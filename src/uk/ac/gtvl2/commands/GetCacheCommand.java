package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Cache;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.models.Filter;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leniglo on 20/11/15.
 */
public class GetCacheCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("GET_ERROR1"));
            return false;
        }
        Cache cache = model.getCache(command.getWord(1));
        if (cache != null) {

            model.setCurrentImage(cache.getImage());
            model.setFilters(cache.getFilters());

            if (!view.isConsole())
                ((GuiView) view).update();
        } else {
            view.showError(view.getTranslation("GET_ERROR2"));
        }
        return false;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> {
            List<String> words = new ArrayList<>();
            words.add(view.getTranslation("GET"));
            words.add(((GuiView) view).getCacheSelected());
            run(model, view, controller, new Command(words, view.getBundle()));
            ((GuiView) view).clearCacheTextField();
        };
    }
}
