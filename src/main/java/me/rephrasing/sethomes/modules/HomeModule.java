package me.rephrasing.sethomes.modules;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HomeModule {

    private final String name;
    private final Location location;

    public HomeModule(String name, Player player) {
        this.name = name;
        this.location = player.getLocation();
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
