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

    private EditorConfig() {

    }

}
