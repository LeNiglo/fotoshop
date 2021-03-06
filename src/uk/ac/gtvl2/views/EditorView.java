package uk.ac.gtvl2.views;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.gtvl2.configurations.EditorConfig;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.models.Editor;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by leniglo on 20/11/15.
 */
public abstract class EditorView extends Application {
    private Locale currentLocale;
    private ResourceBundle bundle;
    protected EditorController controller;
    protected Editor model;

    public EditorView() {
        this.setCurrentLocale(Locale.getDefault());

        this.model = new Editor();
        this.controller = new EditorController(model, this);
    }

    public String getTranslation(String key, String... args) {
        if (this.bundle.containsKey(key))
            return MessageFormat.format(this.bundle.getString(key), args);
        else
            this.showError(this.getTranslation("MISSING_TRANSLATION", key, new Exception().getStackTrace()[1].getClassName(), String.valueOf(new Exception().getStackTrace()[1].getLineNumber())));
        return null;
    }

    public boolean setCurrentLocale(Locale locale) {
        if (this.currentLocale != locale) {
            this.currentLocale = locale;
            ResourceBundle.clearCache();
            this.bundle = ResourceBundle.getBundle(EditorConfig.BUNDLE_NAME, this.currentLocale);
        }
        return this.bundle.getLocale().equals(locale);
    }

    public ResourceBundle getBundle() {
        return this.bundle;
    }

    /*
     * UNIMPLEMENTED HERE
     */
    public abstract void showError(String error);

    public abstract void showMessage(String message);

    public abstract boolean isConsole();

    public void attachController(EditorController controller) {
        this.controller = controller;
    }

    /*
     * JavaFX required
     */

    @Override
    public void start(Stage primaryStage) throws Exception {}
}
