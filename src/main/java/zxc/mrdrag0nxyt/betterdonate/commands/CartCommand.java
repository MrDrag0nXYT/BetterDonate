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

import java.util.List;

public class CartCommand implements CommandExecutor {

    private final BetterDonate plugin;

    private final CartConfig cartConfigFile;

    private final FileConfiguration config;
    private final FileConfiguration languageConfig;
    private final FileConfiguration cartConfig;

    public CartCommand(BetterDonate plugin, Config config, LanguageConfig languageConfig, CartConfig cartConfig){
        this.plugin = plugin;
        this.config = config.getConfig();
        this.languageConfig = languageConfig.getLanguageConfig();

        this.cartConfigFile = cartConfig;
        this.cartConfig = cartConfigFile.getCartConfig();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender.hasPermission("betterdonate.cart")){

            if (strings.length == 0) {
                List<String> helpMessageList = languageConfig.getStringList("cart.help");

                for (String helpMessage : helpMessageList) {
                    commandSender.sendMessage(ColorUtil.setColor(helpMessage));
                }
                return false;
            }

            if (strings[0].equalsIgnoreCase("help")) {
                List<String> helpMessageList = languageConfig.getStringList("cart.help");

                for (String helpMessage : helpMessageList) {
                    commandSender.sendMessage(ColorUtil.setColor(helpMessage));
                }
            } else if (strings[0].equalsIgnoreCase("get")) {

                ConfigurationSection section = cartConfig.getConfigurationSection("players." + commandSender.getName() + ".products");

                if (section == null){
                    commandSender.sendMessage(ColorUtil.setColor(
                            config.getString("prefix") +
                                    languageConfig.getString("cart.empty")
                    ));
                    return false;
                }



                if (cartConfig.getString("players." + commandSender.getName() + ".products.donate") != null){

                    for (String cmd : config.getStringList("commands.donate")){
                        cmd = cmd
                                .replace("%player%", commandSender.getName()
                                );

                        cmd = cmd
                                .replace("%product%", cartConfig.getString("players." + commandSender.getName() + ".products.donate")
                                );

                        runCommand(cmd);
                    }
                }

                if (cartConfig.getString("players." + commandSender.getName() + ".products.money") != null){

                    for (String cmd : config.getStringList("commands.money")){
                        cmd = cmd
                                .replace("%player%", commandSender.getName()
                                );

                        cmd = cmd
                                .replace("%product%", cartConfig.getString("players." + commandSender.getName() + ".products.money")
                                );

                        runCommand(cmd);
                    }
                }

                if (cartConfig.getString("players." + commandSender.getName() + ".products.tokens") != null){

                    for (String cmd : config.getStringList("commands.tokens")){
                        cmd = cmd
                                .replace("%player%", commandSender.getName()
                                );

                        cmd = cmd
                                .replace("%product%", cartConfig.getString("players." + commandSender.getName() + ".products.tokens")
                                );

                        runCommand(cmd);
                    }
                }

                if (!cartConfig.getStringList("players." + commandSender.getName() + ".products.commands").isEmpty()){

                    List<String> commandsList = cartConfig.getStringList("players." + commandSender.getName() + ".products.commands");

                    for (String cmd : commandsList){
                        runCommand(cmd);
                    }
                }


                List<String> listSuccessMessages = languageConfig.getStringList("cart.success");

                for (String successMessage : listSuccessMessages) {
                    commandSender.sendMessage(ColorUtil.setColor(successMessage));
                }



                for (String cmd : config.getStringList("commands.on-get")){
                    cmd = cmd
                            .replace("%player%", commandSender.getName()
                            );

                    runCommand(cmd);
                }

                cartConfig.set("players." + commandSender.getName(), null);
                cartConfigFile.saveCartConfig();

            } else {
                List<String> helpMessageList = languageConfig.getStringList("cart.help");

                for (String helpMessage : helpMessageList) {
                    commandSender.sendMessage(ColorUtil.setColor(helpMessage));
                }
                return false;
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


    private void runCommand(String cmd) {
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
}
