package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.*;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.awt.*;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

/**
 * Created by leniglo on 28/11/15.
 */
public class InvertCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (model.getCurrentImage() == null) {
            view.showError(view.getTranslation("NO_PIC"));
            return false;
        }

        model.pushFilter(new Filter(model.getCurrentImage(), view.getTranslation("INVERT")));
        model.setCurrentImage(mono(model));
        if (!view.isConsole()) ((GuiView) view).update();
        return false;
    }

    private ColorImage mono(Editor model) {
        LookupTable lookup = new LookupTable(0, 3) {
            @Override
            public int[] lookupPixel(int[] src, int[] dest) {
                dest[0] = 255 - src[0];
                dest[1] = 255 - src[1];
                dest[2] = 255 - src[2];
                return dest;
            }
        };
        LookupOp op = new LookupOp(lookup, new RenderingHints(null));
        return Editor.createFiltered(model.getCurrentImage(), op);
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> run(model, view, controller, new Command(EnumCommand.MONO, view.getBundle()));
    }
}
