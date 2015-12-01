package uk.ac.gtvl2.views;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import uk.ac.gtvl2.Main;
import uk.ac.gtvl2.commands.ICommand;
import uk.ac.gtvl2.configurations.EditorConfig;
import uk.ac.gtvl2.models.EnumCommand;
import uk.ac.gtvl2.models.Filter;

/**
 * Created by leniglo on 20/11/15.
 */
public class GuiView extends EditorView {

    private BorderPane root;
    private Text statusText;
    private Stage stage;
    private ImageView imageView;
    private TextField cacheTextField;
    private ListView<Filter> filterList;
    private ListView<String> cacheList;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;

    public GuiView() {
        super();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.centerOnScreen();
        stage.setTitle(getTranslation("PROGRAM"));
        root = new BorderPane(this.createCenter(), this.createTop(), this.createRight(), this.createBot(), this.createLeft());
        root.getStylesheets().add(EditorConfig.CSS_FILE);
        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.setOnCloseRequest(event -> Main.exitRequested(GuiView.this, event));
        stage.show();

        try {
            controller.edit();
        } catch (Exception e) {
            showError(getTranslation("ERROR", e.getMessage(), (e.getCause() != null ? e.getCause().getMessage() : "")));
        }
    }

    private Node createCenter() {
        imageView = new ImageView();
        ScrollPane scrollPane = new ScrollPane();
        StackPane imageHolder = new StackPane(imageView);

        Reflection r = new Reflection(10, 0.7f, 0.7f, 0);

        imageView.setEffect(r);

        zoomProperty.addListener(event -> {
            if (imageView.getImage() != null) {
                imageView.setFitWidth(zoomProperty.get() * 4);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomProperty.set(zoomProperty.get() * 1.1);
            } else if (event.getDeltaY() < 0) {
                zoomProperty.set(zoomProperty.get() / 1.1);
            }
        });

        scrollPane.setContent(imageHolder);
        scrollPane.setPrefHeight(WINDOW_HEIGHT);
        scrollPane.setPrefWidth(WINDOW_WIDTH);
        return scrollPane;
    }

    private Node createTop() {
        GridPane gridPane = new GridPane();
        gridPane.setId("action-bar");
        gridPane.setPrefWidth(WINDOW_WIDTH);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(60);
        column.setHalignment(HPos.LEFT);
        gridPane.getColumnConstraints().add(column);
        column = new ColumnConstraints();
        column.setPercentWidth(40);
        column.setHalignment(HPos.RIGHT);
        gridPane.getColumnConstraints().add(column);

        Button openBtn = this.createButton(EnumCommand.OPEN);
        Button saveBtn = this.createButton(EnumCommand.SAVE);
        Button scriptBtn = this.createButton(EnumCommand.SCRIPT);
        Button undoBtn = this.createButton(EnumCommand.UNDO);

        HBox leftPane = new HBox(5, openBtn, saveBtn, scriptBtn, undoBtn);
        leftPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(leftPane, 0, 0);

        Button helpBtn = this.createButton(EnumCommand.HELP);
        Button quitBtn = this.createButton(EnumCommand.QUIT);

        HBox rightPane = new HBox(5, helpBtn, quitBtn);
        rightPane.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(rightPane, 1, 0);
        return gridPane;
    }

    private Node createRight() {
        GridPane gridPane = new GridPane();
        gridPane.setId("cache-box");
        gridPane.setPrefHeight(WINDOW_HEIGHT);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(5);
        gridPane.getRowConstraints().add(row);
        row = new RowConstraints();
        row.setPercentHeight(85);
        gridPane.getRowConstraints().add(row);
        row = new RowConstraints();
        row.setPercentHeight(5);
        gridPane.getRowConstraints().add(row);
        row = new RowConstraints();
        row.setPercentHeight(5);
        gridPane.getRowConstraints().add(row);

        Label label = new Label(getTranslation("CACHE"));
        gridPane.add(label, 0, 0);

        cacheList = new ListView<>();
        cacheList.setEditable(false);
        cacheList.setDisable(false);
        cacheList.setMinHeight(50);
        cacheList.setPrefHeight(200);

        gridPane.add(cacheList, 0, 1);

        cacheTextField = new TextField();
        Button putBtn = this.createButton(EnumCommand.PUT);
        Button getBtn = this.createButton(EnumCommand.GET);
        Button remBtn = this.createButton(EnumCommand.REMOVE);

        VBox vBox = new VBox(cacheTextField);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(0));
        vBox.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 2);

        HBox hBox = new HBox(putBtn, getBtn, remBtn);
        hBox.setMaxWidth(Double.MAX_VALUE);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0));
        hBox.setAlignment(Pos.CENTER);
        gridPane.add(hBox, 0, 3);

        return gridPane;
    }

    private Node createBot() {
        statusText = new Text();
        VBox pane = new VBox(statusText);
        pane.setId("status-bar");
        return pane;
    }

    private Node createLeft() {

        GridPane gridPane = new GridPane();
        gridPane.setId("filter-box");
        gridPane.setPrefHeight(WINDOW_HEIGHT);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(49);
        gridPane.getRowConstraints().add(row);
        row = new RowConstraints();
        row.setPercentHeight(2);
        gridPane.getRowConstraints().add(row);
        row = new RowConstraints();
        row.setPercentHeight(49);
        gridPane.getRowConstraints().add(row);

        Button rotBtn = this.createButton(EnumCommand.ROT90);
        Button monoBtn = this.createButton(EnumCommand.MONO);
        rotBtn.setMaxWidth(Double.MAX_VALUE);
        monoBtn.setMaxWidth(Double.MAX_VALUE);

        VBox commandBox = new VBox(rotBtn, monoBtn);
        commandBox.setId("command-box");
        gridPane.add(commandBox, 0, 0);
        gridPane.add(new Separator(Orientation.HORIZONTAL), 0, 1);

        filterList = new ListView<>();
        filterList.setEditable(false);
        filterList.setDisable(true);
        filterList.setMinHeight(50);
        gridPane.add(filterList, 0, 2);
        return gridPane;
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
    public boolean isConsole() {
        return false;
    }

    private Button createButton(EnumCommand enumCommand) {
        Button tmpBtn = new Button(enumCommand.getText(getBundle()));
        tmpBtn.setOnAction(this.createHandler(enumCommand));
        return tmpBtn;
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

    public void update() {
        if (imageView != null && this.model.getCurrentImage() != null) {
            FadeTransition transition = new FadeTransition(Duration.millis(500), imageView);
            transition.setFromValue(0.1);
            transition.setToValue(1.0);
            transition.setCycleCount(1);
            transition.setAutoReverse(false);

            imageView.setOpacity(0.0f);
            imageView.setImage(SwingFXUtils.toFXImage(this.model.getCurrentImage(), null));
            imageView.preserveRatioProperty().set(true);
            transition.play();
        }
        if (filterList != null) {
            filterList.setItems(FXCollections.observableList(this.model.getFiltersAsList()));
        }
        if (cacheList != null) {
            cacheList.setItems(FXCollections.observableList(this.model.getCacheList()));
        }

    }


    public Stage getStage() {
        return stage;
    }

    public String getCacheTextField() {
        return cacheTextField.getText();
    }

    public void clearCacheTextField() {
        cacheTextField.clear();
    }

    public String getCacheSelected() {
        return this.cacheList.getSelectionModel().getSelectedItem();
    }
}
