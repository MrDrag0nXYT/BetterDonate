package zxc.mrdrag0nxyt.betterdonate.util.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;

import java.io.File;

public class LanguageConfig {

    private final BetterDonate plugin;
    private File file;
    private File folder;
    @Getter
    private FileConfiguration languageConfig;

    public LanguageConfig(BetterDonate plugin, String language){
        this.plugin = plugin;

        loadLanguage(language);
    }



    public void loadLanguage(String language){
        initializeLangConfig(language);

        languageConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadLanguageConfig(String language){
        initializeLangConfig(language);

        try {
            languageConfig.load(file);
        } catch (Exception e) {
            plugin.getLogger().severe(String.valueOf(e));
        }
    }


    private void initializeLangConfig(String language){
        folder = new File(plugin.getDataFolder() + File.separator + "language");
        if (!folder.exists()){
            folder.mkdirs();
        }

        file = new File(folder, "messages_" + language + ".yml");
        if (!file.exists()){
            plugin.saveResource("language" + File.separator + "messages_" + language + ".yml", true);
        }
    }

}
