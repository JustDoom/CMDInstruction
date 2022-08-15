package com.imjustdoom.cmdinstruction;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public interface SubCommand {

    String getName();

    void execute(CommandSender sender, String[] args);

    List<SubCommand> getSubcommands();
}