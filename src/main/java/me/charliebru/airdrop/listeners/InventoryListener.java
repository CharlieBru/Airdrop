package me.charliebru.airdrop.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryInteract(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof Chest) {
            Chest chest = (Chest) event.getInventory().getHolder();
            if (chest.getCustomName() != null) {
                if (chest.getCustomName().equals(ChatColor.YELLOW + "Wow an Airdrop")) {
                    if (emptyInventory(event.getInventory())) {
                        chest.getBlock().setType(Material.AIR);
                        for (Entity entity : chest.getBlock().getWorld().getNearbyEntities(chest.getLocation(), 1, 1, 1)) {
                            if (entity instanceof ArmorStand) {
                                entity.remove();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean emptyInventory(Inventory inventory) {
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null) {
                return false;
            }
        }
        return true;
    }
}
