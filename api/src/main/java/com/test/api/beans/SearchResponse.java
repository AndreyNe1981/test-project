package com.test.api.beans;

import java.util.List;

public class SearchResponse {

    private int count;
    private List<String> documentIds;

    public SearchResponse() {}

    public SearchResponse(int count, List<String> documentIds) {
        this.count = count;
        this.documentIds = documentIds;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }
}
