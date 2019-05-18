package com.blueclimpy.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class Command {
    protected String name;
    protected String description;
    protected String[] aliases;

    public Command(String name, String description, String[] aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    public abstract String getUsage();

    public abstract void onChannelCommand(CommandParser context);

    public boolean equals(Command command) {
        return this.getName() == command.getName();
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String[] getAliases(){
        return this.aliases;
    }
}
