package me.khealth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class healthCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (player.hasPermission("khealth.health")) {
            if (args.length >= 1) {
                target.setHealth(Double.parseDouble(args[1]));
            }
        } else {
            player.sendMessage(Objects.requireNonNull(command.getPermissionMessage()));
        }

        return true;
    }
}
