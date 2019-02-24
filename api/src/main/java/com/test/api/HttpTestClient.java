package com.test.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.test.api.beans.DocumentDto;
import com.test.api.beans.SearchCriteria;
import com.test.api.beans.SearchRequest;
import com.test.api.beans.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HttpTestClient implements TestClient {

    private final Logger logger = LoggerFactory.getLogger(HttpTestClient.class);

    private final AsyncHttpClient httpClient;
    private final String httpContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HttpTestClient(AsyncHttpClient httpClient, String httpContext) {
        this.httpClient = httpClient;

        if (httpContext.endsWith("/")) {
            this.httpContext = httpContext.substring(0, httpContext.length() - 1);
        } else {
            this.httpContext = httpContext;
        }
    }

    @Override
    public void close() {
        httpClient.close();
    }

    public DocumentDto getDocumentById(String id) {
        final AsyncHttpClient.BoundRequestBuilder requestBuilder = httpClient.prepareGet(endpoint("/document/" + id))
            .setHeader("Content-Type", "application/json; charset=utf-8");

        try {
            Response response = requestBuilder
                .execute()
                .get();

            return objectMapper.readValue(response.getResponseBodyAsBytes(), DocumentDto.class);
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException("Failed to load document by id: " + id, e);
        }
    }

    public void putDocument(DocumentDto document) {
        if (null == document) throw new IllegalArgumentException("Document cannot be null");

        if (null == document.getId()) throw new IllegalArgumentException("Document id cannot be null, please use UUID for id generation");

        final AsyncHttpClient.BoundRequestBuilder requestBuilder = httpClient.preparePut(endpoint("/document/" + document.getId()))
            .setHeader("Content-Type", "application/json; charset=utf-8");

        try {
            requestBuilder.setBody(objectMapper.writeValueAsBytes(document));

            Response response = requestBuilder
                .execute()
                .get();

            if (200 != response.getStatusCode()) {
                throw new RuntimeException("Failed to put document, response status code: " + response.getStatusCode());
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException("Failed to put document by id: " + document.getId(), e);
        }
    }

    public SearchResponse search(String keywords, int offset, int limit) {
        SearchCriteria.SearchCriteriaBuilder builder = new SearchCriteria.SearchCriteriaBuilder();
        SearchCriteria searchCriteria = builder
            .setKeywords(keywords)
            .build();
        return search(searchCriteria, offset, limit);
    }

    private SearchResponse search(SearchCriteria searchCriteria, int offset, int limit) {
        final AsyncHttpClient.BoundRequestBuilder requestBuilder = httpClient.preparePost(endpoint("/search"))
            .setHeader("Content-Type", "application/json; charset=utf-8");

        try {
            requestBuilder.setBody(objectMapper.writeValueAsBytes(
                new SearchRequest(
                    searchCriteria,
                    offset,
                    limit
                )
            ));

            Response response = requestBuilder
                .execute()
                .get();

            return objectMapper.readValue(response.getResponseBodyAsBytes(), SearchResponse.class);
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException("Failed to perform search for the following criteria: " + searchCriteria, e);
        }
    }

    private String endpoint(String url) {
        return httpContext + url;
    }
}
