package uk.ac.gtvl2.views;

import javafx.application.Application;
import uk.ac.gtvl2.controllers.EditorController;
import uk.ac.gtvl2.controllers.Parser;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by leniglo on 20/11/15.
 */
public abstract class EditorView extends Application {
    private Locale currentLocale;
    private ResourceBundle bundle;
    private static final String BUNDLE_NAME = "uk/ac/gtvl2/resources/MessagesBundle";
    protected EditorController controller;

    public EditorView() {
        this.setCurrentLocale(Locale.getDefault());
    }

    public String getTranslation(String key, String... args) {
        if (this.bundle.containsKey(key))
            return MessageFormat.format(this.bundle.getString(key), args);
        else
            this.showError(this.getTranslation("MISSING_TRANSLATION", key, new Exception().getStackTrace()[1].getClassName()));
        return null;
    }

    public boolean setCurrentLocale(Locale locale) {
        if (this.currentLocale != locale) {
            this.currentLocale = locale;
            ResourceBundle.clearCache();
            this.bundle = ResourceBundle.getBundle(BUNDLE_NAME, this.currentLocale);
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

    public abstract Parser getParser();

    public abstract boolean isConsole();

    public abstract void showPrompt();

    public abstract void doLaunch(String... args);

    public void attachController(EditorController controller) {
        System.out.println(3);
        System.out.println(controller);
        this.controller = controller;
        System.out.println(4);
        System.out.println(this.controller);
    }
}
