package de.agiledojo.potterstore.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PriceRequest {


    private final List<String> bookIds;

    public PriceRequest(String bookId) {
        this.bookIds = List.of(bookId);
    }

    public PriceRequest(List<String> bookIds) {
        this.bookIds = new ArrayList<>(bookIds);
    }

    public String json() {
        String idObjectList = bookIds.stream()
                .map(bookId -> String.format("{\"id\": \"%s\"}", bookId))
                .collect(Collectors.joining(","));
        return String.format("[%s]",idObjectList);
    }
}