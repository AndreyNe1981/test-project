package com.test.api;

import com.ning.http.client.AsyncHttpClient;

import java.io.Closeable;

public class TestClient implements Closeable {

    private final AsyncHttpClient httpClient;

    public TestClient(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void close() {
        httpClient.close();
    }
}
