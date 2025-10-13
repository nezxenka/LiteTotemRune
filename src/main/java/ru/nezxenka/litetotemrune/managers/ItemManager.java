package ru.nezxenka.litetotemrune.managers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.nezxenka.litetotemrune.LiteTotemRune;

import java.util.List;

public class ItemManager {
    private final LiteTotemRune plugin;
    private final ConfigManager configManager;
    private final NamespacedKey immortalityKey;

    public ItemManager(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.immortalityKey = new NamespacedKey(plugin, "bessmertie");
    }

    public ItemStack createRune(int amount) {
        Material material = Material.getMaterial(configManager.getRuneMaterial());
        if (material == null) {
            material = Material.ORANGE_DYE;
        }

        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(configManager.getRuneDisplayName());
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.getPersistentDataContainer().set(immortalityKey, PersistentDataType.INTEGER, 1);

        List<String> lore = configManager.getRuneLore();
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public boolean isRune(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(immortalityKey, PersistentDataType.INTEGER);
    }

    public NamespacedKey getImmortalityKey() {
        return immortalityKey;
    }
}