package com.test.api.beans;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = SearchCriteria.SearchCriteriaBuilder.class)
public class SearchCriteria {

    private String keywords;

    private SearchCriteria() {};

    public String getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
            "keywords='" + keywords + '\'' +
            '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class SearchCriteriaBuilder {

        private String keywords;

        public SearchCriteriaBuilder() {}

        public SearchCriteriaBuilder setKeywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public SearchCriteria build() {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.keywords = this.keywords;
            return searchCriteria;
        }
    }
}
