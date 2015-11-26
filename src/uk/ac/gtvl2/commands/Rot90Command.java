package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.EditableImage;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.models.Filter;
import uk.ac.gtvl2.views.EditorView;

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
        model.setCurrentImage(rotate(model, view, 90));
        return false;
    }

    private EditableImage rotate(Editor model, EditorView view, int degrees) {
        double rotation = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(rotation));
        double cos = Math.abs(Math.cos(rotation));
        int w = model.getCurrentImage().getWidth();
        int h = model.getCurrentImage().getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        EditableImage rotImage = new EditableImage(newWidth, newHeight);
        Graphics2D g = rotImage.createGraphics();
        g.translate((newWidth - w) / 2, (newHeight - h) / 2);
        g.rotate(rotation, w / 2, h / 2);
        g.drawRenderedImage(model.getCurrentImage(), null);
        g.dispose();
        return rotImage;
    }

    @Override
    public boolean undo(Editor model, EditorView view, EditorController controller) {
        EditableImage image = model.popFilter().getPreviousImage();
        model.setCurrentImage(image);
        return true;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return null;
    }
}
