package uk.ac.gtvl2.configurations;

/**
 * Created by leniglo on 20/11/15.
 */
public class EditorConfig {
    private static EditorConfig ourInstance = new EditorConfig();

    public static EditorConfig getInstance() {
        return ourInstance;
    }

    public static final String COMMANDS_PKG = "uk.ac.gtvl2.commands.";
    public static final String BUNDLE_NAME = "uk/ac/gtvl2/resources/MessagesBundle";
    public static final String CSS_FILE = "uk/ac/gtvl2/resources/fotoshop.css";

    private EditorConfig() {

    }

}
