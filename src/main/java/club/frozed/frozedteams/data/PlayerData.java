package club.frozed.frozedteams.data;

import club.frozed.frozedteams.enums.PlayerState;
import lombok.Data;
import org.bukkit.Bukkit;

import java.util.UUID;

@Data
public class PlayerData {
    private PlayerState state = PlayerState.IN_SPAWN;
    private UUID uuid;
    private String name;
    private Stat balance = new Stat();
    //private Cooldown combatCooldown;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
    }

}
