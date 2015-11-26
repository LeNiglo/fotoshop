package uk.ac.gtvl2.configurations;

/**
 * Created by leniglo on 20/11/15.
 */
public class EditorConfig {
    private static EditorConfig ourInstance = new EditorConfig();

    public static EditorConfig getInstance() {
        return ourInstance;
    }

    private EditorConfig() {

    }

}
