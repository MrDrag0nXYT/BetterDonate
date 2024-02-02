package zxc.mrdrag0nxyt.betterdonate.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;

import java.io.File;

public class PurchasesCounterFile {

    private BetterDonate plugin;
    private File file;
    private File folder;
    private FileConfiguration config;

    public PurchasesCounterFile(BetterDonate plugin){
        this.plugin = plugin;

        loadFile();
    }

    private void loadFile(){
        folder = new File(plugin.getDataFolder() + File.separator + "data");
        if (!folder.exists()){
            folder.mkdirs();
        }

        file = new File(folder, "purchases.yml");
        if (!file.exists()){
            plugin.saveResource("data" + File.separator + "purchases.yml", true);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadFile(){
        plugin.saveDefaultConfig();

        try{
            config.load(file);
        } catch (Exception e){
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

    public void saveFile(){
        try {
            config.save(file);
        } catch (Exception e) {
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

    public FileConfiguration getConfig(){
        return config;
    }
}
