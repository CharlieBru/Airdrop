package me.charliebru.airdrop.commands;

import me.charliebru.airdrop.commands.handlers.CommandData;
import me.charliebru.airdrop.commands.handlers.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

@CommandData(getName = "give", getDescription = "Give airdrop tokens", getPermission = "airdrop.admin", getUsage = "Incorrect usage: /airdrop give <player|all> <amount>", isConsole = true, isPlayer = true, getLength = 3)
public class TokenGiveCommand extends CommandExecutor {

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args[0].equalsIgnoreCase("give")) {

            int amount = Integer.parseInt(args[2]);

            ItemStack token = new ItemStack(Material.NAME_TAG, amount);
            ItemMeta tokenMeta = token.getItemMeta();
            Objects.requireNonNull(tokenMeta).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lAirdrop Token"));
            token.setItemMeta(tokenMeta);

            if (args[1].equalsIgnoreCase("all")) {
                Bukkit.getOnlinePlayers().forEach(online -> online.getInventory().addItem(token));
                commandSender.sendMessage(ChatColor.GREEN + "You have given all online players " + amount + " airdrop " + (amount > 1 ? "tokens" : "token"));
            } else {
                if (Bukkit.getPlayerExact(args[1]) == null) {
                    commandSender.sendMessage(ChatColor.RED + "That player was not found. Are you sure they're online?");
                    return;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                target.getInventory().addItem(token);
                commandSender.sendMessage(ChatColor.GREEN + "You have given" + target.getName() + " " + amount + " airdrop " + (amount > 1 ? "tokens" : "token"));
            }
        }
    }
}

