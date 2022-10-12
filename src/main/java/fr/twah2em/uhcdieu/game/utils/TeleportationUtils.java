package fr.twah2em.uhcdieu.game.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeleportationUtils {
    private static final List<Location> locations = new ArrayList<>();
    public static void safeRandomlyTeleportPlayer(Player player) {
        player.teleport(generateRandomLocation(player));
    }

    public static Location generateRandomLocation(Player player) {
        final Random random = new Random();

        int x = random.nextInt(2000);
        int y = 150;
        int z = random.nextInt(2000);

        Location randomLocation = new Location(player.getWorld(), x, y, z);
        y = randomLocation.getWorld().getHighestBlockYAt(randomLocation);
        randomLocation.setY(y);

        if (!isLocationSafe(randomLocation)) {
            randomLocation = generateRandomLocation(player);
        }

        if (locations.contains(randomLocation)) {
            randomLocation = generateRandomLocation(player);
        }

        locations.add(randomLocation);

        return randomLocation;
    }

    public static boolean isLocationSafe(Location location) {
        final int x = location.getBlockX();
        final int y = location.getBlockY();
        final int z = location.getBlockZ();

        final Block block = location.getWorld().getBlockAt(location);
        final Block below = location.getWorld().getBlockAt(x, y - 1, z);
        final Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return below.getType().isSolid() || block.getType().isSolid() || above.getType().isSolid();
    }
}
