package uk.ac.gtvl2.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uk.ac.gtvl2.commands.ICommand;
import uk.ac.gtvl2.controllers.Parser;
import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.models.EnumCommand;

/**
 * Created by leniglo on 20/11/15.
 */
public class GuiView extends EditorView {

    public GuiView() {
        super();
        try {
            controller.edit();
        } catch (Exception e) {
            this.showError(this.getTranslation("error"));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        Pane root = new BorderPane(this.createCenter(), this.createTop(), this.createRight(), this.createBot(), this.createLeft());
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private Node createCenter() {
        ImageView imageView = new ImageView();
        Pane pane = new StackPane(imageView);
        return pane;
    }

    private Node createTop() {
        Button openBtn = new Button(getTranslation("OPEN"));
        openBtn.setOnAction(this.createHandler(EnumCommand.OPEN));
        Button quitBtn = new Button(getTranslation("QUIT"));
        Pane pane = new HBox(5, openBtn, quitBtn);
        return pane;
    }

    private Node createRight() {
        Pane pane = null;
        return pane;
    }

    private Node createBot() {
        Pane pane = null;
        return pane;
    }

    private Node createLeft() {
        Pane pane = null;
        return pane;
    }

    @Override
    public void showError(String error) {
        System.err.println(error);
        //TODO MAKE POPUP
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
        //TODO MAKE POPUP
    }

    @Override
    public Parser getParser() {
        return null;
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public void showPrompt() {

    }

    private EventHandler<ActionEvent> createHandler(EnumCommand enumCommand) {
        try {
            final ICommand iCommand = (ICommand) Class.forName(enumCommand.getClassName()).newInstance();
            return iCommand.handler(model, this, controller);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
