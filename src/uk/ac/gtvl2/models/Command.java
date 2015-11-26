package uk.ac.gtvl2.models;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Command {

    private final List<String> commandWords;
    private boolean unknown;

    /**
     * Create a command object. The list of words can be empty.
     *
     * @param words The list of words in the command
     */
    public Command(List<String> words, ResourceBundle bundle) {
        this.commandWords = new ArrayList<>(words);
        this.unknown = EnumCommand.getCmd(words.get(0), bundle) == null;
    }

    /**
     * Return the word corresponding to the specified index of the command. If
     * the command was not understood, or there is no word at the index, the
     * result is null.
     *
     * @param index The index of the word
     * @return The word at index index, or null.
     */
    public String getWord(int index) {
        if (index >= 0 && index < commandWords.size()) {
            return commandWords.get(index);
        }
        return null;
    }

    /**
     * Whether the command is understood or not
     *
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        return this.unknown;
    }

    /**
     * Whether the command has a word at the given index
     *
     * @param index The index of the word
     * @return true if the command has a word at the specified index.
     */
    public boolean hasWord(int index) {
        return (commandWords.size() > index && commandWords.get(index) != null);
    }
}
