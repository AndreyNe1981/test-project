package com.test.cli;

import com.test.cli.commands.Command;
import com.test.cli.commands.CommandFactory;
import com.test.cli.enums.CommandName;
import com.test.cli.enums.ParameterName;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class ConsoleApp {

    public static final Options options;
    public static final Set<String> availableCommandNames;
    public static final Set<String> availableParameterNames;

    static {
        options = new Options();
        // List of available commands
        options.addOption(CommandName.HELP.name().toLowerCase(), "print available command line options");
        options.addOption(CommandName.PUT.name().toLowerCase(), "put document, returns newly created document id (-put -host 127.0.0.1 -port 4242 -document \"document text\")");
        options.addOption(CommandName.GET.name().toLowerCase(), "get document by id (-get -id someid -host 127.0.0.1 -port 4242)");
        options.addOption(CommandName.SEARCH.name().toLowerCase(), "search documents by keywords (-search -keywords \"test some keywords\"  -host 127.0.0.1 -port 4242)");
        // List of available parameters
        options.addOption(ParameterName.HOST.name().toLowerCase(), true, "server app host");
        options.addOption(ParameterName.PORT.name().toLowerCase(), true, "server app port");
        options.addOption(ParameterName.DOCUMENT.name().toLowerCase(), true, "document text, should be placed in quotes, quotes will be stripped, use api to avoid this");
        options.addOption(ParameterName.KEYWORDS.name().toLowerCase(), true, "search keywords");
        options.addOption(ParameterName.ID.name().toLowerCase(), true, "document id");

        availableCommandNames = new HashSet<>(asList(
            CommandName.HELP.name().toLowerCase(),
            CommandName.PUT.name().toLowerCase(),
            CommandName.GET.name().toLowerCase(),
            CommandName.SEARCH.name().toLowerCase()
        ));

        availableParameterNames = new HashSet<>(asList(
            ParameterName.HOST.name().toLowerCase(),
            ParameterName.PORT.name().toLowerCase(),
            ParameterName.DOCUMENT.name().toLowerCase(),
            ParameterName.KEYWORDS.name().toLowerCase(),
            ParameterName.ID.name().toLowerCase()
        ));
    }

    public static void main(String[] args) {
        try {
            final CommandLineParser parser = new DefaultParser();
            final CommandLine cmd = parser.parse(options, args);
            Optional<String> inputCommand = validateAndReturnInputCommand(cmd);

            final Command command = CommandFactory.getCommand(inputCommand, cmd);
            command.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Executing command was canceled.");
        }
    }

    /** Validates and returns input command. Command line must contain only one available command name.
     *
     * @param cmd command line object with input command name and parameters
     * @return a {@code String} command name
     */
    private static Optional<String> validateAndReturnInputCommand(CommandLine cmd) throws Exception {
        Set<String> inputCommands = Arrays.stream(cmd.getOptions())
            .map(Option::getOpt)
            .collect(Collectors.toSet());

        inputCommands.retainAll(availableCommandNames);
        if (inputCommands.size() != 1) {
            throw new IllegalArgumentException(String.format("Command is not valid. Command line should contain one correct command. (%s)", availableCommandNames.toString()));
        }

        return inputCommands.stream().findFirst();
    }
}
