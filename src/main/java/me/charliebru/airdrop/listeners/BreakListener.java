package me.charliebru.airdrop.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

    @EventHandler
    public void onAirdropBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.CHEST) {
            Chest chest = (Chest) event.getBlock().getState();
            if (chest.getCustomName() != null && chest.getCustomName().equals(ChatColor.YELLOW + "Wow an Airdrop")) {
                for (Entity entity : chest.getBlock().getWorld().getNearbyEntities(chest.getLocation(), 1, 1, 1)) {
                    if (entity instanceof ArmorStand) {
                        entity.remove();
                    }
                }
            }
        }
    }
}
