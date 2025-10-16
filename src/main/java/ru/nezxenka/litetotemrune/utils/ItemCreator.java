package ru.nezxenka.litetotemrune.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.nezxenka.litetotemrune.LiteTotemRune;

import java.util.List;

public class ItemCreator {
    private final LiteTotemRune plugin;
    private final ConfigurationHelper configHelper;
    private final NamespacedKey immortalityKey;

    public ItemCreator(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configHelper = plugin.getConfigHelper();
        this.immortalityKey = new NamespacedKey(plugin, "immortality_rune");
    }

    public ItemStack generateRuneItem(int quantity) {
        Material mat = Material.getMaterial(configHelper.getItemMaterial());
        if (mat == null) {
            mat = Material.ORANGE_DYE;
        }

        ItemStack item = new ItemStack(mat, quantity);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(configHelper.getItemName());
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.getPersistentDataContainer().set(immortalityKey, PersistentDataType.INTEGER, 1);

        List<String> lore = configHelper.getRuneLore();
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public boolean isRuneItem(ItemStack item) {
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