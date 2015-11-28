package uk.ac.gtvl2.commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.controllers.Parser;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.ConsoleView;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leniglo on 28/11/15.
 */
public class ScriptCommand implements ICommand {
    @Override
    public boolean run(Editor model, EditorView view, EditorController controller, Command command) {

        if (!command.hasWord(1)) {
            view.showError(view.getTranslation("SCRIPT_ERROR1"));
            return false;
        }

        String scriptName = command.getWord(1);
        Parser scriptParser = new Parser(view);
        try (FileInputStream inputStream = new FileInputStream(scriptName)) {
            scriptParser.setInputStream(inputStream);
            boolean wantToQuit = false;
            while (!wantToQuit) {
                try {
                    Command cmd = scriptParser.getCommand();
                    wantToQuit = controller.processCommand(cmd);
                } catch (Exception ex) {
                    return false;
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            view.showError(view.getTranslation("SCRIPT_ERROR1"));
            return false;
        } catch (IOException ex) {
            throw new RuntimeException(view.getTranslation("ERROR", ex.getMessage(), (ex.getCause() != null ? ex.getCause().getMessage() : "")));
        }
    }

    @Override
    public EventHandler<ActionEvent> handler(Editor model, EditorView view, EditorController controller) {
        return event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(view.getTranslation("OPEN_TITLE"));
            File file = fileChooser.showOpenDialog(((GuiView) view).getStage());

            if (file != null) {
                List<String> words = new ArrayList<>();
                words.add(view.getTranslation("SCRIPT"));
                words.add(file.getAbsolutePath());

                Command cmd = new Command(words, view.getBundle());
                run(model, view, controller, cmd);
            }
        };
    }
}
