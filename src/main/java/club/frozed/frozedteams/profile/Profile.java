package club.frozed.frozedteams.profile;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.profile.warp.PlayerWarp;
import club.frozed.frozedteams.utils.document.DocumentSerializer;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;

@Getter
public class Profile implements DocumentSerializer {

    private static List<Profile> profiles = Lists.newArrayList();

    private UUID uuid;
    private List<PlayerWarp> warps;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.warps = Lists.newArrayList();

        load();

        profiles.add(this);
    }

    private void load() {
        Document document = (Document) FrozedTeams.getInstance().getMongoManager().getCollection("profiles").find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            if (document.containsKey("playerWarps")) {
                JsonObject tmp = FrozedTeams.getInstance().getGson().fromJson(document.toJson(), JsonObject.class);

                tmp.getAsJsonArray("playerWarps").forEach(p -> warps.add(FrozedTeams.getInstance().getGson().fromJson(p.toString(), PlayerWarp.class)));
            }
        }
    }

    public void save() {
        FrozedTeams.getInstance().getMongoManager().getCollection("profiles").replaceOne(Filters.eq("uuid", uuid.toString()), serialize(), new UpdateOptions().upsert(true));

        profiles.remove(this);
    }

    @Override
    public Document serialize() {
        Document document = new Document();

        document.put("uuid", uuid.toString());

        List<Document> tmp = Lists.newArrayList();

        warps.forEach(pk -> tmp.add(pk.serialize()));

        document.put("playerWarps", tmp);

        return document;
    }

    public PlayerWarp getWarpByName(String name) {
        return warps.stream().filter(playerWarp -> playerWarp.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Profile get(OfflinePlayer player) {
        return profiles.stream().filter(profile -> profile.getUuid().equals(player.getUniqueId())).findFirst().orElse(new Profile(player.getUniqueId()));
    }
}
