package club.frozed.frozedteams.managers;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.data.PlayerData;
import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.Getter;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class PlayerDataManager {
    @Getter public static PlayerDataManager instance;
    private Map<UUID, PlayerData> playerDatas = new HashMap<>();

    public PlayerDataManager() {
        instance = this;
    }

    public PlayerData getByUUID(UUID uuid) {
        return playerDatas.getOrDefault(uuid, null);
    }

    public void handleCreateData(UUID uuid) {
        if (!playerDatas.containsKey(uuid)) {
            playerDatas.put(uuid, new PlayerData(uuid));
        }
    }

    public void saveData(PlayerData data, String info, String value, int amount) {
        if (!hasData(info)) {
            return;
        }
        Document document = FrozedTeams.getInstance().getMongoManager().getPlayerData().find(Filters.eq("info")).first();
        document.put(value, amount);
        FrozedTeams.getInstance().getMongoManager().getPlayerData().replaceOne(Filters.eq("info", info), document);
    }

    private boolean hasData(String info) {
        Document document = FrozedTeams.getInstance().getMongoManager().getPlayerData().find(Filters.eq("info", info)).first();
        return document != null;
    }
}
