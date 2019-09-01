package me.charliebru.airdrop;

import lombok.Getter;
import me.charliebru.airdrop.commands.handlers.CommandHandler;
import me.charliebru.airdrop.listeners.BreakListener;
import me.charliebru.airdrop.listeners.InventoryListener;
import me.charliebru.airdrop.listeners.ThrowListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Airdrop extends JavaPlugin {

    @Getter private static Airdrop instance;
    public List<ItemStack> items = new ArrayList<>();

    public void onEnable() {
        instance = this;

        this.getServer().getPluginManager().registerEvents(new ThrowListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        this.getServer().getPluginManager().registerEvents(new BreakListener(), this);

        getCommand("airdrop").setExecutor(new CommandHandler());

        this.saveDefaultConfig();

        for (String key : this.getConfig().getConfigurationSection("Items").getKeys(false)) {
            ItemStack itemStack = Airdrop.getInstance().getConfig().getItemStack("Items." + key);
            items.add(itemStack);
        }
    }
}
