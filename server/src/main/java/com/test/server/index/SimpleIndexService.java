package com.test.server.index;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.test.api.beans.SearchResponse;
import com.test.server.model.Document;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Component
public class SimpleIndexService implements IndexService {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Multimap<String, String> documentIdsByTerm = LinkedListMultimap.create();

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
            Set<Collection<String>> documentsBySeparateTerms = keywords.stream()
                .map(documentIdsByTerm::get)
                .collect(Collectors.toCollection(LinkedHashSet::new));

            if (0 == documentsBySeparateTerms.size()) return new SearchResponse(0, Collections.emptyList());

            Set<String> intersectedDocumentIds = intersect(documentsBySeparateTerms);

            List<String> documentIds = new ArrayList<>(intersectedDocumentIds);

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

    @Override
    public void clean() {
        lock.writeLock().lock();
        try {
            documentIdsByTerm.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private <T> Set<T> intersect(Collection<? extends Collection<T>> collections) {
        Set<T> common = new LinkedHashSet<T>();
        if (!collections.isEmpty()) {
            Iterator<? extends Collection<T>> iterator = collections.iterator();
            common.addAll(iterator.next());
            while (iterator.hasNext()) {
                common.retainAll(iterator.next());
            }
        }
        return common;
    }
}
