package club.frozed.frozedteams.utils.command;

import club.frozed.frozedteams.FrozedTeams;

public abstract class BaseCommand {

    public FrozedTeams main = FrozedTeams.getInstance();

    public BaseCommand() {
        main.getFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
