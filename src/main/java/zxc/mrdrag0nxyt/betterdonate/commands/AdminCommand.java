package zxc.mrdrag0nxyt.betterdonate.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;
import zxc.mrdrag0nxyt.betterdonate.util.ColorUtil;
import zxc.mrdrag0nxyt.betterdonate.util.DiscordWebhook;
import zxc.mrdrag0nxyt.betterdonate.util.PurchasesCounterFile;
import zxc.mrdrag0nxyt.betterdonate.util.config.WebhooksConfig;
import zxc.mrdrag0nxyt.betterdonate.util.product.ProductType;
import zxc.mrdrag0nxyt.betterdonate.util.config.CartConfig;
import zxc.mrdrag0nxyt.betterdonate.util.config.Config;
import zxc.mrdrag0nxyt.betterdonate.util.config.LanguageConfig;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class AdminCommand implements CommandExecutor {

    private final BetterDonate plugin;

    private final FileConfiguration config;
    private final FileConfiguration languageConfig;
    private final FileConfiguration cartConfig;
    private final PurchasesCounterFile purchasesCounterFile;
    private final FileConfiguration purchasesCounter;
    private final FileConfiguration webhookConfig;

    private final CartConfig cartConfigFile;

    public AdminCommand(BetterDonate plugin, Config config, LanguageConfig languageConfig, CartConfig cartConfig, PurchasesCounterFile purchasesCounterFile, WebhooksConfig webhooksConfig) {
        this.plugin = plugin;
        this.config = config.getConfig();
        this.languageConfig = languageConfig.getLanguageConfig();

        this.cartConfigFile = cartConfig;
        this.cartConfig = cartConfigFile.getCartConfig();
        this.purchasesCounterFile = purchasesCounterFile;
        this.purchasesCounter = purchasesCounterFile.getConfig();
        this.webhookConfig = webhooksConfig.getWebhooksConfig();
    }



    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender.hasPermission("betterdonate.admin")) {

            if (strings.length == 0) {
                List<String> helpMessageList = languageConfig.getStringList("admin.help");

                for (String helpMessage : helpMessageList) {
                    commandSender.sendMessage(ColorUtil.setColor(helpMessage));
                }
                return false;
            }

            switch (strings[0].toLowerCase()){
                case "reload":
                    plugin.reloadConfigs();
                    commandSender.sendMessage(ColorUtil.setColor(
                            config.getString("prefix") +
                                    languageConfig.getString("reload")
                    ));

                    return true;

                case "give":
                    if (strings.length < 2) {
                        commandSender.sendMessage(ColorUtil.setColor(
                                config.getString("prefix") +
                                        languageConfig.getString("admin.give.usage")
                        ));
                        return false;
                    }

                    boolean isAdded = addToCart(strings);

                    if (isAdded) {

                        String productName = "true";
                        try {
                            productName = strings[3];
                        } catch (ArrayIndexOutOfBoundsException ignore) {
                        }

                        commandSender.sendMessage(ColorUtil.setColor(
                                config.getString("prefix") +
                                        languageConfig.getString("admin.give.success")
                                                .replace("%player%", strings[1])
                                                .replace("%product%", strings[2] + " (" + productName + ")")
                        ));

                    } else {
                        commandSender.sendMessage(ColorUtil.setColor(
                                config.getString("prefix") +
                                        languageConfig.getString("admin.give.disabled")
                                                .replace("%product%", strings[2])
                        ));
                    }

                    return true;

                case "clear":
                    if (strings.length < 2) {
                        commandSender.sendMessage(ColorUtil.setColor(
                                config.getString("prefix") +
                                        languageConfig.getString("admin.clear.usage")
                        ));
                        return false;
                    }

                    cartConfig.set("players." + strings[1], null);

                    commandSender.sendMessage(ColorUtil.setColor(
                            config.getString("prefix") +
                                    languageConfig.getString("admin.clear.success")
                                            .replace("%player%", strings[1])
                    ));
                    cartConfigFile.saveCartConfig();

                    return true;

                default:
                    List<String> helpMessageList = languageConfig.getStringList("admin.help");

                    for (String helpMessage : helpMessageList) {
                        commandSender.sendMessage(ColorUtil.setColor(helpMessage));
                    }

                    return false;
            }

        } else {
            commandSender.sendMessage(ColorUtil.setColor(
                    config.getString("prefix") +
                            languageConfig.getString("no-permission")
            ));
            return false;
        }
    }


    private boolean addToCart(String[] strings) {
        ProductType productType;

        try {
            productType = ProductType.getProductType(strings[2].toLowerCase());
        } catch (NoSuchElementException e) {
            return false;
        }

        if (config.getBoolean(productType.getConfigKey() + ".enabled")) {

            if (productType != ProductType.COMMAND) {
                cartConfig.set("players." + strings[1] + "." + productType.getCartKey(), strings[3]);

            } else {
                String cmd = "";

                for (int i = 3; i < strings.length; i++) {
                    cmd += strings[i] + " ";
                }

                List<String> commandList = cartConfig.getStringList("players." + strings[1] + "." + productType.getCartKey());
                commandList.add(cmd.trim());

                cartConfig.set("players." + strings[1] + "." + productType.getCartKey(), commandList);
            }

            setPlayerPurchases(strings[1]);

        } else {
            return false;
        }

        sendWebhookMessage(config.getString("webhook.link"), strings[1], productType, strings);

        cartConfigFile.saveCartConfig();
        return true;
    }

    private void setPlayerPurchases(String playerName){
        int count = purchasesCounter.getInt("players." + playerName);
        count++;

        purchasesCounter.set("players." + playerName, count);
        purchasesCounterFile.saveFile();
    }



    private boolean sendWebhookMessage(String token, String playerName, ProductType productType, String[] strings){
        if (webhookConfig.getConfigurationSection(productType.getWebhookKey()) == null) {
            return false;
        }

        // Because webhook image urls can`t be null, used when not specified ("url" = null)
        final String DEFAULT_IMAGE_URL = "https://www.spigotmc.org/data/avatars/l/1959/1959589.jpg";

        String product = config.getString(productType.getConfigKey() + ".visible-name") + " (" + strings[3] + ")";

        DiscordWebhook webhook = new DiscordWebhook(token);

        webhook.setAvatarUrl(webhookConfig.getString(productType.getWebhookKey() + ".avatar-url", DEFAULT_IMAGE_URL));
        webhook.setUsername(replaceString(webhookConfig.getString(productType.getWebhookKey() + ".username", plugin.getName()), playerName, product));
        webhook.setContent(replaceString(webhookConfig.getString(productType.getWebhookKey() + ".content"), playerName, product));

        ConfigurationSection embeds = webhookConfig.getConfigurationSection(productType.getWebhookKey() + ".embeds");

        if (embeds != null) {
            for (String key : embeds.getKeys(false)) {
                DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

                embed.setTitle(replaceString(embeds.getString(key + ".title", null), playerName, product));
                embed.setDescription(replaceString(embeds.getString(key + ".description", null), playerName, product));
                embed.setColor(Color.decode(embeds.getString(key + ".color", "#5865f2")));

                if (embeds.getString(key + ".thumbnail", null) != null){
                    embed.setThumbnail(embeds.getString(key + ".thumbnail"));
                }

                if (embeds.getConfigurationSection(key + ".footer") != null) {
                    embed.setFooter(
                            replaceString(embeds.getString(key + ".footer.text", null), playerName, product),
                            embeds.getString(key + ".footer.image", null)
                    );
                }

                if (embeds.getConfigurationSection(key + ".author") != null) {
                    embed.setAuthor(
                            replaceString(embeds.getString(key + ".author.name", null), playerName, product),
                            embeds.getString(key + ".author.link", null),
                            embeds.getString(key + ".author.image", null)
                    );
                }



                ConfigurationSection fields = embeds.getConfigurationSection(key + ".fields");

                if (fields != null) {
                    // "fields" here was not finding more than 1 field, idk why
                    for (String fieldsKey : embeds.getConfigurationSection(key + ".fields").getKeys(false)) {
                        embed.addField(
                                replaceString(fields.getString(fieldsKey + ".text"), playerName, product),
                                replaceString(fields.getString(fieldsKey + ".description"), playerName, product),
                                fields.getBoolean(fieldsKey + ".is-inline")
                        );
                    }
                }

                webhook.addEmbed(embed);
            }
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                webhook.execute();
            } catch (IOException e) {
                plugin.getLogger().severe(String.valueOf(e));
            }
        });

        return true;
    }





    private String replaceString(String string, String player, String product){
        if (string == null) {
            return string;
        }

        return string
                .replace("%player%", player)
                .replace("%product%", product);
    }
}
