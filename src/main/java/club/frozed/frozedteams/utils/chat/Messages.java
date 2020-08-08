package club.frozed.frozedteams.utils.chat;

import club.frozed.frozedteams.FrozedTeams;

public class Messages {

    public static String CATCH_NOT_ENOUGH_XP;
    public static String CATCH_NOT_TOO_FAR;
    public static String CATCH_SUCCEEDED;

    static {
        CATCH_SUCCEEDED = ColorText.translate(FrozedTeams.getInstance().getConfiguration("messages").getString("Catching.Succeeded"));
        CATCH_NOT_TOO_FAR = ColorText.translate(FrozedTeams.getInstance().getConfiguration("messages").getString("Catching.NotTooFar"));
        CATCH_NOT_ENOUGH_XP = ColorText.translate(FrozedTeams.getInstance().getConfiguration("messages").getString("Catching.NotEnoughXP"));
    }
}
