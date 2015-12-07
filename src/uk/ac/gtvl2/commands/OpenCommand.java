package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.ColorImage;
import uk.ac.gtvl2.models.Command;
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

    private ColorImage loadImage(EditorView view, String name) {
        ColorImage img;
        try {
            img = new ColorImage(ImageIO.read(new File(name)));
            img.setName(name);
            return img;
        } catch (IOException | NullPointerException e) {
            view.showError(view.getTranslation("OPEN_ERROR_FILE", name));
            return null;
        }
    }

    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {
        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("OPEN_ERROR1"));
            return false;
        }

        ColorImage image = loadImage(view, command.getWord(1));
        if (image != null) {
            model.setCurrentImage(image);
            if (!view.isConsole()) {
                ((GuiView) view).update();
            }
            view.showMessage(view.getTranslation("OPEN_SUCCESS", image.getName()));
        } else {
            view.showError(view.getTranslation("OPEN_ERROR2"));
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
                run(model, view, controller, cmd);
            }

        };
    }
}
