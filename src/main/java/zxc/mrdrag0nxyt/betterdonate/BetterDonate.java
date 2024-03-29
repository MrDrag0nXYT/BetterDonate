package zxc.mrdrag0nxyt.betterdonate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import zxc.mrdrag0nxyt.betterdonate.commands.AdminCommand;
import zxc.mrdrag0nxyt.betterdonate.commands.CartCommand;
import zxc.mrdrag0nxyt.betterdonate.commands.completer.AdminCompleter;
import zxc.mrdrag0nxyt.betterdonate.commands.completer.CartCompleter;
import zxc.mrdrag0nxyt.betterdonate.listener.PlayerJoinListener;
import zxc.mrdrag0nxyt.betterdonate.util.*;
import zxc.mrdrag0nxyt.betterdonate.util.config.CartConfig;
import zxc.mrdrag0nxyt.betterdonate.util.config.Config;
import zxc.mrdrag0nxyt.betterdonate.util.config.LanguageConfig;
import zxc.mrdrag0nxyt.betterdonate.util.config.WebhooksConfig;

import static org.bukkit.Bukkit.getPluginManager;

public final class BetterDonate extends JavaPlugin {

    private Config configFile;
    private LanguageConfig languageConfigFile;
    private CartConfig cartConfigFile;
    private WebhooksConfig webhooksConfigFile;
    private PurchasesCounterFile purchasesCounterFile;

    private FileConfiguration mainConfig;

    @Override
    public void onEnable() {
        configFile = new Config(this);
        mainConfig = configFile.getConfig();
        cartConfigFile = new CartConfig(this);
        webhooksConfigFile = new WebhooksConfig(this);
        purchasesCounterFile = new PurchasesCounterFile(this);

        languageConfigFile = new LanguageConfig(this, mainConfig.getString("language"));

        checkUpdate(mainConfig.getBoolean("check-update"));

        if (getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new BetterDonatePlaceholderApiHook(this, purchasesCounterFile).register();
        }

        if (mainConfig.getBoolean("enable-metrics")) {
            BStatsMetrics metrics = new BStatsMetrics(this, 20916);
        }



        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, configFile, languageConfigFile, cartConfigFile), this);

        getCommand("cart").setExecutor(new CartCommand(this, configFile, languageConfigFile, cartConfigFile));
        getCommand("cart").setTabCompleter(new CartCompleter());

        getCommand("betterdonate").setExecutor(new AdminCommand(this, configFile, languageConfigFile, cartConfigFile, purchasesCounterFile, webhooksConfigFile));
        getCommand("betterdonate").setTabCompleter(new AdminCompleter());

        showTitle(true);
    }

    @Override
    public void onDisable() {
        cartConfigFile.saveCartConfig();
        showTitle(false);
    }

    public void reloadConfigs(){
        configFile.reloadConfig();
        languageConfigFile.reloadLanguageConfig(mainConfig.getString("language"));
        cartConfigFile.reloadCartConfig();
        purchasesCounterFile.reloadFile();
        webhooksConfigFile.reloadWebhooks();
    }

    private void checkUpdate(boolean isCheckEnabled){
        if (isCheckEnabled) {
            new SpigotMcOrgUpdateChecker(this, languageConfigFile).getVersion(
                    version -> {
                        version = this.getDescription().getVersion();

                        if (this.getDescription().getVersion().equals(version)) {
                            getLogger().info(ColorUtil.setColor(languageConfigFile.getLanguageConfig().getString("update.no-updates")));
                        } else {
                            getLogger().info(ColorUtil.setColor(languageConfigFile.getLanguageConfig().getString("update.has-updates")));
                        }
                    });
        }
    }



    private void showTitle(boolean enabling){
        getLogger().info(" ");
        getLogger().info(ColorUtil.setColor(" &#a880ff█▄▄ █▀▀ ▀█▀ ▀█▀ █▀▀ █▀█ █▀▄ █▀█ █▄░█ ▄▀█ ▀█▀ █▀▀     &#C0C0C0|   &#fffafaVer. &#a880ff" + this.getDescription().getVersion() + " "));
        getLogger().info(ColorUtil.setColor(" &#a880ff█▄█ ██▄ ░█░ ░█░ ██▄ █▀▄ █▄▀ █▄█ █░▀█ █▀█ ░█░ ██▄     &#C0C0C0|   &#fffafaAuthor: &#a880ffMrDrag0nXYT "));
        getLogger().info(" ");

        if (enabling) {
            getLogger().info(ColorUtil.setColor(" &#00ff7fPlugin successfully loaded!"));
        } else {
            getLogger().info(ColorUtil.setColor(" &#dc143cPlugin successfully unloaded!"));
        }

        getLogger().info(" ");
    }
}
