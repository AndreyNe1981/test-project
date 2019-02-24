package com.test.api.beans;

public class DocumentDto {

    private String id;
    private String text;

    public DocumentDto(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
