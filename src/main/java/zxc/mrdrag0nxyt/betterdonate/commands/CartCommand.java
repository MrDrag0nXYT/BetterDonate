package zxc.mrdrag0nxyt.betterdonate.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;
import zxc.mrdrag0nxyt.betterdonate.util.ColorUtil;
import zxc.mrdrag0nxyt.betterdonate.util.config.CartConfig;
import zxc.mrdrag0nxyt.betterdonate.util.config.Config;
import zxc.mrdrag0nxyt.betterdonate.util.config.LanguageConfig;
import zxc.mrdrag0nxyt.betterdonate.util.product.ProductType;

import java.util.List;

public class CartCommand implements CommandExecutor {

    private final BetterDonate plugin;

    private final CartConfig cartConfigFile;

    private final FileConfiguration config;
    private final FileConfiguration languageConfig;
    private final FileConfiguration cartConfig;

    public CartCommand(BetterDonate plugin, Config config, LanguageConfig languageConfig, CartConfig cartConfig) {
        this.plugin = plugin;
        this.config = config.getConfig();
        this.languageConfig = languageConfig.getLanguageConfig();

        this.cartConfigFile = cartConfig;
        this.cartConfig = cartConfigFile.getCartConfig();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender.hasPermission("betterdonate.cart")) {

            if (strings.length == 0) {
                return getProducts(commandSender);

            }

            if (strings[0].equalsIgnoreCase("help")) {
                List<String> helpMessageList = languageConfig.getStringList("cart.help");

                for (String helpMessage : helpMessageList) {
                    commandSender.sendMessage(ColorUtil.setColor(helpMessage));
                }
            } else if (strings[0].equalsIgnoreCase("get")) {
                return getProducts(commandSender);

            } else {
                return getProducts(commandSender);

            }

            return true;
        } else {
            commandSender.sendMessage(ColorUtil.setColor(
                    config.getString("prefix") +
                            languageConfig.getString("no-permission")
            ));
            return false;
        }
    }



    private boolean getProducts(CommandSender commandSender) {

        ConfigurationSection section = cartConfig.getConfigurationSection("players." + commandSender.getName() + ".products");

        if (section == null) {
            commandSender.sendMessage(ColorUtil.setColor(
                    config.getString("prefix") +
                            languageConfig.getString("cart.empty")
            ));
            return false;
        }


        List<String> listSuccessMessages = languageConfig.getStringList("cart.success");

        for (String successMessage : listSuccessMessages) {
            commandSender.sendMessage(ColorUtil.setColor(successMessage));
        }

        for (String cmd : config.getStringList("commands.on-get")) {
            cmd = cmd
                    .replace("%player%", commandSender.getName()
                    );

            runCommand(cmd);
        }

        for (ProductType type : ProductType.values()) {
            if (type != ProductType.COMMAND) {
                if (cartConfig.getString("players." + commandSender.getName() + "." + type.getCartKey()) != null) {
                    List<String> commandsList = config.getStringList("commands." + type.getName());

                    giveProductCommand(type, commandSender, commandsList);
                }
            } else {
                if (cartConfig.getString("players." + commandSender.getName() + "." + type.getCartKey()) != null) {
                    List<String> commandsList = cartConfig.getStringList("players." + commandSender.getName() + ".products.commands");

                    giveProductCommand(type, commandSender, commandsList);
                }
            }
        }

        cartConfig.set("players." + commandSender.getName(), null);
        cartConfigFile.saveCartConfig();
        return true;
    }



    private void giveProductCommand(ProductType type, CommandSender commandSender, List<String> commandsList){
        for (String cmd : commandsList) {
            plugin.getLogger().info(cmd);

            cmd = cmd
                    .replace("%player%", commandSender.getName()
                    );

            cmd = cmd
                    .replace("%product%", cartConfig.getString("players." + commandSender.getName() + "." + type.getCartKey())
                    );

            runCommand(cmd);
        }
    }



    private void runCommand(String cmd) {
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
}
