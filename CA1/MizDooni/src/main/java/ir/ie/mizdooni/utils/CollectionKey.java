package ir.ie.mizdooni.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectionKey {
    private final List<Object> items;

    public CollectionKey() {
        this.items = new ArrayList<>();
    }

    public void addItem(Object item) {
        items.add(item);
    }

    public List<Object> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CollectionKey that = (CollectionKey) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    // Optional: toString() method for easy debugging and logging
    @Override
    public String toString() {
        return "CollectionKey{" +
                "items=" + items +
                '}';
    }
}
