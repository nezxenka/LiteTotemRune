package ru.nezxenka.litetotemrune.listeners;

import ru.nezxenka.litetotemrune.LiteTotemRune;
import ru.nezxenka.litetotemrune.managers.ConfigManager;
import ru.nezxenka.litetotemrune.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AnvilListener implements Listener {
    private final LiteTotemRune plugin;
    private final ConfigManager configManager;
    private final ItemManager itemManager;

    public AnvilListener(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.itemManager = new ItemManager(plugin);
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);

        if (first == null || second == null) return;

        if (first.getType() == Material.TOTEM_OF_UNDYING && itemManager.isRune(second)) {
            ItemMeta firstMeta = first.getItemMeta();
            if (firstMeta != null && !itemManager.isRune(first)) {
                if (second.getAmount() == 1) {
                    ItemStack result = first.clone();
                    ItemMeta resultMeta = result.getItemMeta();

                    resultMeta.getPersistentDataContainer().set(
                            itemManager.getImmortalityKey(),
                            org.bukkit.persistence.PersistentDataType.INTEGER,
                            1
                    );

                    List<String> lore = resultMeta.hasLore() ?
                            new ArrayList<>(resultMeta.getLore()) :
                            new ArrayList<>();

                    lore.addAll(configManager.getTotemLore());
                    resultMeta.setLore(lore);

                    result.setItemMeta(resultMeta);
                    event.getInventory().setRepairCost(configManager.getAnvilRepairCost());
                    event.setResult(result);
                }
            }
        }
    }
}