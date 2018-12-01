package de.agiledojo.potterstore.app;

import de.agiledojo.potterstore.BookId;

import java.util.Objects;

public class JSONBookId implements BookId {


    public String id;

    public JSONBookId() {
    }

    public JSONBookId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JSONBookId bookId = (JSONBookId) o;
        return Objects.equals(id, bookId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
