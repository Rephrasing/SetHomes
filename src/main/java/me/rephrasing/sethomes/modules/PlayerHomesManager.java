package me.rephrasing.sethomes.modules;

import me.rephrasing.sethomes.data.DataManager;
import java.util.*;

public class PlayerHomesManager {

    private final DataManager dataManager;
    private final HashMap<UUID, List<HomeModule>> moduleToEdit;
    private final List<HomeModule> playerHomes;
    private final UUID playerUniqueId;

    public PlayerHomesManager(UUID playerUniqueId) {
        this.dataManager = new DataManager();
        this.playerUniqueId = playerUniqueId;
        this.moduleToEdit = dataManager.getHomesData();
        this.playerHomes = moduleToEdit.get(playerUniqueId) != null ? moduleToEdit.get(playerUniqueId) : new ArrayList<>();
    }


    public HomeModule getHomeModule(String name) {
        if (playerHomes.isEmpty()) return null;
        return playerHomes.stream().filter(homeModule -> homeModule.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void addHomeModule(HomeModule homeModule) throws IllegalAccessException {
        if (getHomeModule(homeModule.getName()) != null) throw new IllegalAccessException();
        playerHomes.add(homeModule);
        save();
    }

    public void removeHomeModule(String name) throws IllegalAccessException {
        HomeModule moduleToRemove = getHomeModule(name);
        if (moduleToRemove == null) throw new IllegalAccessException();
        playerHomes.remove(moduleToRemove);
        save();
    }

    public List<HomeModule> getPlayerHomesList() {
        return playerHomes;
    }

    public int homesSize() {
        return playerHomes.size();
    }

    public void save() {
        moduleToEdit.put(playerUniqueId, playerHomes);
        dataManager.setDataModule(new HomesDataModule(moduleToEdit));
    }




}
