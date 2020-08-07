package com.valorin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    private String name;
    private String fullName;
    private String abbreviation;

    public SubCommand(String name) {
        this.name = name;
    }
    
    public SubCommand(String fullName,String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public String getName() {
        return name;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
}
