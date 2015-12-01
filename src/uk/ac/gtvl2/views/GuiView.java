package uk.ac.gtvl2.views;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
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
        MenuItem openItem = this.createMenuItem(EnumCommand.OPEN);
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.META_DOWN));
        MenuItem scriptItem = this.createMenuItem(EnumCommand.SCRIPT);
        MenuItem saveItem = this.createMenuItem(EnumCommand.SAVE);
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN));
        MenuItem quitItem = this.createMenuItem(EnumCommand.QUIT);
        quitItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.META_DOWN));
        MenuItem undoItem = this.createMenuItem(EnumCommand.UNDO);
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.META_DOWN));
        MenuItem rotItem = this.createMenuItem(EnumCommand.ROT90);
        MenuItem monoItem = this.createMenuItem(EnumCommand.MONO);
        MenuItem helpItem = this.createMenuItem(EnumCommand.HELP);
        helpItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.META_DOWN));

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu(getTranslation("FILE"));
        menuFile.getItems().addAll(openItem,
                scriptItem,
                new SeparatorMenuItem(),
                saveItem,
                quitItem);
        Menu menuEdit = new Menu(getTranslation("EDIT"));
        menuEdit.getItems().addAll(undoItem,
                new SeparatorMenuItem(),
                rotItem,
                monoItem);
        Menu menuHelp = new Menu(getTranslation("HELP"));
        menuHelp.getItems().addAll(helpItem);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
        return menuBar;
    }

    private Node createRight() {
        GridPane gridPane = new GridPane();
        gridPane.setId("cache-box");
        gridPane.setPrefHeight(WINDOW_HEIGHT);

        Label label = new Label(getTranslation("FILTERS"));
        gridPane.add(label, 0, 0);
        filterList = new ListView<>();
        filterList.setEditable(false);
        filterList.setDisable(true);
        filterList.setMinHeight(50);
        gridPane.add(filterList, 0, 1);

        gridPane.add(new Separator(Orientation.HORIZONTAL), 0, 2);

        label = new Label(getTranslation("CACHE"));
        gridPane.add(label, 0, 3);

        cacheList = new ListView<>();
        cacheList.setEditable(false);
        cacheList.setDisable(false);
        cacheList.setMinHeight(50);
        cacheList.setPrefHeight(200);

        gridPane.add(cacheList, 0, 4);

        cacheTextField = new TextField();
        Button putBtn = this.createButton(EnumCommand.PUT);
        Button getBtn = this.createButton(EnumCommand.GET);
        Button remBtn = this.createButton(EnumCommand.REMOVE);

        VBox vBox = new VBox(cacheTextField);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(0));
        vBox.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 5);

        HBox hBox = new HBox(putBtn, getBtn, remBtn);
        hBox.setMaxWidth(Double.MAX_VALUE);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0));
        hBox.setAlignment(Pos.CENTER);
        gridPane.add(hBox, 0, 6);

        return gridPane;
    }

    private Node createBot() {
        statusText = new Text();
        VBox pane = new VBox(statusText);
        pane.setId("status-bar");
        return pane;
    }

    private Node createLeft() {
        return null;
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

    private MenuItem createMenuItem(EnumCommand enumCommand) {
        MenuItem tmpMenu = new MenuItem(enumCommand.getText(getBundle()));
        tmpMenu.setOnAction(this.createHandler(enumCommand));
        return tmpMenu;
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
