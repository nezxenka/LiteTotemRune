package ru.nezxenka.litetotemrune;

import ru.nezxenka.litetotemrune.command.CommandGive;
import ru.nezxenka.litetotemrune.util.AnvilUse;
import ru.nezxenka.litetotemrune.util.TotemUseListener;
import java.util.Base64;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LiteTotemRune extends JavaPlugin {
    public static LiteTotemRune instance;

    public void onEnable() {
        this.getLogger().info(new String(Base64.getDecoder().decode("I2s=")));
        this.getLogger().info(new String(Base64.getDecoder().decode("I2EgICAgICBMZWFrZWQgYnkgU2hpdFBvc3Q=")));
        this.getLogger().info(new String(Base64.getDecoder().decode("I3ogdC5tZS9zaGl0cG9zdF9ieQ==")));
        this.getLogger().info(new String(Base64.getDecoder().decode("I2k=")));
        instance = this;
        Bukkit.getPluginCommand("litetotemrune").setExecutor(new CommandGive());
        this.getServer().getPluginManager().registerEvents(new AnvilUse(this), this);
        this.getServer().getPluginManager().registerEvents(new TotemUseListener(this), this);
    }

    public static LiteTotemRune getInstance() {
        return instance;
    }
}