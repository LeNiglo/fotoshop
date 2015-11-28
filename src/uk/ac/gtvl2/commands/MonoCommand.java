package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.controllers.Parser;
import uk.ac.gtvl2.models.*;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.awt.*;

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

        EditableImage saveImage = model.getCurrentImage();
        EditableImage tmpImage = new EditableImage(model.getCurrentImage());

        int height = tmpImage.getHeight();
        int width = tmpImage.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pix = tmpImage.getPixel(x, y);
                try {
                    int lum = Parser.safeLongToInt(Math.round(0.299 * pix.getRed()
                            + 0.587 * pix.getGreen()
                            + 0.114 * pix.getBlue()));
                    tmpImage.setPixel(x, y, new Color(lum, lum, lum));
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }

        model.pushFilter(new Filter(saveImage, EnumCommand.MONO.getText(view.getBundle())));
        model.setCurrentImage(tmpImage);
        if (!view.isConsole()) ((GuiView) view).updateCurrentImage();
        return false;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> run(model, view, controller, new Command(EnumCommand.MONO, view.getBundle()));
    }
}
