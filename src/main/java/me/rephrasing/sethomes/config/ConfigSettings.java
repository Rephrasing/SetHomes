package me.rephrasing.sethomes.config;

import me.rephrasing.sethomes.SetHomes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class ConfigSettings {

    JavaPlugin main;
    FileConfiguration config;

    public ConfigSettings() {
        main = SetHomes.getInstance();
        main.saveDefaultConfig();
        config = main.getConfig();

        if (!assureSafeConfig()) {
            resetConfig();
        } else {
            main.getLogger().info("Successfully loaded config.yml");
            if (getMaxHomes() == 0) {
                main.getLogger().info("maxHomes in config was set to 0");
                main.getServer().getPluginManager().disablePlugin(main);
            }
        }
    }

    public boolean assureSafeConfig() {
        if (!config.isInt("max-homes")) {
            return false;
        }
        if (!config.isInt("cooldowns.sethome")){
            if (!config.isDouble("cooldowns.sethome")) {
                return false;
            }
        }
        if (!config.isInt("cooldowns.home")) {
            if (!config.isDouble("cooldowns.home")) {
                return false;
            }
        }
        if (!config.isInt("cooldowns.deletehome")) {
            if (!config.isDouble("cooldowns.deletehome")) {
                return false;
            }
        }
        return true;
    }

    public void resetConfig() {
        new File(main.getDataFolder(), "config.yml").delete();
        main.getLogger().severe("Configuration was reset due to invalid values");
        main.saveDefaultConfig();
    }

    public Double getSetHomesCooldown() {
        double value = Double.valueOf(config.getString("cooldowns.sethome"));
        return value != 0.0 ? value : null;
    }
    public Double getHomeCooldown() {
        double value = Double.valueOf(config.getString("cooldowns.home"));
        return value != 0.0 ? value : null;
    }
    public Double getDeleteHomeCooldown() {
        double value = Double.valueOf(config.getString("cooldowns.deletehome"));
        return value != 0.0 ? value : null;
    }
    public int getMaxHomes() {
        return config.getInt("max-homes");
    }



}
