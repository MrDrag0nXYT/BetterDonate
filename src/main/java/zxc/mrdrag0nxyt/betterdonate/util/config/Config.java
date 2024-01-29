package zxc.mrdrag0nxyt.betterdonate.util.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;

import java.io.File;

public class Config {

    private final BetterDonate plugin;
    private File file;
    private FileConfiguration config;

    public Config(BetterDonate plugin){
        this.plugin = plugin;

        loadConfig();
    }



    public void loadConfig(){
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()){
            plugin.saveDefaultConfig();
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadConfig(){
        plugin.saveDefaultConfig();

        try{
            config.load(file);
        } catch (Exception e){
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

    public FileConfiguration getConfig(){
        return config;
    }

}
