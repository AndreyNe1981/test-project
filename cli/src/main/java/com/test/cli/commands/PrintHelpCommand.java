package com.test.cli.commands;

import com.test.cli.ConsoleApp;
import org.apache.commons.cli.HelpFormatter;

public class PrintHelpCommand implements Command {

    @Override
    public void execute() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("host, port are mandatory options", ConsoleApp.options);
    }
}
