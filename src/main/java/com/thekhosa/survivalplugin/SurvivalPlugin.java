package com.thekhosa.survivalplugin;

import org.bukkit.plugin.java.JavaPlugin;
import com.thekhosa.survivalplugin.listeners.PlayerJoinListener;
import com.thekhosa.survivalplugin.commands.WildCommand;

import java.io.File;

public class SurvivalPlugin extends JavaPlugin {

    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        // Create data folder if it doesn't exist
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Initialize player data manager
        playerDataManager = new PlayerDataManager(new File(getDataFolder(), "playerdata.yml"));

        // Register event listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        // Register commands
        getCommand("wild").setExecutor(new WildCommand(this));

        getLogger().info("SurvivalPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SurvivalPlugin has been disabled!");
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
