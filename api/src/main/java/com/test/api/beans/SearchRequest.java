package com.test.api.beans;

public class SearchRequest {

    private SearchCriteria searchCriteria;
    private int offset;
    private int limit;

    public SearchRequest() {}

    public SearchRequest(SearchCriteria searchCriteria, int offset, int limit) {
        this.searchCriteria = searchCriteria;
        this.offset = offset;
        this.limit = limit;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
