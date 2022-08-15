package com.imjustdoom.cmdinstruction;

import org.bukkit.plugin.java.JavaPlugin;

public class CMDInstruction {

    public static void registerCommands(JavaPlugin plugin, Command ... commands) {
        for (Command command : commands) {
            plugin.getCommand(command.getName()).setExecutor(command);
            plugin.getCommand(command.getName()).setTabCompleter(command);
        }
    }
}
