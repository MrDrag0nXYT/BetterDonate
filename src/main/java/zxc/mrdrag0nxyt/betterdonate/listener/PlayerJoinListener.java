package zxc.mrdrag0nxyt.betterdonate.listener;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;
import zxc.mrdrag0nxyt.betterdonate.util.ColorUtil;
import zxc.mrdrag0nxyt.betterdonate.util.config.CartConfig;
import zxc.mrdrag0nxyt.betterdonate.util.config.Config;
import zxc.mrdrag0nxyt.betterdonate.util.config.LanguageConfig;

import java.util.List;

public class PlayerJoinListener implements Listener {

    private BetterDonate plugin;
    private FileConfiguration config;
    private FileConfiguration languageConfig;
    private FileConfiguration cartConfig;

    public PlayerJoinListener(BetterDonate plugin, Config config, LanguageConfig languageConfig, CartConfig cartConfig){
        this.plugin = plugin;
        this.config = config.getConfig();
        this.languageConfig = languageConfig.getLanguageConfig();
        this.cartConfig = cartConfig.getCartConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ConfigurationSection section = cartConfig.getConfigurationSection("players." + event.getPlayer().getName() + ".products");

        if (section != null) {
            List<String> helpMessageList = languageConfig.getStringList("cart.on-join-notify");

            for (String helpMessage : helpMessageList) {
                event.getPlayer().sendMessage(ColorUtil.setColor(helpMessage));
            }
        }
    }
}
