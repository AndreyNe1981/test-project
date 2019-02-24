package com.test.server.controllers;

import com.test.api.beans.SearchRequest;
import com.test.api.beans.SearchResponse;
import com.test.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public @ResponseBody SearchResponse search(@RequestBody SearchRequest searchRequest) {
        return searchService.search(searchRequest.getSearchCriteria(), searchRequest.getOffset(), searchRequest.getLimit());
    }
}
