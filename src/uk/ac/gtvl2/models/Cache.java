package uk.ac.gtvl2.models;

import java.util.Stack;

/**
 * Created by leniglo on 20/11/15.
 */
public class Cache {
    private final ColorImage image;
    private final Stack<Filter> filters;

    public Cache(ColorImage image, Stack<Filter> filters) {
        this.image = image;
        this.filters = (Stack<Filter>) filters.clone();
    }

    public ColorImage getImage() {
        return image;
    }

    public Stack<Filter> getFilters() {
        return filters;
    }
}
