package ru.nezxenka.litetotemrune.listeners;

import ru.nezxenka.litetotemrune.LiteTotemRune;
import ru.nezxenka.litetotemrune.managers.ConfigManager;
import ru.nezxenka.litetotemrune.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class TotemUseListener implements Listener {
    private final LiteTotemRune plugin;
    private final ConfigManager configManager;
    private final ItemManager itemManager;

    public TotemUseListener(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.itemManager = new ItemManager(plugin);
    }

    @EventHandler
    public void onTotemActivate(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        ItemStack totem = getUsedTotem(player);

        if (itemManager.isRune(totem)) {
            player.setInvulnerable(true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setInvulnerable(false);
                }
            }.runTaskLater(plugin, configManager.getInvulnerabilityDuration());
        }
    }

    private ItemStack getUsedTotem(Player player) {
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