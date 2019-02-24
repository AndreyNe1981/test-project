package com.test.server;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.test.api.HttpTestClient;
import com.test.api.TestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = ServerApp.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(
    locations = "classpath:integration-test.properties")
public abstract class BaseTest {

    @Value("${server.port}")
    private int localServerPort;

    protected TestClient testClient;

    @Before
    public void before() {
        final AsyncHttpClientConfig asyncHttpClientConfig = new AsyncHttpClientConfig.Builder()
            .setRequestTimeout(5000)
            .setReadTimeout(5000)
            .setMaxConnections(5)
            .build();

        AsyncHttpClient httpClient = new AsyncHttpClient(asyncHttpClientConfig);

        testClient = new HttpTestClient(httpClient, "http://localhost:" + localServerPort);
    }

    @After
    public void after() throws IOException {
        testClient.close();
    }
}
