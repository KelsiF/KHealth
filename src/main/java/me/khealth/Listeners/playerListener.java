package me.khealth.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class playerListener implements Listener {

    JavaPlugin plugin;
    public playerListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {

        String fallMessage = plugin.getConfig().getString("fall-message");
        double fallHealth = plugin.getConfig().getDouble("fall-health");

        Player player = event.getPlayer();
        double health = player.getHealth();

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        Location loc = player.getLocation().add(0, 1, 0);
        Material material = player.getWorld().getBlockAt(x,y,z).getType();
        if (health < fallHealth) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {


                player.sendBlockChange(loc, Material.BARRIER.createBlockData());
                player.setSwimming(true);

                player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(5, 1));
                player.addPotionEffect(PotionEffectType.SLOW.createEffect(5, 1));
                player.addPotionEffect(PotionEffectType.SLOW_DIGGING.createEffect(5, 1));
            }, 1);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.sendBlockChange(loc, material.createBlockData());
            }, 40);
        } else if (health >= fallHealth) {
            player.setSwimming(false);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        } else if (health <= 0) {
            player.setSwimming(false);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SLOW);
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
    }
}
