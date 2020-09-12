package club.frozed.frozedteams.managers;

import lombok.Data;
import lombok.Getter;

@Data
public class PlayerManager {
    @Getter public static PlayerManager instance;
    public PlayerManager() {
        instance = this;
    }
}
