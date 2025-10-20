package com.thekhosa.survivalplugin.listeners;

import com.thekhosa.survivalplugin.SafeLocationFinder;
import com.thekhosa.survivalplugin.SurvivalPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final SurvivalPlugin plugin;
    private static final int MAX_WORLD_SIZE = 29999984; // Maximum Minecraft world border

    public PlayerJoinListener(SurvivalPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if this is the player's first join
        if (!plugin.getPlayerDataManager().hasJoinedBefore(player.getUniqueId())) {
            // Teleport to random location
            World world = player.getWorld();

            // Use world border size or default max size
            double worldBorderSize = world.getWorldBorder().getSize() / 2;
            int maxRange = (int) Math.min(worldBorderSize, MAX_WORLD_SIZE);

            Location safeLocation = SafeLocationFinder.findSafeLocation(world, maxRange);

            if (safeLocation != null) {
                player.teleport(safeLocation);
                player.sendMessage("§aWelcome! You've been teleported to a random location.");
                player.sendMessage("§eUse /wild to explore more random locations!");
            }

            // Mark player as having joined
            plugin.getPlayerDataManager().setHasJoined(player.getUniqueId());
        }
    }
}
