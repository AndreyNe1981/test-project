package com.test.cli.commands;

import com.google.common.base.CaseFormat;
import com.test.cli.enums.CommandName;
import org.apache.commons.cli.CommandLine;

import java.util.Optional;

public class CommandFactory {

    public static Command getCommand(Optional<String> inputCommand, CommandLine cmd) throws Exception {
        if (!inputCommand.isPresent()) {
            throw new Exception("Command name does not exist");
        }

        final String inputCommandInUppedrUnderscore = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, inputCommand.get()).toUpperCase();
        final CommandName commandName = CommandName.valueOf(inputCommandInUppedrUnderscore);

        switch (commandName) {
            case HELP:
                return new PrintHelpCommand();
            case PUT:
                return new PutCommand(cmd);
            case GET:
                return new GetCommand(cmd);
            case SEARCH:
                return new SearchCommand(cmd);
            default:
                return new PrintHelpCommand();
        }
    }
}
