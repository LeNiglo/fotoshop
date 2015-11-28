package uk.ac.gtvl2.models;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created by leniglo on 20/11/15.
 */
public class Editor {
    private ColorImage currentImage;
    private HashMap<String, Cache> cache;
    private Stack<Filter> filters;

    public Editor() {
        this.cache = new HashMap<>();
        this.filters = new Stack<>();
    }

    public ColorImage getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(ColorImage currentImage) {
        this.currentImage = currentImage;
    }

    public Filter pushFilter(Filter filter) {
        return this.filters.push(filter);
    }

    public Filter popFilter() {
        return this.filters.pop();
    }

    public Stack<Filter> getFilters() {
        return this.filters;
    }

    public List<Filter> getFiltersAsList() {
        return this.filters.stream().collect(Collectors.toList());
    }

    public Cache getCache(String key) {
        if (this.cache.containsKey(key)) {
            return this.cache.get(key);
        } else {
            return null;
        }
    }

    public boolean putCache(String key, ColorImage image) {
        if (!this.cache.containsKey(key)) {
            this.cache.put(key, new Cache(image, this.filters));
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

    public List<String> getCacheList() {
        return cache.keySet().stream().collect(Collectors.toList());
    }

    public void setFilters(Stack<Filter> filters) {
        this.filters = (Stack<Filter>) filters.clone();
    }
}
