package ru.nezxenka.litetotemrune.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class AnvilUse implements Listener {
    private final JavaPlugin plugin;
    private final NamespacedKey immortalityKey;

    public AnvilUse(JavaPlugin plugin) {
        this.plugin = plugin;
        this.immortalityKey = new NamespacedKey(plugin, "immortality");
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);

        if (first != null && second != null && first.getType() == Material.TOTEM_OF_UNDYING) {
            ItemMeta secondMeta = second.getItemMeta();
            if (secondMeta != null && secondMeta.getPersistentDataContainer().has(this.immortalityKey, PersistentDataType.INTEGER)) {
                ItemMeta firstMeta = first.getItemMeta();
                if (firstMeta != null && !firstMeta.getPersistentDataContainer().has(this.immortalityKey, PersistentDataType.INTEGER)) {
                    ItemStack result = first.clone();
                    ItemMeta meta = result.getItemMeta();
                    meta.getPersistentDataContainer().set(this.immortalityKey, PersistentDataType.INTEGER, 1);

                    List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                    lore.add(ChatColor.translateAlternateColorCodes('&', ""));
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&5&o&x&4&0&8&B&F&BЭффекты при активации: "));
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&5&o &6• Бессмертие"));
                    lore.add(ChatColor.translateAlternateColorCodes('&', " "));
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&5&o&7Применятся после использования"));

                    meta.setLore(lore);
                    result.setItemMeta(meta);
                    event.getInventory().setRepairCost(10);
                    event.setResult(result);
                }
            }
        }
    }
}