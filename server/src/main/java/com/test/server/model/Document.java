package com.test.server.model;

import java.util.Objects;

public class Document {

    private String id;
    private String text;

    public Document(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) &&
            Objects.equals(text, document.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
