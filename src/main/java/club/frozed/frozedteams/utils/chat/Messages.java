package club.frozed.frozedteams.utils.chat;

import club.frozed.frozedteams.FrozedTeams;

public class Messages {

    public static String CATCH_SUCCEEDED;
    public static String CATCH_FAILED;

    static {
        CATCH_SUCCEEDED = Color.translate(FrozedTeams.getInstance().getConfiguration("messages.yml").getString(""));
        CATCH_FAILED = Color.translate(FrozedTeams.getInstance().getConfiguration("messages.yml").getString(""));
    }
}
