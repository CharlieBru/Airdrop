package me.charliebru.airdrop.listeners;

import me.charliebru.airdrop.Airdrop;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class ThrowListener implements Listener {

    private Random random = new Random();

    @EventHandler
    public void onThrow(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.NAME_TAG) {
            if (event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&6&lAirdrop Token"))) {
                Player player = event.getPlayer();

                if (event.getItem().getAmount() > 1) {
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                } else {
                    event.getItem().setType(Material.AIR);
                }

                Location eyeLocation = player.getEyeLocation();
                eyeLocation.add(0, 30, 0);
                Vector direction = player.getLocation().getDirection().multiply(2);
                Location infront = eyeLocation.add(direction);

                final Location spawnChest = new Location(player.getWorld(), infront.getX(), infront.getY(), infront.getZ());
                final ArmorStand stand = player.getWorld().spawn(spawnChest, ArmorStand.class);

                stand.setGravity(true);
                stand.setSmall(false);
                ItemStack helm = new ItemStack(Material.CHEST);
                stand.setHelmet(helm);
                stand.setVisible(false);
                stand.setCustomName("§c§l" + player.getName() + "'s Airdrop");
                stand.setCustomNameVisible(true);

                new BukkitRunnable() {
                    public void run() {
                        if (stand.isOnGround()) {
                            this.cancel();
                            stand.setSmall(true);
                            stand.getWorld().getBlockAt(stand.getLocation()).setType(Material.CHEST);

                            if (stand.getWorld().getBlockAt(stand.getLocation()).getType() == Material.CHEST) {
                                Chest chest = (Chest) stand.getWorld().getBlockAt(stand.getLocation()).getState();
                                chest.setCustomName(ChatColor.YELLOW + "Wow an Airdrop");
                                chest.update();
                                stand.setHelmet(new ItemStack(Material.AIR));

                                Inventory inventory = chest.getInventory();

                                for (int i = 0; i < 4; i++) {
                                    inventory.addItem(Airdrop.getInstance().items.get(random.nextInt(Airdrop.getInstance().items.size())));
                                }
                            }
                        }
                    }
                }.runTaskTimer(Airdrop.getInstance(), 20L, 20L);
            }
        }
    }
}



