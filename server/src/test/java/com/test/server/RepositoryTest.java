package com.test.server;

import com.test.api.beans.DocumentDto;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class RepositoryTest extends BaseTest {

    @Test
    public void should_put_and_get_document_by_key() {
        String documentId = UUID.randomUUID().toString();

        DocumentDto document = new DocumentDto(
            documentId,
            "some test text"
        );

        testClient.putDocument(document);

        DocumentDto storedDocument = testClient.getDocumentById(documentId);

        assertEquals(document.getId(), storedDocument.getId());
        assertEquals(document.getText(), storedDocument.getText());
    }
}
