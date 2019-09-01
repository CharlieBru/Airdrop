package me.charliebru.airdrop.commands;

import me.charliebru.airdrop.Airdrop;
import me.charliebru.airdrop.commands.handlers.CommandData;
import me.charliebru.airdrop.commands.handlers.CommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandData(getName = "additem", getDescription = "Add an item to the loot table", getPermission = "airdrop.admin", getUsage = "Incorrect usage: /airdrop additem", isConsole = false, isPlayer = true, getLength = 1)
public class AddItemCommand extends CommandExecutor {

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args[0].equalsIgnoreCase("additem")) {
            Player player = (Player) commandSender;
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            if (itemStack.getType() != Material.AIR) {
                int newKey = Airdrop.getInstance().getConfig().getConfigurationSection("Items").getKeys(false).size() + 1;
                Airdrop.getInstance().getConfig().set("Items." + newKey, itemStack);
                Airdrop.getInstance().items.add(itemStack);
                player.sendMessage("Your held item has been added to airdrop loot.");
                Airdrop.getInstance().saveConfig();
            } else {
                player.sendMessage(ChatColor.RED + "You are not holding an item in your main hand.");
                return;
            }
        }
    }
}
