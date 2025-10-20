package com.thekhosa.survivalplugin.commands;

import com.thekhosa.survivalplugin.SafeLocationFinder;
import com.thekhosa.survivalplugin.SurvivalPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class WildCommand implements CommandExecutor {

    private final SurvivalPlugin plugin;
    private final HashMap<UUID, Long> cooldowns;
    private static final int COOLDOWN_SECONDS = 60; // 1 minute cooldown
    private static final int MAX_WORLD_SIZE = 29999984;

    public WildCommand(SurvivalPlugin plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        // Check cooldown
        if (cooldowns.containsKey(player.getUniqueId())) {
            long timeLeft = (cooldowns.get(player.getUniqueId()) + (COOLDOWN_SECONDS * 1000)) - System.currentTimeMillis();
            if (timeLeft > 0) {
                player.sendMessage("§cYou must wait " + (timeLeft / 1000) + " seconds before using /wild again!");
                return true;
            }
        }

        // Get world and calculate max range
        World world = player.getWorld();
        double worldBorderSize = world.getWorldBorder().getSize() / 2;
        int maxRange = (int) Math.min(worldBorderSize, MAX_WORLD_SIZE);

        // Find safe location
        player.sendMessage("§eSearching for a random location...");
        Location safeLocation = SafeLocationFinder.findSafeLocation(world, maxRange);

        if (safeLocation != null) {
            player.teleport(safeLocation);
            player.sendMessage("§aYou've been teleported to a random location!");

            // Set cooldown
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        } else {
            player.sendMessage("§cCouldn't find a safe location. Please try again!");
        }

        return true;
    }
}
