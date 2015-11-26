package uk.ac.gtvl2.views;

import javafx.stage.Stage;
import uk.ac.gtvl2.controllers.Parser;

/**
 * Created by leniglo on 20/11/15.
 */
public class ConsoleView extends EditorView {

    private final Parser parser;

    public ConsoleView() {
        this.parser = new Parser(this);
    }

    @Override
    public void showError(String error) {
        if (error != null)
            System.err.println(error);
    }

    @Override
    public void showMessage(String message) {
        if (message != null)
            System.out.println(message);
    }

    @Override
    public Parser getParser() {
        return this.parser;
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public void showPrompt() {
        System.out.print(this.getTranslation("PROMPT") == null ? "" : this.getTranslation("PROMPT"));
    }

    /*
     * JavaFX required
     */
    @Override
    public void doLaunch(String... args) { }

    @Override
    public void start(Stage primaryStage) throws Exception { }

}
