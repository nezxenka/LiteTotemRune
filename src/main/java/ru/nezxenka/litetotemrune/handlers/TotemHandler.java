package ru.nezxenka.litetotemrune.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import ru.nezxenka.litetotemrune.LiteTotemRune;
import ru.nezxenka.litetotemrune.utils.ConfigurationHelper;
import ru.nezxenka.litetotemrune.utils.ItemCreator;

public class TotemHandler implements Listener {
    private final LiteTotemRune plugin;
    private final ConfigurationHelper configHelper;
    private final ItemCreator itemCreator;

    public TotemHandler(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configHelper = plugin.getConfigHelper();
        this.itemCreator = plugin.getItemCreator();
    }

    @EventHandler
    public void onTotemActivate(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        ItemStack totem = getActiveTotem(player);

        if (itemCreator.isRuneItem(totem)) {
            player.setInvulnerable(true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setInvulnerable(false);
                }
            }.runTaskLater(plugin, configHelper.getEffectDuration());
        }
    }

    private ItemStack getActiveTotem(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.getType() == Material.TOTEM_OF_UNDYING) {
            return mainHand;
        } else if (offHand.getType() == Material.TOTEM_OF_UNDYING) {
            return offHand;
        }

        return null;
    }
}