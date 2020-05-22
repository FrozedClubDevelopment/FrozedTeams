package club.frozed.frozedteams;

import club.frozed.frozedteams.commands.TrackCommand;
import club.frozed.frozedteams.listeners.MobCatchingListener;
import club.frozed.frozedteams.listeners.MobListener;
import club.frozed.frozedteams.listeners.PlayerListener;
import club.frozed.frozedteams.listeners.SalvagingListener;
import club.frozed.frozedteams.utils.chat.Color;
import club.frozed.frozedteams.utils.command.CommandFramework;
import club.frozed.frozedteams.utils.configurations.ConfigFile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
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

        if (!this.getDescription().getAuthors().contains("Elb1to") || !this.getDescription().getAuthors().contains("FrozedDevelopment") ||
            !this.getDescription().getDescription().equals("MineHQ MCTeams replica by Elb1to") || !this.getDescription().getName().equals("FrozedTeams")) {
            System.exit(0);
            Bukkit.shutdown();
        }

        // Register Listeners
        Bukkit.getPluginManager().registerEvents(new MobCatchingListener(), this);
        Bukkit.getPluginManager().registerEvents(new SalvagingListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MobListener(), this);

        // Register Managers

        // Register Commands
        this.getCommand("track").setExecutor(new TrackCommand());

        Bukkit.getConsoleSender().sendMessage(Color.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7This server is using &bFrozedTeams"));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7Authors&8: &b" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7Version&8: &b" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(Color.translate("&bChecking your spigot version..."));
        Bukkit.getConsoleSender().sendMessage(Color.translate("&aSuccess! &bYour Server NMS version: " + getNmsVersion()));
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(Color.translate("&7&m--------------------------------------------------------------"));
    }

    private String getNmsVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public ConfigFile getConfiguration(String name) {
        return files.stream().filter(config -> config.getName().equals(name)).findFirst().orElse(null);
    }

    public void registerConfigurations() {
        files.addAll(Arrays.asList(new ConfigFile("config"), new ConfigFile("messages"), new ConfigFile("design")));
    }
}
