package me.khealth.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;


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


        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (health < 10.0) {
                player.sendBlockChange(location, Material.BARRIER.createBlockData());
                player.sendBlockChange(location.add(-1, 0, -1), Material.AIR.createBlockData());
                player.sendBlockChange(location.add(1, 0, 1), Material.AIR.createBlockData());
                player.setSwimming(true);

                player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(20, 1));
                player.addPotionEffect(PotionEffectType.SLOW.createEffect(20, 1));
                player.addPotionEffect(PotionEffectType.SLOW_DIGGING.createEffect(20, 1));
            }
            if (health >= 10.0) {
                if (event.getPlayer().getLocation().getBlock().getType() != Material.WATER) {
                    player.setSwimming(false);
                }
            }
        }, 2);
    }

}
