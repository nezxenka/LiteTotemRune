package ru.nezxenka.litetotemrune.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TotemUseListener implements Listener {
    private final JavaPlugin plugin;
    private final NamespacedKey immortalityKey;

    public TotemUseListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.immortalityKey = new NamespacedKey(plugin, "immortality");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (player.hasMetadata("immortality_active")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("immortality_active")) {
            player.removeMetadata("immortality_active", this.plugin);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasMetadata("immortality_active")) {
            player.removeMetadata("immortality_active", this.plugin);
        }
    }

    @EventHandler
    public void onTotemActivate(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            ItemStack totem = getUsedTotem(player);
            if (checkTotem(totem)) {
                player.setMetadata("immortality_active", new FixedMetadataValue(this.plugin, true));
                new BukkitRunnable() {
                    public void run() {
                        player.removeMetadata("immortality_active", plugin);
                    }
                }.runTaskLater(this.plugin, 80L);
            }
        }
    }

    private ItemStack getUsedTotem(Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();
        return mainHand.getType() == Material.TOTEM_OF_UNDYING ? mainHand :
                (offHand.getType() == Material.TOTEM_OF_UNDYING ? offHand : null);
    }

    private boolean checkTotem(ItemStack item) {
        if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
            ItemMeta meta = item.getItemMeta();
            return meta != null && meta.getPersistentDataContainer().has(this.immortalityKey, PersistentDataType.INTEGER);
        }
        return false;
    }
}