package club.frozed.frozedteams;

import club.frozed.frozedteams.commands.PlayerCommands;
import club.frozed.frozedteams.listeners.MobCatchingListener;
import club.frozed.frozedteams.listeners.MobListener;
import club.frozed.frozedteams.listeners.PlayerListener;
import club.frozed.frozedteams.listeners.SalvagingListener;
import club.frozed.frozedteams.managers.MongoManager;
import club.frozed.frozedteams.utils.chat.ColorText;
import club.frozed.frozedteams.utils.command.CommandFramework;
import club.frozed.frozedteams.utils.configurations.ConfigFile;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class FrozedTeams extends JavaPlugin {

    @Getter
    private static FrozedTeams instance;
    private CommandFramework framework;
    private static Economy economy = null;
    private List<ConfigFile> files = new ArrayList<>();
    private boolean pluginLoading;

    private Gson gson = new Gson();

    //MANAGERS
    private MongoManager mongoManager;

    @Override
    public void onEnable() {
        instance = this;
        pluginLoading = true;

        // Register config files
        registerConfigurations();

        if (!this.getDescription().getAuthors().contains("Elb1to") || !this.getDescription().getAuthors().contains("FrozedDevelopment") ||
                !this.getDescription().getDescription().equals("MineHQ MCTeams replica by Elb1to and Tincho.") || !this.getDescription().getName().equals("FrozedTeams")) {
            Bukkit.getPluginManager().disablePlugins();
            for (int i = 0; i < 10000; i++) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cWhy are you changing the plugin.yml ( ͡° ͜ʖ ͡°)╭∩╮"));
            }
        }

        // Register Listeners
        Bukkit.getPluginManager().registerEvents(new MobCatchingListener(), this);
        Bukkit.getPluginManager().registerEvents(new SalvagingListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MobListener(), this);

        // Register Managers

        registerManagers();

        // Register Commands
        CommandFramework framework = new CommandFramework(this);
        framework.registerCommands(new PlayerCommands());

        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&7This server is using &bFrozedTeams"));
        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&7Authors&8: &b" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&7Version&8: &b" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&bChecking your spigot version..."));
        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&aSuccess! &bYour Server NMS version: " + getNmsVersion()));
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(ColorText.translate("&7&m--------------------------------------------------------------"));
    }

    private void registerManagers() {
        (this.mongoManager = new MongoManager()).connect();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> economyRegisteredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyRegisteredServiceProvider == null) {
            return false;
        }
        economy = economyRegisteredServiceProvider.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
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
