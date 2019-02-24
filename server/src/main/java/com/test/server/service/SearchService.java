package com.test.server.service;

import com.test.api.beans.SearchCriteria;
import com.test.api.beans.SearchResponse;

public interface SearchService {

    SearchResponse search(SearchCriteria searchCriteria, int offset, int limit);

}
