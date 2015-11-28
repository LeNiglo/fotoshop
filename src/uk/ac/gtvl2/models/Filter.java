package uk.ac.gtvl2.models;

/**
 * Created by leniglo on 20/11/15.
 */
public class Filter {
    private ColorImage previousImage;
    private String name;

    public Filter(ColorImage previousImage, String name) {
        this.previousImage = previousImage;
        this.name = name;
    }

    public ColorImage getPreviousImage() {
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
