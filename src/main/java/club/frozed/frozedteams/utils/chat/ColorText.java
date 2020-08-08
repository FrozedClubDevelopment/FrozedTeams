package club.frozed.frozedteams.utils.chat;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorText {

    public static String translate(String text) {
        String output = text;
        return ChatColor.translateAlternateColorCodes('&', output);
    }

    public static List<String> translate(List<String> list) {
        return list.stream().map(ColorText::translate).collect(Collectors.toList());
    }
}
