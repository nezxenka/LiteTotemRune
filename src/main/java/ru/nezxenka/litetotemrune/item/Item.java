package ru.nezxenka.litetotemrune.item;

import ru.nezxenka.litetotemrune.LiteTotemRune;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Item {

    public ItemStack giveItem(Player player) {
        ItemStack item = new ItemStack(Material.ORANGE_DYE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6«Бессмертие»"));
        itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.getPersistentDataContainer().set(
                new NamespacedKey(LiteTotemRune.getInstance(), "immortality"),
                PersistentDataType.INTEGER, 1
        );

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&8Эффект руны"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5 &7Особенности:"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5 &6 - после активации тотема с этим эффектом,"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5 &0.&6   Вы получите неуязвимость к урону"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5 &0.&6   продолжительностью 3 секунды;"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5 &6 - возможность наложить данный эффект"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5 &0.&6   на тотем через наковальню;"));
        lore.add(ChatColor.translateAlternateColorCodes('&', " "));

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}