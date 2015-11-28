package uk.ac.gtvl2.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created by leniglo on 20/11/15.
 */
public class Editor {
    private EditableImage currentImage;
    private Stack<Filter> filters;
    private HashMap<String, EditableImage> cache;

    public Editor() {
        this.cache = new HashMap<>();
        this.filters = new Stack<>();
    }

    public EditableImage getCurrentImage() {
        return currentImage;
    }

    public Filter pushFilter(Filter filter) {
        return this.filters.push(filter);
    }

    public Filter popFilter() {
        return this.filters.pop();
    }

    public List<Filter> getFilters() {
        return this.filters.stream().collect(Collectors.toList());
    }

    public void setCurrentImage(EditableImage currentImage) {
        this.currentImage = currentImage;
    }

    public EditableImage getCache(String key) {
        if (this.cache.containsKey(key)) {
            return this.cache.get(key);
        } else {
            return null;
        }
    }

    public boolean putCache(String key, EditableImage image) {
        if (!this.cache.containsKey(key)) {
            this.cache.put(key, image);
            return true;
        }
        return false;
    }

    public boolean removeCache(String key) {
        if (this.cache.containsKey(key)) {
            this.cache.remove(key);
            return true;
        }
        return false;
    }

    public String listCache() {
        return this.cache.keySet().stream().collect(Collectors.joining(", "));
    }

    public Map<String, EditableImage> getFullCache() {
        return cache;
    }
}
