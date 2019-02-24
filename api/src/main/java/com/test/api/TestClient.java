package com.test.api;

import com.test.api.beans.DocumentDto;
import com.test.api.beans.SearchCriteria;
import com.test.api.beans.SearchResponse;

import java.io.Closeable;

public interface TestClient extends Closeable {

    DocumentDto getDocumentById(String id);

    void putDocument(DocumentDto document);

    SearchResponse search(String keywords, int offset, int limit);
}
