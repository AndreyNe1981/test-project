package com.test.cli.commands;

import com.google.common.collect.Sets;
import com.test.api.beans.DocumentDto;
import org.apache.commons.cli.CommandLine;

import java.util.UUID;

import static com.test.cli.enums.ParameterName.DOCUMENT;

public class PutCommand extends BaseCommand {

    private final String documentText;

    public PutCommand(CommandLine cmd) {
        super(cmd);

        documentText = cmd.getOptionValue(DOCUMENT.name().toLowerCase());
    }

    @Override
    protected void validate(CommandLine cmd) {
        validateParams(cmd, Sets.newHashSet(DOCUMENT.name().toLowerCase()));
    }

    @Override
    public void executeInternal() {
        DocumentDto documentDto = new DocumentDto(
            UUID.randomUUID().toString(),
            documentText
        );

        getTestAppClient().putDocument(documentDto);

        System.out.println("Put document with id: " + documentDto.getId());
    }
}
