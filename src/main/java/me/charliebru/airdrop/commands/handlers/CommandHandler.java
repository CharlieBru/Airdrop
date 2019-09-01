package me.charliebru.airdrop.commands.handlers;

import me.charliebru.airdrop.commands.AddItemCommand;
import me.charliebru.airdrop.commands.TokenGiveCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements org.bukkit.command.CommandExecutor {

    private List<CommandExecutor> commands = new ArrayList<>();

    public CommandHandler() {
        commands.add(new TokenGiveCommand());
        commands.add(new AddItemCommand());
    }

    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("airdrop")) {
            if (!commandSender.hasPermission("airdrop.admin")) {
                commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            }

            if (args.length == 0) {
                for (CommandExecutor c : commands) {
                    CommandData commandData = c.getClass().getAnnotation(CommandData.class);
                    commandSender.sendMessage(ChatColor.RED + commandData.getUsage());
                }
                return true;
            }

            for (CommandExecutor executor : commands) {
                CommandData commandData = executor.getClass().getAnnotation(CommandData.class);
                if (commandData.getName().equalsIgnoreCase(args[0])) {
                    if (commandData.getPermission() != null && !commandSender.hasPermission(commandData.getPermission())) {
                        commandSender.sendMessage(ChatColor.RED + "No permission");
                        return true;
                    }

                    if (!isBoth()) {
                        if (commandData.isConsole() && commandSender instanceof Player) {
                            commandSender.sendMessage(ChatColor.RED + "Console only command.");
                        }

                        if (commandData.isPlayer() && commandSender instanceof ConsoleCommandSender) {
                            commandSender.sendMessage(ChatColor.RED + "Player only command.");
                        }
                    }

                    if (commandData.getLength() > args.length) {
                        commandSender.sendMessage(ChatColor.RED + commandData.getUsage());
                        return true;
                    }

                    executor.execute(commandSender, args);
                }
            }
        }
        return true;
    }

    private boolean isBoth() {
        for (CommandExecutor executor : commands) {
            CommandData commandData = executor.getClass().getAnnotation(CommandData.class);
            if (commandData.isPlayer() && commandData.isConsole()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}

