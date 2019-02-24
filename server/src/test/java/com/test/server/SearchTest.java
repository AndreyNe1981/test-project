package com.test.server;

import com.test.api.beans.DocumentDto;
import com.test.api.beans.SearchResponse;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchTest extends BaseTest {

    @Test
    public void should_search_by_tokens_using_keywords() {
        DocumentDto documentNotFound1 = new DocumentDto(
            UUID.randomUUID().toString(),
            "simple document that shouldn't be found"
        );
        DocumentDto documentNotFound2 = new DocumentDto(
            UUID.randomUUID().toString(),
            "simple document that shouldn't be found 2"
        );
        DocumentDto documentFound1 = new DocumentDto(
            UUID.randomUUID().toString(),
            "among this text apple is a word to be found"
        );
        DocumentDto documentFound2 = new DocumentDto(
            UUID.randomUUID().toString(),
            "among this text apple and water is a words to be found"
        );

        testClient.putDocument(documentFound1);
        testClient.putDocument(documentFound2);
        testClient.putDocument(documentNotFound1);
        testClient.putDocument(documentNotFound2);

        SearchResponse result = testClient.search("water apple", 0, 5);

        assertEquals(2, result.getCount());

        assertTrue(result.getDocumentIds().contains(documentFound1.getId()));
        assertTrue(result.getDocumentIds().contains(documentFound2.getId()));
    }
}
