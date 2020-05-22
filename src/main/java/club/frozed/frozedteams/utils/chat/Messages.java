package club.frozed.frozedteams.utils.chat;

import club.frozed.frozedteams.FrozedTeams;

public class Messages {

    public static String CATCH_NOT_ENOUGH_XP;
    public static String CATCH_NOT_TOO_FAR;
    public static String CATCH_SUCCEEDED;

    static {
        CATCH_SUCCEEDED = Color.translate(FrozedTeams.getInstance().getConfiguration("messages").getString("Catching.Succeeded"));
        CATCH_NOT_TOO_FAR = Color.translate(FrozedTeams.getInstance().getConfiguration("messages").getString("Catching.NotTooFar"));
        CATCH_NOT_ENOUGH_XP = Color.translate(FrozedTeams.getInstance().getConfiguration("messages").getString("Catching.NotEnoughXP"));
    }
}
