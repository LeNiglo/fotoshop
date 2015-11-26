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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.controllers.Parser;
import uk.ac.gtvl2.models.Editor;

/**
 * Created by leniglo on 20/11/15.
 */
public class GuiView extends EditorView {

    public GuiView() { }

    @Override
    public void doLaunch(String... args) {
        launch(args);
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
        openBtn.setOnAction(EditorController.handleEvent());
        Button quitBtn = new Button(getTranslation("QUIT"));
        quitBtn.setOnAction(EditorController.handleEvent());
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

    }

    @Override
    public void showMessage(String message) {

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
}
