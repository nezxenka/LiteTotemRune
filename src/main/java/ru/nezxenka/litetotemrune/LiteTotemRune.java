package ru.nezxenka.litetotemrune;

import ru.nezxenka.litetotemrune.commands.CommandGive;
import ru.nezxenka.litetotemrune.listeners.AnvilListener;
import ru.nezxenka.litetotemrune.listeners.TotemUseListener;
import ru.nezxenka.litetotemrune.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LiteTotemRune extends JavaPlugin {
    private static LiteTotemRune instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);
        configManager.loadConfig();

        registerCommands();
        registerListeners();

        getLogger().info("Плагин LiteTotemRune успешно запущен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Плагин LiteTotemRune отключен!");
    }

    private void registerCommands() {
        getCommand("litetotemrune").setExecutor(new CommandGive(this));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new AnvilListener(this), this);
        Bukkit.getPluginManager().registerEvents(new TotemUseListener(this), this);
    }

    public static LiteTotemRune getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}