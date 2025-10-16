package ru.nezxenka.litetotemrune.handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.nezxenka.litetotemrune.LiteTotemRune;
import ru.nezxenka.litetotemrune.utils.ConfigurationHelper;
import ru.nezxenka.litetotemrune.utils.ItemCreator;

public class CommandHandler implements CommandExecutor {
    private final LiteTotemRune plugin;
    private final ConfigurationHelper configHelper;
    private final ItemCreator itemCreator;

    public CommandHandler(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configHelper = plugin.getConfigHelper();
        this.itemCreator = plugin.getItemCreator();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("litetotemrune.admin")) {
            sender.sendMessage(configHelper.getText("no-access"));
            return true;
        }

        if (args.length < 3 || !args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(configHelper.getText("correct-syntax"));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(configHelper.getText("target-missing"));
            return true;
        }

        try {
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                sender.sendMessage(configHelper.getText("wrong-quantity"));
                return true;
            }

            ItemStack rune = itemCreator.generateRuneItem(amount);
            targetPlayer.getInventory().addItem(rune);

            String successMessage = configHelper.getText("item-given")
                    .replace("{amount}", String.valueOf(amount))
                    .replace("{player}", targetPlayer.getName());
            sender.sendMessage(successMessage);

        } catch (NumberFormatException e) {
            sender.sendMessage(configHelper.getText("wrong-quantity"));
        }

        return true;
    }
}