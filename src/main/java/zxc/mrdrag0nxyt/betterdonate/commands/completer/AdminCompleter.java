package zxc.mrdrag0nxyt.betterdonate.commands.completer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        switch (strings.length){
            case 1:
                return Arrays.asList(
                        "help",
                        "give",
                        "reload"
                );

            case 3:
                return Arrays.asList(
                        "donate",
                        "money",
                        "tokens",
                        "command"
                );

            default:
                return null;
        }
    }
}
