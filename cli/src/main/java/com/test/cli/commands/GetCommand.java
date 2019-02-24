package com.test.cli.commands;

import com.google.common.collect.Sets;
import com.test.api.beans.DocumentDto;
import org.apache.commons.cli.CommandLine;

import static com.test.cli.enums.ParameterName.ID;

public class GetCommand extends BaseCommand {

    private final String id;

    public GetCommand(CommandLine cmd) {
        super(cmd);

        id = cmd.getOptionValue(ID.name().toLowerCase());
    }

    @Override
    protected void validate(CommandLine cmd) {
        validateParams(cmd, Sets.newHashSet(ID.name().toLowerCase()));
    }

    @Override
    public void executeInternal() {
        DocumentDto documentDto = getTestAppClient().getDocumentById(id);

        if ("".equals(documentDto.getId())) {
            System.out.println("Document not found");
        } else {
            System.out.println("Document text: " + documentDto.getText());
        }
    }
}
