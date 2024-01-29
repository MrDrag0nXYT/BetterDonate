package zxc.mrdrag0nxyt.betterdonate.util;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    public static String setColor(String from) {
        Pattern pattern = Pattern.compile("&#[a-fA-F\0-9]{6}");
        Matcher matcher = pattern.matcher(from);
        while (matcher.find()) {
            String hexCode = from.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("&#", "x");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch)
                builder.append("&").append(c);
            from = from.replace(hexCode, builder.toString());
            matcher = pattern.matcher(from);
        }
        return ChatColor.translateAlternateColorCodes('&', from);
    }

}
