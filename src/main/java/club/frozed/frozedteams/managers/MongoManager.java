package club.frozed.frozedteams.managers;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.utils.configurations.ConfigFile;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;

@Getter
public class MongoManager {

    private MongoClient client;

    private MongoCollection<Document> playerData, teamData;

    public void connect() {
        ConfigFile configuration = FrozedTeams.getInstance().getConfiguration("config");

        if (configuration.getBoolean("DATABASE.MONGO.AUTHENTICATION.ENABLED")) {
            ServerAddress serverAddress = new ServerAddress(configuration.getString("DATABASE.MONGO.HOST"),
                    configuration.getInt("DATABASE.MONGO.PORT"));

            MongoCredential credential = MongoCredential.createCredential(
                    configuration.getString("DATABASE.MONGO.AUTHENTICATION.USER"), configuration.getString("DATABASE.MONGO.AUTHENTICATION.DATABASE"),
                    configuration.getString("DATABASE.MONGO.AUTHENTICATION.PASSWORD").toCharArray());

            client = new MongoClient(serverAddress, credential, MongoClientOptions.builder().build());
            this.playerData = client.getDatabase("FrozedTeams").getCollection("PlayerData");
            this.teamData = client.getDatabase("FrozedTeams").getCollection("TeamData");
        } else {
            client = new MongoClient(configuration.getString("DATABASE.MONGO.HOST"), configuration.getInt("DATABASE.MONGO.PORT"));
            this.playerData = client.getDatabase("FrozedTeams").getCollection("PlayerData");
            this.teamData = client.getDatabase("FrozedTeams").getCollection("TeamData");
        }
    }

    public void disconnect() {
        client.close();
    }
}
