package zxc.mrdrag0nxyt.betterdonate.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import zxc.mrdrag0nxyt.betterdonate.BetterDonate;
import zxc.mrdrag0nxyt.betterdonate.util.ColorUtil;
import zxc.mrdrag0nxyt.betterdonate.util.config.CartConfig;
import zxc.mrdrag0nxyt.betterdonate.util.config.Config;
import zxc.mrdrag0nxyt.betterdonate.util.config.LanguageConfig;

import java.util.List;

public class AdminCommand implements CommandExecutor {

    private final BetterDonate plugin;

    private final FileConfiguration config;
    private final FileConfiguration languageConfig;
    private final FileConfiguration cartConfig;

    private final CartConfig cartConfigFile;


    public AdminCommand(BetterDonate plugin, Config config, LanguageConfig languageConfig, CartConfig cartConfig){
        this.plugin = plugin;
        this.config = config.getConfig();
        this.languageConfig = languageConfig.getLanguageConfig();

        this.cartConfigFile = cartConfig;
        this.cartConfig = cartConfigFile.getCartConfig();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender.hasPermission("betterdonate.admin")){

            if (strings.length == 0) {
                List<String> helpMessageList = languageConfig.getStringList("admin.help");

                for (String helpMessage : helpMessageList) {
                    commandSender.sendMessage(ColorUtil.setColor(helpMessage));
                }
                return false;
            }

            if (strings[0].equalsIgnoreCase("reload")){

                plugin.reloadConfigs();
                commandSender.sendMessage(ColorUtil.setColor(
                        config.getString("prefix") +
                        languageConfig.getString("reload")
                ));

                return true;
                
            } else if (strings[0].equalsIgnoreCase("give")) {

                if (strings.length < 2){
                    commandSender.sendMessage(ColorUtil.setColor(
                            config.getString("prefix") +
                                    languageConfig.getString("admin.give.usage")
                    ));
                    return false;
                }

                boolean isAdded = addToCart(strings);

                if (isAdded){

                    String productName = "true";
                    try {
                        productName = strings[3];
                    } catch (ArrayIndexOutOfBoundsException ignore) {}

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

            } else {
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



    private boolean addToCart(String[] strings){

        switch (strings[2].toLowerCase()){
            case "donate":
                if (config.getBoolean("features.donate")){
                    cartConfig.set("players." + strings[1] + ".products.donate", strings[3]);
                } else {
                    return false;
                }
                break;

            case "money":
                if (config.getBoolean("features.money")){
                    cartConfig.set("players." + strings[1] + ".products.money", strings[3]);
                } else {
                    return false;
                }
                break;

            case "tokens":
                if (config.getBoolean("features.tokens")){
                    cartConfig.set("players." + strings[1] + ".products.tokens", strings[3]);
                } else {
                    return false;
                }
                break;

            case "command":
                if (config.getBoolean("features.commands")){

                    String cmd = "";

                    for (int i = 3; i < strings.length; i++) {
                        cmd += strings[i] + " ";
                    }

                    List<String> commandList = cartConfig.getStringList("players." + strings[1] + ".products.commands");
                    commandList.add(cmd.trim());

                    cartConfig.set("players." + strings[1] + ".products.commands", commandList);
                } else {
                    return false;
                }
                break;
            default:
                return false;
        }

        cartConfigFile.saveCartConfig();
        return true;
    }
}
