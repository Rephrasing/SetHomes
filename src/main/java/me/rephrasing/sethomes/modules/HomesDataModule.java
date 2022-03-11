package me.rephrasing.sethomes.modules;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HomesDataModule {


    private final HashMap<UUID, List<HomeModule>> playerHomes;

    public HomesDataModule() {
        playerHomes = new HashMap<>();
    }

    HomesDataModule(HashMap<UUID, List<HomeModule>> data) {
        this.playerHomes = data;
    }

    public HashMap<UUID, List<HomeModule>> getPlayerHomes() {
        return playerHomes;
    }
}
