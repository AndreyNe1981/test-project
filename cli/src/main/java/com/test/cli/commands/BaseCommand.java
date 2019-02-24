package com.test.cli.commands;

import com.google.common.collect.Sets;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.test.api.HttpTestClient;
import com.test.api.TestClient;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.test.cli.enums.ParameterName.HOST;
import static com.test.cli.enums.ParameterName.PORT;

public abstract class BaseCommand implements Command {

    private TestClient testClient;
    private Collection<?> requiredParameters = Sets.newHashSet(PORT.name().toLowerCase(), HOST.name().toLowerCase());

    public BaseCommand(CommandLine cmd) {
        internalValidate(cmd);

        final AsyncHttpClientConfig asyncHttpClientConfig = new AsyncHttpClientConfig.Builder()
            .setRequestTimeout(5000)
            .setReadTimeout(5000)
            .setMaxConnections(1)
            .build();

        AsyncHttpClient httpClient = new AsyncHttpClient(asyncHttpClientConfig);

        String host = cmd.getOptionValue(HOST.name().toLowerCase());
        int port = Integer.parseInt(cmd.getOptionValue(PORT.name().toLowerCase()));

        testClient = new HttpTestClient(httpClient, "http://" + host + ":" + port);

    }

    protected abstract void validate(CommandLine cmd);

    protected TestClient getTestAppClient() {
        return testClient;
    }

    private void internalValidate(CommandLine cmd) {
        validateParams(cmd, requiredParameters);
        validate(cmd);
    }

    protected void validateParams(CommandLine cmd, Collection<?> requiredParameters) {
        List<Option> options = Arrays.asList(cmd.getOptions());
        Set<String> inputParameters = options.stream()
            .map(Option::getOpt)
            .collect(Collectors.toSet());

        if (!inputParameters.containsAll(requiredParameters)) {
            System.out.println("One of the required parameters are missing (" + requiredParameters + ").");
            throw new IllegalArgumentException();
        }
    }
}
