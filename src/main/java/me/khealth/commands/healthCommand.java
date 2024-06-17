package me.khealth.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class healthCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Эту команду нельзя использовать в консоли!");
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (Objects.requireNonNull(target).isOnline()) {
            Bukkit.getPlayer(args[0]);
        } else {
            player.sendMessage(ChatColor.RED + "Такого игрока нет на сервере!");
        }
        if (player.hasPermission("khealth.health")) {
            if (args.length > 0) {
                final double newHealth = Double.parseDouble(args[1]);
                final double oldHealth = player.getHealth();
                target.setHealth(newHealth);

                player.sendMessage("Здоровье игрока ", target.getName(), " было изменено.");

                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "У вас недостаточно прав!");
        }

        return true;
    }
}
