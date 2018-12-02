package de.agiledojo.potterstore.app;

public class PriceRequest {


    private String bookId;

    public PriceRequest(String bookId) {
        this.bookId = bookId;
    }

    public String json() {
        return "[{\"id\": \"" + this.bookId + "\"}]";
    }
}