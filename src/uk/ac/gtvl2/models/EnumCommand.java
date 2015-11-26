package uk.ac.gtvl2.models;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by leniglo on 13/10/15.
 */

public enum EnumCommand {

    OPEN("OpenCommand"),
    SAVE("SaveCommand"),
    LOOK("LookCommand"),
    MONO("MonoCommand"),
    ROT90("Rot90Command"),
    HELP("HelpCommand"),
    LANG("LangCommand"),
    QUIT("QuitCommand"),
    SCRIPT("ScriptCommand"),
    PUT("PutCacheCommand"),
    GET("GetCacheCommand"),
    REMOVE("RemoveCacheCommand"),
    LIST("ListCacheCommand"),
    UNDO("UndoCommand");

    private final String className;
    private static String list;

    EnumCommand(String className) {
        this.className = className;
    }

    /**
     * Get the text from the specified bundle
     *
     * @param bundle ResourceBundle of the Locale
     * @return String name of the command
     */
    public String getText(ResourceBundle bundle) {
        return bundle.getString(this.name());
    }

    /**
     * Get the method name to call (method from Editor)
     *
     * @return String name of the method
     */
    public String getClassName() {
        return className;
    }

    public static String getList(ResourceBundle bundle) {
        return "\t" + Arrays.stream(EnumCommand.values())
                .map(enumCommand -> enumCommand.getText(bundle))
                .collect(Collectors.joining(", "));
    }


    public static EnumCommand getCmd(String commandWord, ResourceBundle resourceBundle) {
        for (EnumCommand enumCommand : EnumCommand.values()) {
            if (enumCommand.getText(resourceBundle).equals(commandWord)) {
                return enumCommand;
            }
        }
        return null;
    }
}
