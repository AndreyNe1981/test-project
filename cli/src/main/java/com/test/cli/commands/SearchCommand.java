package com.test.cli.commands;

import com.google.common.collect.Sets;
import com.test.api.beans.SearchResponse;
import org.apache.commons.cli.CommandLine;

import java.util.Collection;

import static com.test.cli.enums.ParameterName.*;

public class SearchCommand extends BaseCommand {

    private final String keywords;
    private final int offset;
    private final int limit;
    private Collection<?> requiredParameters = Sets.newHashSet(KEYWORDS.name().toLowerCase());

    public SearchCommand(CommandLine cmd) {
        super(cmd);

        keywords = cmd.getOptionValue(KEYWORDS.name().toLowerCase());
        offset = Integer.parseInt(cmd.getOptionValue(OFFSET.name().toLowerCase(), "0"));
        limit = Integer.parseInt(cmd.getOptionValue(LIMIT.name().toLowerCase(), "10"));
    }

    @Override
    protected void validate(CommandLine cmd) {
        validateParams(cmd, requiredParameters);
    }

    @Override
    public void execute() {
        SearchResponse searchResult = getTestAppClient().search(keywords, offset, limit);

        System.out.println("Overall items found: " + searchResult.getCount());
        System.out.println("Found document ids: " + searchResult.getDocumentIds());
    }
}
