package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.*;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.awt.geom.AffineTransform;

/**
 * Created by leniglo on 20/11/15.
 */
public class FlipVCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (model.getCurrentImage() == null) {
            view.showError(view.getTranslation("NO_PIC"));
            return false;
        }
        model.pushFilter(new Filter(model.getCurrentImage(), view.getTranslation("FLIPV")));
        model.setCurrentImage(flipV(model));
        if (!view.isConsole())
            ((GuiView) view).update();
        return false;
    }

    private ColorImage flipV(Editor model) {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-model.getCurrentImage().getWidth(), 0));
        return Editor.createTransformed(model.getCurrentImage(), at);
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> run(model, view, controller, new Command(EnumCommand.FLIPV, view.getBundle()));
    }
}
