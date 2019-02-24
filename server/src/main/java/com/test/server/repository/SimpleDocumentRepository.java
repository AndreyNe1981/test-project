package com.test.server.repository;

import com.test.server.model.Document;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SimpleDocumentRepository implements DocumentRepository {

    private Map<String, Document> documentById = new ConcurrentHashMap<>();

    @Override
    public void save(Document document) {
        documentById.put(document.getId(), document);
    }

    @Override
    public Document get(String id) {
        return documentById.get(id);
    }
}
