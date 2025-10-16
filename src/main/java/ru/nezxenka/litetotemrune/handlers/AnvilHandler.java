package ru.nezxenka.litetotemrune.handlers;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.nezxenka.litetotemrune.LiteTotemRune;
import ru.nezxenka.litetotemrune.utils.ConfigurationHelper;
import ru.nezxenka.litetotemrune.utils.ItemCreator;

import java.util.ArrayList;
import java.util.List;

public class AnvilHandler implements Listener {
    private final LiteTotemRune plugin;
    private final ConfigurationHelper configHelper;
    private final ItemCreator itemCreator;

    public AnvilHandler(LiteTotemRune plugin) {
        this.plugin = plugin;
        this.configHelper = plugin.getConfigHelper();
        this.itemCreator = plugin.getItemCreator();
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);

        if (first == null || second == null) return;

        if (first.getType() == Material.TOTEM_OF_UNDYING && itemCreator.isRuneItem(second)) {
            ItemMeta firstMeta = first.getItemMeta();
            if (firstMeta != null && !itemCreator.isRuneItem(first)) {
                if (second.getAmount() == 1) {
                    ItemStack result = first.clone();
                    ItemMeta resultMeta = result.getItemMeta();

                    resultMeta.getPersistentDataContainer().set(
                            itemCreator.getImmortalityKey(),
                            org.bukkit.persistence.PersistentDataType.INTEGER,
                            1
                    );

                    List<String> lore = resultMeta.hasLore() ?
                            new ArrayList<>(resultMeta.getLore()) :
                            new ArrayList<>();

                    lore.addAll(configHelper.getTotemLore());
                    resultMeta.setLore(lore);

                    result.setItemMeta(resultMeta);
                    event.getInventory().setRepairCost(configHelper.getAnvilCost());
                    event.setResult(result);
                }
            }
        }
    }
}