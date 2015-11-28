package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.EditableImage;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            if (!view.isConsole()) {
                ((GuiView) view).updateCurrentImage();
            }
            view.showMessage(view.getTranslation("OPEN_SUCCESS", image.getName()));
        } else {
            view.showError(view.getTranslation("OPEN_ERROR"));
        }
        return false;
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(view.getTranslation("OPEN_TITLE"));
            File file = fileChooser.showOpenDialog(((GuiView) view).getStage());

            if (file != null) {
                List<String> words = new ArrayList<>();
                words.add(view.getTranslation("OPEN"));
                words.add(file.getAbsolutePath());

                Command cmd = new Command(words, view.getBundle());
                this.run(model, view, controller, cmd);
            }

        };
    }
}
