package zxc.mrdrag0nxyt.betterdonate.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;

public class BetterDonatePlaceholderApiHook extends PlaceholderExpansion {

    private BetterDonate plugin;
    private FileConfiguration purchasesCounterFile;

    public BetterDonatePlaceholderApiHook(BetterDonate plugin, PurchasesCounterFile purchasesCounterFile) {
        this.plugin = plugin;
        this.purchasesCounterFile = purchasesCounterFile.getConfig();
    }


    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "betterdonate";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("purchases_count")) {
            return String.valueOf(purchasesCounterFile.getInt("players." + player.getName(), 0));
        }

        if (player == null) {
            return null;
        }

        return null;
    }
}
