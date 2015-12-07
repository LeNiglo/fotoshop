package uk.ac.gtvl2.controllers;

import uk.ac.gtvl2.models.Command;
import uk.ac.gtvl2.views.ConsoleView;
import uk.ac.gtvl2.views.EditorView;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by leniglo on 20/11/15.
 */
public class Parser {
    private Scanner reader;
    private final EditorView view;

    /**
     * Create a parser to read from the terminal window.
     *
     * @param v The editor view
     */
    public Parser(EditorView v) {
        reader = new Scanner(System.in);
        view = v;
    }

    /**
     * Set input stream to another stream
     *
     * @param str The stream
     */
    public void setInputStream(FileInputStream str) {
        reader = new Scanner(str);
    }

    /**
     * Get a command from user input
     *
     * @return The next command from the user.
     */
    public Command getCommand() {
        String inputLine;
        List<String> words = new ArrayList<>();

        if (view.isConsole())
            ((ConsoleView) view).showPrompt();

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        while (tokenizer.hasNext()) {
            String w = tokenizer.next();
            words.add(w);
        }

        return new Command(words, this.view.getBundle());
    }

    /**
     * This methods checks if you can convert a long to an int
     *
     * @param l long to cast
     * @return long casted to int
     */
    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

}
