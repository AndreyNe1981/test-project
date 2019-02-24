package com.test.server.repository;

import com.test.server.model.Document;

public interface DocumentRepository {

    void save(Document document);

    Document get(String id);
}
