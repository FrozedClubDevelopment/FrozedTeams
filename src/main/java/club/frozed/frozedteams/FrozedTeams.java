package club.frozed.frozedteams;

import club.frozed.frozedteams.utils.chat.Color;
import club.frozed.frozedteams.utils.command.CommandFramework;
import club.frozed.frozedteams.utils.configurations.ConfigFile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class FrozedTeams extends JavaPlugin {

    @Getter
    public static FrozedTeams instance;
    private CommandFramework framework;
    private List<ConfigFile> files = new ArrayList<>();
    private boolean pluginLoading;

    @Override
    public void onEnable() {
        instance = this;
        pluginLoading = true;

        // Register config files
        registerConfigurations();

        if (!this.getDescription().getAuthors().contains("FrozedDevelopment") ||
                !this.getDescription().getAuthors().contains("Elb1to") ||
                !this.getDescription().getDescription().equals("MineHQ MCTeams replica by Elb1to") ||
                !this.getDescription().getName().equals("FrozedTeams")) {
            int i;
            for (i = 0; i < 10; i++) {
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "Why are you changing the");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "plugin yml ( ͡° ͜ʖ ͡°)╭∩╮");
            }
            System.exit(0);
            Bukkit.shutdown();
        }

        // Register Listeners

        // Register Managers

        // Register Commands

        Bukkit.getConsoleSender().sendMessage(Color.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7This server is using &bFrozedSG"));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7Authors&8: &b" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7Version&8: &b" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(Color.translate("&eChecking your spigot version..."));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&aSuccess! &eYour Server NMS version: " + getNmsVersion()));

    }

    private String getNmsVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public ConfigFile getConfiguration(String name) {
        return files.stream().filter(config -> config.getName().equals(name)).findFirst().orElse(null);
    }

    public void registerConfigurations() {
        files.addAll(Arrays.asList(
                new ConfigFile("config"),
                new ConfigFile("messages"),
                new ConfigFile("items"),
                new ConfigFile("inventory"),
                new ConfigFile("chests"),
                new ConfigFile("scoreboard"),
                new ConfigFile("tablist")
        ));
    }

}
