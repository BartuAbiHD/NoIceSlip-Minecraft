package com.bartuabihd.noiceslip;

import java.util.function.*;
import org.bukkit.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class UpdateChecker {

    private NoIceSlip plugin;
    private int resourceId;

    public UpdateChecker(NoIceSlip plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getLatestVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
                plugin.getLogger().info("[NoIceSlip] Cannot call updates: " + exception.getMessage());
            }
        });
    }
}