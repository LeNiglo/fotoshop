package uk.ac.gtvl2.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import uk.ac.gtvl2.commands.ICommand;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.Editor;
import uk.ac.gtvl2.models.EnumCommand;
import uk.ac.gtvl2.views.EditorView;

import java.util.ArrayList;

/**
 * Created by leniglo on 20/11/15.
 */
public class EditorController {

    private final Editor model;
    private final EditorView view;
    private static final String COMMANDS_PKG = "uk.ac.gtvl2.commands.";

    public EditorController(Editor model, EditorView view) {
        this.model = model;
        this.view = view;
        this.view.attachController(this);
    }

    public void edit() {
        if (this.view.isConsole()) {
            this.view.showMessage(view.getTranslation("WELCOME"));
            // Enter the main command loop.  Here we repeatedly read commands and
            // execute them until the editing session is over.
            boolean finished = false;
            while (!finished) {
                Command command = null;
                command = this.view.getParser().getCommand();
                finished = processCommand(command);
            }
            this.view.showMessage(view.getTranslation("GOODBYE"));
        } else {
            try {
                this.view.doLaunch();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.view.showMessage(view.getTranslation("WELCOME"));
        }
    }


    public EventHandler<ActionEvent> handleEvent() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                processCommand(new Command(new ArrayList<>(), view.getBundle()));
            }
        };
    }

    /**
     * Given a command, edit (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the editing session, false otherwise.
     */
    public boolean processCommand(Command command) {
        boolean wantToQuit = true;

        if (command.isUnknown()) {
            // USER ERROR, we don't want to quit, just to warn him.
            this.view.showError(this.view.getTranslation("CMD_NOT_FOUND", command.getWord(0)));
            return false;
        }

        String commandWord = command.getWord(0);

        EnumCommand cmd = EnumCommand.getCmd(commandWord, this.view.getBundle());

        if (cmd == null) {
            // CRITICAL ERROR, we want to quit.
            view.showError(view.getTranslation("CRITICAL") + view.getTranslation("CMD_NOT_FOUND", commandWord));
            return true;
        } else {
            try {

                String className = COMMANDS_PKG + cmd.getClassName();
                final ICommand iCommand = (ICommand) (Class.forName(className).newInstance());
                return iCommand.run(model, view, this, command);

            } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
                // CRITICAL ERROR, we want to quit.
                view.showError(view.getTranslation("CRITICAL") + view.getTranslation("CMD_NOT_FOUND", commandWord));
                return true;
            }
        }
    }

}
