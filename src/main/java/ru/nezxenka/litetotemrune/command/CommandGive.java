package ru.nezxenka.litetotemrune.command;

import ru.nezxenka.litetotemrune.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandGive implements CommandExecutor {
    Item itemRune = new Item();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("litetotemrune.admin")) {
            sender.sendMessage("У вас нет прав на использование этой команды");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("/litetotemrune give <игрок>");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            Player targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage("Игрок не найден");
                return true;
            }

            targetPlayer.getInventory().addItem(itemRune.giveItem(targetPlayer));
            sender.sendMessage("Вы успешно выдали для " + targetPlayer.getName() + " руну!");
        }

        return true;
    }
}