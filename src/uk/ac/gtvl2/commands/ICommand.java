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
public interface ICommand {
    public boolean run(Editor model, EditorView view, EditorController controller, Command command);

    public boolean undo(Editor model, EditorView view, EditorController controller);

    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller);
}
