package ru.nezxenka.litetotemrune;

import ru.nezxenka.litetotemrune.command.CommandGive;
import ru.nezxenka.litetotemrune.util.AnvilUse;
import ru.nezxenka.litetotemrune.util.TotemUseListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LiteTotemRune extends JavaPlugin {
    public static LiteTotemRune instance;

    public void onEnable() {
        instance = this;
        Bukkit.getPluginCommand("litetotemrune").setExecutor(new CommandGive());
        this.getServer().getPluginManager().registerEvents(new AnvilUse(this), this);
        this.getServer().getPluginManager().registerEvents(new TotemUseListener(this), this);
    }

    public static LiteTotemRune getInstance() {
        return instance;
    }
}