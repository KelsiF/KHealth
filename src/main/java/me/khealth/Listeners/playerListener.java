package me.khealth.Listeners;

import me.khealth.utils.Crawl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class playerListener implements Listener {

    JavaPlugin plugin;
    public playerListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private static final Map<UUID, Crawl> crowlingPlayers = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onToggleSwim(EntityToggleSwimEvent event) {
        if (Crawl.findPlayer(event.getEntity().getUniqueId()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Crawl crawl = Crawl.findPlayer(player);

        if (crawl != null && event.isSneaking()) {
            crawl.stop();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Crawl crawl = Crawl.findPlayer(player);
        Block clickedBlock = event.getClickedBlock();

        if (crawl != null && clickedBlock != null && clickedBlock.getLocation().equals(crawl.getBarrierLocation())) {
            crawl.update(clickedBlock.getLocation());

            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        double health = player.getHealth();

        Crawl crawl = Crawl.findPlayer(player);

        if (crawl != null) {
            Location from = event.getFrom();
            Location to = event.getTo();

            if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
                crawl.update(from.clone());
            }
        }

        if (health < 10) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                crawl.start(player);

            }, 30);
        }

    }

}
