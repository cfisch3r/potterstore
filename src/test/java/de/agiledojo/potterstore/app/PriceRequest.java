package de.agiledojo.potterstore.app;

public class PriceRequest {


    private String bookId;

    public PriceRequest(String bookId) {
        this.bookId = bookId;
    }

    String json() {
        return "[{\"id\": \"" + this.bookId + "\"}]";
    }
}