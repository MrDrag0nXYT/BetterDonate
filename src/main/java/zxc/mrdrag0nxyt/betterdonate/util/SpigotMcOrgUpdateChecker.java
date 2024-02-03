package zxc.mrdrag0nxyt.betterdonate.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;
import zxc.mrdrag0nxyt.betterdonate.util.config.LanguageConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class SpigotMcOrgUpdateChecker {

    private final BetterDonate plugin;
    private final FileConfiguration languageConfig;
    private final String SPIGOTMC_ORG_RESOURCE_ID = "114847";
    private final String SPIGOTMC_ORG_API_LINK = "https://api.spigotmc.org/legacy/update.php?resource=";

    public SpigotMcOrgUpdateChecker(BetterDonate plugin, LanguageConfig languageConfig){
        this.plugin = plugin;
        this.languageConfig = languageConfig.getLanguageConfig();
    }

    public void getVersion(final Consumer<String> consumer){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (
                    InputStream stream = new URL(SPIGOTMC_ORG_API_LINK + SPIGOTMC_ORG_RESOURCE_ID + "/~").openStream();
                    Scanner scanner = new Scanner(stream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException e) {
                plugin.getLogger().severe(ColorUtil.setColor(languageConfig.getString("update.check-failed")) + e.getMessage());
            }
        });
    }

}
