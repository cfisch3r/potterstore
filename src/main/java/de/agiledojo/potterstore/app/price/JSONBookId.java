package de.agiledojo.potterstore.app.price;

import de.agiledojo.potterstore.BookId;

public class JSONBookId implements BookId {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
