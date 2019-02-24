package com.test.server.index;

import com.test.api.beans.SearchResponse;
import com.test.server.model.Document;

import java.util.List;

public interface IndexService {

    void putToIndex(Document document);

    SearchResponse search(List<String> keywords, int offset, int limit);
}
