package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.*;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.awt.*;

/**
 * Created by leniglo on 20/11/15.
 */
public class Rot90Command implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (model.getCurrentImage() == null) {
            view.showError(view.getTranslation("NO_PIC"));
            return false;
        }
        model.pushFilter(new Filter(model.getCurrentImage(), view.getTranslation("ROT90")));
        model.setCurrentImage(rotate(model, 90));
        if (!view.isConsole())
            ((GuiView) view).update();
        return false;
    }

    private ColorImage rotate(Editor model, int degrees) {
        double rotation = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(rotation));
        double cos = Math.abs(Math.cos(rotation));
        int w = model.getCurrentImage().getWidth();
        int h = model.getCurrentImage().getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        ColorImage rotImage = new ColorImage(newWidth, newHeight);
        Graphics2D g = rotImage.createGraphics();
        g.translate((newWidth - w) / 2, (newHeight - h) / 2);
        g.rotate(rotation, w / 2, h / 2);
        g.drawRenderedImage(model.getCurrentImage(), null);
        g.dispose();
        return rotImage;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> run(model, view, controller, new Command(EnumCommand.ROT90, view.getBundle()));
    }
}
