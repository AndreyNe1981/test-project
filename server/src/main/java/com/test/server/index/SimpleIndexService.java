package com.test.server.index;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.test.api.beans.SearchResponse;
import com.test.server.model.Document;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Component
public class SimpleIndexService implements IndexService {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Multimap<String, String> documentIdsByTerm = LinkedHashMultimap.create();

    @Override
    public void putToIndex(Document document) {
        lock.writeLock().lock();
        try {
            if (null != document
                && null != document.getId()
                && null != document.getText()) {
                String[] terms = document.getText().split(" ");

                for (String term : terms) {
                    documentIdsByTerm.put(term, document.getId());
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public SearchResponse search(List<String> keywords, int offset, int limit) {
        lock.readLock().lock();
        try {
            List<String> documentIds = keywords.stream()
                .map(documentIdsByTerm::get)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

            int count = documentIds.size();

            List<String> result = count <= offset
                ? Collections.emptyList()
                : count > limit + offset
                    ? documentIds.subList(offset, offset + limit)
                    : documentIds.subList(offset, count);

            return new SearchResponse(
                count,
                result
            );
        } finally {
            lock.readLock().unlock();
        }
    }
}
