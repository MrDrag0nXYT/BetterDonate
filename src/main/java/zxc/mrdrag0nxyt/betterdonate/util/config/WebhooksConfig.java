package zxc.mrdrag0nxyt.betterdonate.util.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;

import java.io.File;

public class WebhooksConfig {

    private final BetterDonate plugin;
    private File file;

    @Getter
    private FileConfiguration webhooksConfig;

    public WebhooksConfig(BetterDonate plugin) {
        this.plugin = plugin;

        loadWebhooks();
    }

    private void loadWebhooks() {
        file = new File(plugin.getDataFolder(), "webhooks.yml");
        if (!file.exists()) {
            plugin.saveResource("webhooks.yml", true);
        }

        this.webhooksConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void reloadWebhooks(){
        try {
            webhooksConfig.load(file);
        } catch (Exception e) {
            plugin.getLogger().severe(String.valueOf(e));
        }
    }

}
