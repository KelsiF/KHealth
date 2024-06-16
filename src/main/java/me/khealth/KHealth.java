package me.khealth;

import me.khealth.Listeners.playerListener;
import me.khealth.commands.healthCommand;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class KHealth extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "Плагин запущен!");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new playerListener(this), this);

        getCommand("sethealth").setExecutor(new healthCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "Плагин выключен!");
    }
}
