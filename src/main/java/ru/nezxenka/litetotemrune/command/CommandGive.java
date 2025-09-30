package ru.nezxenka.litetotemrune.command;

import ru.nezxenka.litetotemrune.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandGive implements CommandExecutor {
    Item itemrune = new Item();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("litetotemrune.admin")) {
            sender.sendMessage("бесправный еблан");
            return true;
        } else if (args.length == 0) {
            sender.sendMessage("/litetotemrune give <name>");
            return true;
        } else if (args.length < 2) {
            sender.sendMessage("/litetotemrune give <name>");
            return true;
        } else {
            if (args[0].equalsIgnoreCase("give")) {
                Player playergive = Bukkit.getPlayer(args[1]);
                if (playergive == null) {
                    sender.sendMessage("Player is null");
                    return true;
                }

                playergive.getInventory().addItem(new ItemStack[]{this.itemrune.giveItem(playergive)});
                sender.sendMessage("Вы успешно выдали для " + playergive.getName() + " руну!");
            }

            return true;
        }
    }
}