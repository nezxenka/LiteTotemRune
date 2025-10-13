package ru.nezxenka.litetotemrune.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ConfigManager {
    private final JavaPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages." + path, ""));
    }

    public String getRuneDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', config.getString("rune-settings.display-name", "&6«Бессмертие»"));
    }

    public String getRuneMaterial() {
        return config.getString("rune-settings.material", "ORANGE_DYE");
    }

    public int getInvulnerabilityDuration() {
        return config.getInt("rune-settings.invulnerability-duration", 60);
    }

    public int getAnvilRepairCost() {
        return config.getInt("rune-settings.anvil-repair-cost", 10);
    }

    public List<String> getRuneLore() {
        return colorizeList(config.getStringList("rune-lore"));
    }

    public List<String> getTotemLore() {
        return colorizeList(config.getStringList("totem-lore"));
    }

    private List<String> colorizeList(List<String> list) {
        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        return list;
    }
}