package com.imjustdoom.cmdinstruction;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public abstract class Command implements CommandExecutor, TabCompleter {

    private String name, permission;
    private List<SubCommand> subcommands;
    private List<String> tabCompletions;
    private boolean requiresArgs;

    public abstract void execute(CommandSender sender, String[] args);

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (getSubcommands().size() > 0 && args.length > 0) {
            for (SubCommand subCommand : getSubcommands()) {
                if (subCommand.getName().equalsIgnoreCase(args[0])) {
                    subCommand.handleCommand(sender, args);
                    return true;
                }
            }
        }

        if (isRequiresArgs() && args.length < 1) {
            sender.sendMessage("You must have more arguments.");
            return true;
        }

        execute(sender, args);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if(!sender.hasPermission(getPermission())) {
            return Collections.emptyList();
        }

        for (SubCommand subcommand : subcommands) {
            if (subcommand.getName().equalsIgnoreCase(args[0])) {
                return subcommand.handleTabComplete(sender, args);
            }
        }

        return tabCompletions;
    }

    public Command setName(String name) {
        this.name = name;
        return this;
    }

    public Command setSubcommands(SubCommand ... subcommands) {
        this.subcommands = Arrays.asList(subcommands);
        return this;
    }

    public Command setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Command setRequiresArgs(boolean requiresArgs) {
        this.requiresArgs = requiresArgs;
        return this;
    }

    public Command setTabCompletions(List<String> tabCompletions) {
        this.tabCompletions = tabCompletions;
        return this;
    }

    public Command setTabCompletions(String ... tabCompletions) {
        this.tabCompletions = Arrays.asList(tabCompletions);
        return this;
    }
}
