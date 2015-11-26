package uk.ac.gtvl2.models;

import java.awt.image.BufferedImage;

/**
 * Created by leniglo on 20/11/15.
 */
public class EditableImage extends ColorImage {
    private String name;

    public EditableImage(BufferedImage image) {
        super(image);
    }

    public EditableImage(int width, int height) {
        super(width, height);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
