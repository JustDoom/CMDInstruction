package com.imjustdoom.cmdinstruction;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public abstract class SubCommand {

    private String name, permission;
    private List<SubCommand> subcommands;
    private List<String> tabCompletions;
    private boolean requiresArgs;

    public abstract void execute(CommandSender sender, String[] args);

    public void handleCommand(CommandSender sender, String[] args) {
        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            sender.sendMessage("You do not have permission to use this command.");
            return;
        }

        if (getSubcommands() != null && getSubcommands().size() > 0) {
            for (SubCommand subCommand : getSubcommands()) {
                if (subCommand.getName().equalsIgnoreCase(args[0])) {
                    subCommand.handleCommand(sender, args);
                    return;
                }
            }
        }

        if (isRequiresArgs() && args.length < 1) {
            sender.sendMessage("You must have more arguments.");
            return;
        }

        execute(sender, args);
    }

    public List<String> handleTabComplete(CommandSender sender, String[] args) {
        if(!sender.hasPermission(getPermission())) {
            return Collections.emptyList();
        }

        if (getSubcommands() != null && getSubcommands().size() > 0) {
            for (SubCommand subcommand : subcommands) {
                if (subcommand.getName().equalsIgnoreCase(args[0])) {
                    return subcommand.getTabCompletions();
                }
            }
        }

        return tabCompletions;
    }

    public SubCommand setName(String name) {
        this.name = name;
        return this;
    }

    public SubCommand setSubcommands(SubCommand ... subcommands) {
        this.subcommands = Arrays.asList(subcommands);
        return this;
    }

    public SubCommand setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public SubCommand setRequiresArgs(boolean requiresArgs) {
        this.requiresArgs = requiresArgs;
        return this;
    }

    public SubCommand setTabCompletions(List<String> tabCompletions) {
        this.tabCompletions = tabCompletions;
        return this;
    }

    public SubCommand setTabCompletions(String ... tabCompletions) {
        this.tabCompletions = Arrays.asList(tabCompletions);
        return this;
    }
}
