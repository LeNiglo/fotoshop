package uk.ac.gtvl2;

import javafx.application.Application;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.views.ConsoleView;
import uk.ac.gtvl2.views.EditorView;
import uk.ac.gtvl2.views.GuiView;

/**
 * Created by leniglo on 20/11/15.
 */
public class Main {
    public static void main(String[] args) {
        final String envConsole = System.getenv("CONSOLE");
        final boolean isConsole = envConsole != null && envConsole.equals("1");

        if (isConsole) {
            EditorView view = new ConsoleView();
        } else {
            Application.launch(GuiView.class, args);
        }

    }
}
