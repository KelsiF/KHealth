package me.khealth.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;


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
        Location backLocation = player.getLocation().add(-2, 1, -2);


        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (health < 10.0) {
                player.sendBlockChange(location, Material.BARRIER.createBlockData());
                player.setSwimming(true);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    for (double i = -4.0; i < 0; i++) {
                        player.sendBlockChange(new Location(player.getWorld(), i, player.getLocation().getY(), i), Material.AIR.createBlockData());
                    } for (double i = 1.0; i <= 4; i++) {
                        player.sendBlockChange(new Location(player.getWorld(), i, player.getLocation().getY(), i), Material.AIR.createBlockData());
                    }
                }, 5);
            }
            if (health >= 10.0) {
                if (event.getPlayer().getLocation().getBlock().getType() != Material.WATER) {
                    player.setSwimming(false);
                    for (double i = -4.0; i < 0; i++) {
                        player.sendBlockChange(new Location(player.getWorld(), i, player.getLocation().getY(), i), Material.AIR.createBlockData());
                    } for (double i = 1.0; i <= 4; i++) {
                        player.sendBlockChange(new Location(player.getWorld(), i, player.getLocation().getY(), i), Material.AIR.createBlockData());
                    }
                }
            }
        }, 5);
    }

}
