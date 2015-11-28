package uk.ac.gtvl2.models;

/**
 * Created by leniglo on 20/11/15.
 */
public class Filter {
    private EditableImage previousImage;
    private String name;

    public Filter(EditableImage previousImage, String name) {
        this.previousImage = previousImage;
        this.name = name;
    }

    public EditableImage getPreviousImage() {
        return previousImage;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
