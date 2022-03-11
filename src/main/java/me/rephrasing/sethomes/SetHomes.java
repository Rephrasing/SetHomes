package me.rephrasing.sethomes;

import me.rephrasing.sethomes.commands.DeleteHomeCommand;
import me.rephrasing.sethomes.commands.HomeCommand;
import me.rephrasing.sethomes.commands.HomesCommand;
import me.rephrasing.sethomes.commands.SetHomeCommand;
import me.rephrasing.sethomes.config.ConfigSettings;
import me.rephrasing.sethomes.listeners.HomeAssuranceListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetHomes extends JavaPlugin {

    static SetHomes INSTANCE;
    static ConfigSettings configSettings;

    @Override
    public void onEnable() {
        INSTANCE = this;
        getDataFolder().mkdirs();
        configSettings = new ConfigSettings();
        reloadConfig();
        registerCommands();
        getServer().getPluginManager().registerEvents(new HomeAssuranceListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static void sendPlayerMessage(Player player, String message) {
        String prefix = "&8[&6SetHomes&8] &7";
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    private void registerCommands() {
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("deletehome").setExecutor(new DeleteHomeCommand());
        getCommand("homes").setExecutor(new HomesCommand());
    }


    public static JavaPlugin getInstance() {
        return INSTANCE;
    }

    public static ConfigSettings getConfigSettings() {
        return configSettings;
    }
}
