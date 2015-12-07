package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.controllers.Parser;
import uk.ac.gtvl2.models.*;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.awt.*;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;

/**
 * Created by leniglo on 28/11/15.
 */
public class MonoCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (model.getCurrentImage() == null) {
            view.showError(view.getTranslation("NO_PIC"));
            return false;
        }

        model.pushFilter(new Filter(model.getCurrentImage(), view.getTranslation("MONO")));
        model.setCurrentImage(mono(model));
        if (!view.isConsole()) ((GuiView) view).update();
        return false;
    }

    private ColorImage mono(Editor model) {
        LookupTable lookup = new LookupTable(0, 3) {
            @Override
            public int[] lookupPixel(int[] src, int[] dest) {
                int lum = Parser.safeLongToInt(Math.round(0.299 * src[0] + 0.587 * src[1] + 0.114 * src[2]));
                dest[0] = lum;
                dest[1] = lum;
                dest[2] = lum;
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
