package uk.ac.gtvl2.commands;

import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.EditableImage;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.EditorView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by leniglo on 20/11/15.
 */
public class OpenCommand implements ICommand {

    private EditableImage loadImage(EditorView view, String name) {
        EditableImage img = null;
        try {
            img = new EditableImage(ImageIO.read(new File(name)));
            img.setName(name);
        } catch (IOException e) {
            view.showError(view.getTranslation("OPEN_ERROR_FILE", name));
        }
        return img;
    }

    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("OPEN_ERROR"));
            return false;
        }

        EditableImage image = loadImage(view, command.getWord(1));
        if (image != null) {
            model.setCurrentImage(image);
            view.showMessage(view.getTranslation("OPEN_SUCCESS", image.getName()));
        } else {
            view.showError(view.getTranslation("OPEN_ERROR"));
        }
        return false;
    }

    @Override
    public boolean undo(Editor model, EditorView view, EditorController controller) {
        return false;
    }
}
