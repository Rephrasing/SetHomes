package me.rephrasing.sethomes.commands;

import me.rephrasing.sethomes.SetHomes;

import me.rephrasing.sethomes.config.ConfigSettings;
import me.rephrasing.sethomes.modules.PlayerHomesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class DeleteHomeCommand implements CommandExecutor {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        PlayerHomesManager manager = new PlayerHomesManager(player.getUniqueId());
        ConfigSettings settings = SetHomes.getConfigSettings();
        if (args.length != 1) {
            return false;
        }
        Double cooldownTime = settings.getDeleteHomeCooldown();
        String homeName = args[0];
        if (cooldownTime != null) {
            if(cooldowns.containsKey(player.getUniqueId())) {
                double secondsLeft = (((cooldowns.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000));
                if (secondsLeft > 0) {
                    SetHomes.sendPlayerMessage(player, "&cYou may use this command again in " + secondsLeft + " seconds");
                    return true;
                }
            }
        }
        try {
            manager.removeHomeModule(homeName);
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        } catch (IllegalAccessException e) {
            SetHomes.sendPlayerMessage(player, "&cYou do not have a home with the name \"" + homeName + "\"");
            return true;
        }
        SetHomes.sendPlayerMessage(player, "You have successfully deleted your home with the name \"" + homeName + "\"");
        SetHomes.sendPlayerMessage(player, "You currently have (" + manager.homesSize() + "/" + settings.getMaxHomes() + ") homes");
        return true;

    }
}
