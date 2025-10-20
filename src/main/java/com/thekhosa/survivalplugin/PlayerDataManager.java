package com.thekhosa.survivalplugin;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerDataManager {

    private final File file;
    private YamlConfiguration config;

    public PlayerDataManager(File file) {
        this.file = file;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean hasJoinedBefore(UUID uuid) {
        return config.getBoolean("players." + uuid.toString() + ".hasJoined", false);
    }

    public void setHasJoined(UUID uuid) {
        config.set("players." + uuid.toString() + ".hasJoined", true);
        config.set("players." + uuid.toString() + ".firstJoinTime", System.currentTimeMillis());
        save();
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
