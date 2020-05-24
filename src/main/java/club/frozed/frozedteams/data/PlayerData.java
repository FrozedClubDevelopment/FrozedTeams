package club.frozed.frozedteams.data;

import club.frozed.frozedteams.enums.PlayerState;
import lombok.Data;

import java.util.UUID;

@Data
public class PlayerData {
    private PlayerState state = PlayerState.IN_SPAWN;
    private UUID uuid;
    private String name;
    //private Cooldown combatCooldown;

}
