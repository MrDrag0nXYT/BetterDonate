package zxc.mrdrag0nxyt.betterdonate.commands.completer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class CartCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length == 1) {
            return Arrays.asList(
                    "get",
                    "help"
            );
        }

        return null;
    }
}
