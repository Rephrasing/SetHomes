package me.rephrasing.sethomes.commands;

import lombok.SneakyThrows;
import me.rephrasing.sethomes.SetHomes;
import me.rephrasing.sethomes.config.ConfigSettings;
import me.rephrasing.sethomes.modules.HomeModule;
import me.rephrasing.sethomes.modules.PlayerHomesManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomesCommand implements CommandExecutor {
    @SneakyThrows
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        PlayerHomesManager manager = new PlayerHomesManager(player.getUniqueId());
        ConfigSettings settings = SetHomes.getConfigSettings();

        if (args.length != 0) {
            return false;
        }
        if (manager.homesSize() == 0) {
            SetHomes.sendPlayerMessage(player, "&cYou do not have any homes to display.");
            return true;
        }
        StringBuilder builder = new StringBuilder();

        int index = 1;
        for (HomeModule module : manager.getPlayerHomesList()) {
            if (module.getLocation().getWorld() != null) {
                String moduleName = "&f" + index +  ". &7Home name: &f\"&7" + module.getName() + "&f\" -> " + toStringLoc(module.getLocation());
                builder.append(moduleName).append("\n");
                index++;
            } else {
                manager.removeHomeModule(module.getName());
                SetHomes.sendPlayerMessage(player, "Your home with the name \"" + module.getName() + "\" was deleted automatically. World is corrupt.");
                if (manager.homesSize() == 0) {
                    SetHomes.sendPlayerMessage(player, "&cYou do not have any homes to display.");
                    return true;
                }
            }
        }
        SetHomes.sendPlayerMessage(player, "Your current homes:");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', builder.toString()));
        if (settings.getMaxHomes() > manager.homesSize())
        SetHomes.sendPlayerMessage(player, "You still have (" + (settings.getMaxHomes() - manager.homesSize()) + ") empty home slots.");
        return true;
    }

    public String toStringLoc(Location location) {
        String env = "&8Unknown";
        switch (location.getWorld().getEnvironment()) {
            case NORMAL -> env = "&aOverworld";
            case NETHER -> env = "&cNether";
            case THE_END -> env = "&eEnd";
        }
        return "&8{&6x:" + location.getBlockX() + "&7, &6y:" + location.getBlockY() + "&7, &6z:" + location.getBlockZ() + "&8}&7, &fdimension: [" + env  + "&f]";
    }
}
