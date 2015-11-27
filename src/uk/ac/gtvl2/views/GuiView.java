package uk.ac.gtvl2.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uk.ac.gtvl2.commands.ICommand;
import uk.ac.gtvl2.configurations.EditorConfig;
import uk.ac.gtvl2.controllers.Parser;
import uk.ac.gtvl2.models.EnumCommand;

/**
 * Created by leniglo on 20/11/15.
 */
public class GuiView extends EditorView {

    private BorderPane root;
    private Text statusText;

    public GuiView() {
        super();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(getTranslation("PROGRAM"));
        root = new BorderPane(this.createCenter(), this.createTop(), this.createRight(), this.createBot(), this.createLeft());
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        try {
            controller.edit();
        } catch (Exception e) {
            e.printStackTrace();
            showError(getTranslation("ERROR", e.getMessage(), (e.getCause() != null ? e.getCause().getMessage() : "")));
        }
    }

    private Node createCenter() {
        ImageView imageView = new ImageView();
        Pane pane = new StackPane(imageView);
        return pane;
    }

    private Node createTop() {
        Button openBtn = new Button(getTranslation("OPEN"));
        Button quitBtn = new Button(getTranslation("QUIT"));
        quitBtn.setOnAction(this.createHandler(EnumCommand.QUIT));
        Pane pane = new HBox(5, openBtn, quitBtn);
        return pane;
    }

    private Node createRight() {
        Pane pane = null;
        return pane;
    }

    private Node createBot() {
        Pane pane = new VBox();
        //TODO css
        pane.setStyle("-fx-background-color: gainsboro");
        statusText = new Text();
        pane.getChildren().add(statusText);
        return pane;
    }

    private Node createLeft() {
        Pane pane = null;
        return pane;
    }

    @Override
    public void showError(String error) {
        System.err.println(error);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error !");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    @Override
    public void showMessage(String message) {
        if (this.statusText != null)
            this.statusText.setText(message);
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
            final ICommand iCommand = (ICommand) Class.forName(EditorConfig.COMMANDS_PKG + enumCommand.getClassName()).newInstance();
            return iCommand.handler(model, this, controller);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
