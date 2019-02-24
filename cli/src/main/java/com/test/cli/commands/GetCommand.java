package com.test.cli.commands;

import com.google.common.collect.Sets;
import com.test.api.beans.DocumentDto;
import org.apache.commons.cli.CommandLine;

import java.util.Collection;

import static com.test.cli.enums.ParameterName.ID;

public class GetCommand extends BaseCommand {

    private final String id;
    private Collection<?> requiredParameters = Sets.newHashSet(ID.name().toLowerCase());

    public GetCommand(CommandLine cmd) {
        super(cmd);

        id = cmd.getOptionValue(ID.name().toLowerCase());
    }

    @Override
    protected void validate(CommandLine cmd) {
        validateParams(cmd, requiredParameters);
    }

    @Override
    public void execute() {
        DocumentDto documentDto = getTestAppClient().getDocumentById(id);

        System.out.println("Document text: " + documentDto.getId());
    }
}
