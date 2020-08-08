package club.frozed.frozedteams.profile.warp;

import club.frozed.frozedteams.utils.document.DocumentSerializer;
import club.frozed.frozedteams.utils.location.LocationUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Location;

@AllArgsConstructor
public class PlayerWarp implements DocumentSerializer {

    @Getter
    private String name;
    private String location;

    public Location getWarpLocation() {
        return LocationUtils.fromString(location);
    }

    @Override
    public Document serialize() {
        Document document = new Document();
        document.put("name", name);
        document.put("location", name);
        return document;
    }
}
