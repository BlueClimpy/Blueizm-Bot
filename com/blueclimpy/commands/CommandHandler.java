package com.blueclimpy.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    public static List<Command> registeredCommands = new ArrayList<Command>();

    public void setupChannelCommand(CommandParser context) {
        Command commandToRun = null;

        for (Command command : registeredCommands) {
            if (context.getLabel().equalsIgnoreCase(command.getName())) {
                commandToRun = command;
            }

            for (String alias : command.getAliases()) {
                if (context.getLabel().equalsIgnoreCase(alias)) {
                    commandToRun = command;
                }
            }
        }

        if (commandToRun == null) return;
        commandToRun.onChannelCommand(context);
    }

    private boolean commandNotFound(Command command) {
        for (Command commandNotFound : registeredCommands) {
            if (command.equals(commandNotFound)) {
                return true;
            }
        }

        return false;
    }

    public void registerCommand(Command command) {
        if (!commandNotFound(command)) {
            registeredCommands.add(command);
        }
    }

    public void unRegisterCommand(Command command) {
        if (commandNotFound(command)) {
            for (int i = 0; i < registeredCommands.size(); i++) {
                if (registeredCommands.get(i).equals(command)) {
                    registeredCommands.remove(i); return;
                }
            }
        }
    }

}
