package me.charliebru.airdrop.commands.handlers;

import org.bukkit.command.CommandSender;

public abstract class CommandExecutor {

    public abstract void execute(CommandSender commandSender, String[] args);
}

