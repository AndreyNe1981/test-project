package com.test.server;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.test.api.TestClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = ServerApp.class)
@TestPropertySource(
    locations = "classpath:integration-test.properties")
public abstract class BaseTest {

    @LocalServerPort
    private int localServerPort;

    protected TestClient testClient;

    @BeforeClass
    protected void beforeClass() {
        final AsyncHttpClientConfig asyncHttpClientConfig = new AsyncHttpClientConfig.Builder()
            .setRequestTimeout(5000)
            .setReadTimeout(5000)
            .setMaxConnections(5)
            .build();

        AsyncHttpClient httpClient = new AsyncHttpClient(asyncHttpClientConfig);

        testClient = new TestClient(httpClient);
    }

    @AfterClass
    protected void afterClass() {
        testClient.close();
    }
}