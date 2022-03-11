package me.rephrasing.sethomes.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import me.rephrasing.sethomes.SetHomes;
import me.rephrasing.sethomes.modules.HomeModule;
import me.rephrasing.sethomes.modules.HomesDataModule;
import org.bukkit.Location;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataManager {

    private final File dataFile;
    private HomesDataModule homesDataModule;
    private final Gson gson;

    @SneakyThrows
    public DataManager() {
        this.dataFile = new File(SetHomes.getInstance().getDataFolder(), "playerHomes" + ".json");
        gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationSerializer()).setPrettyPrinting().serializeNulls().create();

        if (!dataFile.exists() || dataFile.length() == 0) {
            dataFile.createNewFile();
            this.homesDataModule = new HomesDataModule();
            setDataModule(homesDataModule);

        } else {
            this.homesDataModule = gson.fromJson(new FileReader(dataFile), HomesDataModule.class);
        }
    }

    public HashMap<UUID, List<HomeModule>> getHomesData() {
        return homesDataModule.getPlayerHomes();
    }

    @SneakyThrows
    public void setDataModule(HomesDataModule module) {
        this.homesDataModule = module;
        FileWriter writer = new FileWriter(dataFile);
        writer.write(gson.toJson(homesDataModule));
        writer.close();
    }



}
