package ru.nezxenka.litetotemrune;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.nezxenka.litetotemrune.handlers.CommandHandler;
import ru.nezxenka.litetotemrune.handlers.AnvilHandler;
import ru.nezxenka.litetotemrune.handlers.TotemHandler;
import ru.nezxenka.litetotemrune.utils.ConfigurationHelper;
import ru.nezxenka.litetotemrune.utils.ItemCreator;

public final class LiteTotemRune extends JavaPlugin {
    private static LiteTotemRune plugin;
    private ConfigurationHelper configHelper;
    private ItemCreator itemCreator;

    @Override
    public void onEnable() {
        plugin = this;

        configHelper = new ConfigurationHelper(this);
        configHelper.initializeConfiguration();

        itemCreator = new ItemCreator(this);

        registerCommands();
        registerEvents();

        getLogger().info("LiteTotemRune активирован!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LiteTotemRune деактивирован!");
    }

    private void registerCommands() {
        getCommand("litetotemrune").setExecutor(new CommandHandler(this));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new AnvilHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new TotemHandler(this), this);
    }

    public static LiteTotemRune getPlugin() {
        return plugin;
    }

    public ConfigurationHelper getConfigHelper() {
        return configHelper;
    }

    public ItemCreator getItemCreator() {
        return itemCreator;
    }
}