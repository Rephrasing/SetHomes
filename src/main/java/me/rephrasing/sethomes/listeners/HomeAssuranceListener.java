package me.rephrasing.sethomes.listeners;

import me.rephrasing.sethomes.SetHomes;
import me.rephrasing.sethomes.modules.HomeModule;
import me.rephrasing.sethomes.modules.PlayerHomesManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;


public class HomeAssuranceListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerHomesManager manager = new PlayerHomesManager(event.getPlayer().getUniqueId());
        if (manager.homesSize() == 0) return;
        List<HomeModule> conflicts = new ArrayList<>();
        for (HomeModule module : manager.getPlayerHomesList()) {
            if (module.getLocation().getWorld() == null) {
                conflicts.add(module);
            }
        }
        StringBuilder builder = new StringBuilder();
        conflicts.forEach(homeModule -> {
            builder.append("&8- \"&f" + homeModule.getName() + "&8\"\n");
            try {
                manager.removeHomeModule(homeModule.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        if (!builder.isEmpty()) {
            SetHomes.sendPlayerMessage(event.getPlayer(), "&cYour homes with the following names were automatically deleted due to their worlds being corrupted/deleted.");
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', builder.toString()));
        }

    }
}
