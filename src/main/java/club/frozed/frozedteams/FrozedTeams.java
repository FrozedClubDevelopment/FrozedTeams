package club.frozed.frozedteams;

import club.frozed.frozedteams.commands.PlayerCommands;
import club.frozed.frozedteams.commands.TeamCommand;
import club.frozed.frozedteams.listeners.MobCatchingListener;
import club.frozed.frozedteams.listeners.MobListener;
import club.frozed.frozedteams.listeners.PlayerListener;
import club.frozed.frozedteams.listeners.SalvagingListener;
import club.frozed.frozedteams.managers.MongoManager;
import club.frozed.frozedteams.managers.team.TeamManager;
import club.frozed.frozedteams.utils.chat.CC;
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
import org.bukkit.scheduler.BukkitRunnable;

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
    private TeamManager teamManager;

    private Gson gson = new Gson();

    private MongoManager mongoManager;

    /*
     * TODO List:
     *  - Make Teams System
     *  - Make Economy System using Vault
     *  - Make Shop for players to be able to buy resources
     *
     *  NOTE: All data has to be saved on Mongo
     *
     *      + Useful Links:
     *          - https://minehq.fandom.com/wiki/MCTeams
     *          - https://minehq.fandom.com/wiki/Economy
     *          - https://minehq.fandom.com/wiki/Team
     */

    @Override
    public void onEnable() {
        instance = this;
        pluginLoading = true;

        // Register config files
        registerConfigurations();

        if (!this.getDescription().getAuthors().contains("Elb1to") || !this.getDescription().getAuthors().contains("FrozedClubDevelopment") || !this.getDescription().getDescription().equals("MineHQ MCTeams replica by Elb1to.") || !this.getDescription().getName().equals("FrozedTeams")) {
            Bukkit.getPluginManager().disablePlugin(this);
            for (int i = 0; i < 25; i++) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cWhy are you changing the plugin.yml ( ͡° ͜ʖ ͡°)╭∩╮"));
            }
        }

        /*
        Team Manager
         */
        teamManager = new TeamManager();

        // Register Listeners
        Bukkit.getPluginManager().registerEvents(new MobCatchingListener(), this);
        Bukkit.getPluginManager().registerEvents(new SalvagingListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MobListener(), this);

        // Register Vault
        if (!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to register economy sistem disabling plugin."));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Register Managers
        registerManagers();

        // Register Commands
        CommandFramework framework = new CommandFramework(this);
        framework.registerCommands(new PlayerCommands());
        framework.registerCommands(new TeamCommand());

        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7This server is using &bFrozedTeams"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7Authors&8: &b" + getDescription().getAuthors()));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7Version&8: &b" + getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m--------------------------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&bChecking your spigot version..."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&aSuccess! &bYour Server NMS version: " + getNmsVersion()));
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m--------------------------------------------------------------"));
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
