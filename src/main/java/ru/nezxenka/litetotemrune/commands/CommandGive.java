package ru.nezxenka.litetotemrune.commands;

import ru.nezxenka.litetotemrune.LiteTotemRune;
import ru.nezxenka.litetotemrune.managers.ConfigManager;
import ru.nezxenka.litetotemrune.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandGive implements CommandExecutor {
    private final LiteTotemRune plugin;
    private final ConfigManager configManager;
    private final ItemManager itemManager;

    public CommandGive(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.itemManager = new ItemManager(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("litetotemrune.admin")) {
            sender.sendMessage(configManager.getMessage("no-permission"));
            return true;
        }

        if (args.length < 3 || !args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(configManager.getMessage("usage"));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(configManager.getMessage("player-not-found"));
            return true;
        }

        try {
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                sender.sendMessage(configManager.getMessage("invalid-amount"));
                return true;
            }

            ItemStack rune = itemManager.createRune(amount);
            targetPlayer.getInventory().addItem(rune);

            String successMessage = configManager.getMessage("give-success")
                    .replace("{amount}", String.valueOf(amount))
                    .replace("{player}", targetPlayer.getName());
            sender.sendMessage(successMessage);

        } catch (NumberFormatException e) {
            sender.sendMessage(configManager.getMessage("invalid-amount"));
        }

        return true;
    }
}