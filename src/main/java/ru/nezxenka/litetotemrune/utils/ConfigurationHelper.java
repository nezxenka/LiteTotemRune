package ru.nezxenka.litetotemrune.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigurationHelper {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private FileConfiguration messages;
    private FileConfiguration lore;
    private File configFile;
    private File messagesFile;
    private File loreFile;

    public ConfigurationHelper(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initializeConfiguration() {
        createConfigs();
        loadConfigs();
    }

    private void createConfigs() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        loreFile = new File(plugin.getDataFolder(), "lore.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        if (!loreFile.exists()) {
            plugin.saveResource("lore.yml", false);
        }
    }

    private void loadConfigs() {
        config = YamlConfiguration.loadConfiguration(configFile);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        lore = YamlConfiguration.loadConfiguration(loreFile);
    }

    public void reloadConfiguration() {
        loadConfigs();
    }

    public String getText(String path) {
        return ChatColor.translateAlternateColorCodes('&', messages.getString("player-messages." + path, ""));
    }

    public String getItemName() {
        return ChatColor.translateAlternateColorCodes('&', config.getString("item-properties.display-title", "&6«Бессмертие»"));
    }

    public String getItemMaterial() {
        return config.getString("item-properties.material-type", "ORANGE_DYE");
    }

    public int getEffectDuration() {
        return config.getInt("effect-settings.protection-duration", 60);
    }

    public int getAnvilCost() {
        return config.getInt("effect-settings.repair-expense", 10);
    }

    public List<String> getRuneLore() {
        return colorizeList(lore.getStringList("rune-information"));
    }

    public List<String> getTotemLore() {
        return colorizeList(lore.getStringList("totem-details"));
    }

    private List<String> colorizeList(List<String> list) {
        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        return list;
    }
}