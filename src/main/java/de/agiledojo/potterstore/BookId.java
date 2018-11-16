package de.agiledojo.potterstore;

import java.util.Objects;

public class BookId {

    public String id;

    public BookId() {
    }

    public BookId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return Objects.equals(id, bookId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
