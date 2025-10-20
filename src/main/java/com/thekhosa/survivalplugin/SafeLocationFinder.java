package com.thekhosa.survivalplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Random;

public class SafeLocationFinder {

    private static final Random random = new Random();
    private static final int MAX_ATTEMPTS = 50;
    private static final int SEARCH_RADIUS = 10;

    /**
     * Finds a safe random location in the world
     * @param world The world to search in
     * @param maxRange The maximum range from world spawn
     * @return A safe location or null if none found
     */
    public static Location findSafeLocation(World world, int maxRange) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            // Generate random X and Z coordinates
            int x = random.nextInt(maxRange * 2) - maxRange;
            int z = random.nextInt(maxRange * 2) - maxRange;

            // Get the highest block at this location
            Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);

            // Check if location is safe
            if (isSafeLocation(location)) {
                // Add 1 to Y to place player on top of the block
                location.add(0.5, 1, 0.5); // Center of the block
                return location;
            }

            // If not safe, try nearby locations
            Location nearbyLocation = findNearbyLocation(location);
            if (nearbyLocation != null) {
                return nearbyLocation;
            }
        }

        // If no safe location found, return world spawn
        return world.getSpawnLocation();
    }

    /**
     * Checks if a location is safe for teleportation
     */
    private static boolean isSafeLocation(Location location) {
        Block block = location.getBlock();
        Block below = block.getRelative(0, -1, 0);
        Block above = block.getRelative(0, 1, 0);
        Block above2 = block.getRelative(0, 2, 0);

        // Check if ground is solid
        if (!below.getType().isSolid()) {
            return false;
        }

        // Check if ground is safe (not lava, fire, etc.)
        if (isDangerous(below.getType())) {
            return false;
        }

        // Check if there's enough space (2 blocks high)
        if (!block.getType().isAir() && block.getType() != Material.WATER) {
            return false;
        }
        if (!above.getType().isAir() && above.getType() != Material.WATER) {
            return false;
        }

        // Make sure we're not spawning in deep water
        if (block.getType() == Material.WATER || above.getType() == Material.WATER) {
            return false;
        }

        return true;
    }

    /**
     * Tries to find a safe location nearby
     */
    private static Location findNearbyLocation(Location center) {
        World world = center.getWorld();
        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();

        for (int x = -SEARCH_RADIUS; x <= SEARCH_RADIUS; x++) {
            for (int z = -SEARCH_RADIUS; z <= SEARCH_RADIUS; z++) {
                int checkX = centerX + x;
                int checkZ = centerZ + z;
                int y = world.getHighestBlockYAt(checkX, checkZ);

                Location checkLocation = new Location(world, checkX, y, checkZ);
                if (isSafeLocation(checkLocation)) {
                    checkLocation.add(0.5, 1, 0.5);
                    return checkLocation;
                }
            }
        }
        return null;
    }

    /**
     * Checks if a material is dangerous to spawn on
     */
    private static boolean isDangerous(Material material) {
        return material == Material.LAVA ||
               material == Material.FIRE ||
               material == Material.MAGMA_BLOCK ||
               material == Material.CACTUS ||
               material == Material.SWEET_BERRY_BUSH ||
               material == Material.WITHER_ROSE ||
               material == Material.POWDER_SNOW;
    }
}
