package com.test.server.service;

import com.test.api.beans.SearchCriteria;
import com.test.api.beans.SearchResponse;
import com.test.server.index.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SimpleSearchService implements SearchService {

    private final IndexService indexService;

    @Autowired
    public SimpleSearchService(IndexService indexService) {
        this.indexService = indexService;
    }

    @Override
    public SearchResponse search(SearchCriteria searchCriteria, int offset, int limit) {
        if (null == searchCriteria || null == searchCriteria.getKeywords()) return new SearchResponse(0, Collections.emptyList());

        List<String> keywords = Arrays.asList(
            searchCriteria.getKeywords().split(" ")
        );

        return indexService.search(keywords, offset, limit);
    }
}
