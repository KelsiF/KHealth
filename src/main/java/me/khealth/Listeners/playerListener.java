package me.khealth.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;


public class playerListener implements Listener {

    JavaPlugin plugin;
    public playerListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }



    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        double health = player.getHealth();
        Location location = player.getLocation().add(0, 1, 0);
        Location backLocation = player.getLocation().add(-1, 1, -1);
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();


        if (health < 10.0) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.sendBlockChange(location, Material.BARRIER.createBlockData());
                player.setSwimming(true);
                Material material = player.getWorld().getBlockAt(x,y,z).getType();
                Location loc = player.getLocation();

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.sendBlockChange(loc, material.createBlockData());
                }, 1);
            }, 2);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.addPotionEffect(PotionEffectType.DARKNESS.createEffect(100, 1));
                player.addPotionEffect(PotionEffectType.SLOW.createEffect(100, 1));
                player.addPotionEffect(PotionEffectType.SLOW_DIGGING.createEffect(100, 1));
            }, 80);
                if (health >= 10.0) {
                    if (event.getPlayer().getLocation().getBlock().getType() != Material.WATER) {
                        player.setSwimming(false);
                    }
                }
        }
    }
}
