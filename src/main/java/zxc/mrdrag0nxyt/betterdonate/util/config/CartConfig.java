package zxc.mrdrag0nxyt.betterdonate.util.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;

import java.io.File;

public class CartConfig {

    private BetterDonate plugin;
    private File file;
    private FileConfiguration cartConfig;

    public CartConfig(BetterDonate plugin){
        this.plugin = plugin;

        loadCartConfig();
    }



    public void loadCartConfig(){
        file = new File(plugin.getDataFolder(), "cart.yml");
        if (!file.exists()){
            plugin.saveResource("cart.yml", true);
        }

        cartConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void saveCartConfig(){
        try {
            cartConfig.save(file);
        } catch (Exception e) {
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

    public void reloadCartConfig(){
        try {
            cartConfig.load(file);
        } catch (Exception e) {
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

    public FileConfiguration getCartConfig(){
        return cartConfig;
    }
}
