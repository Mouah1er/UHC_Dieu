package fr.twah2em.uhcdieu.commands.internal.subcommands;

import fr.twah2em.uhcdieu.commands.internal.UHCCommandCallback;
import org.bukkit.command.CommandSender;

public abstract class UHCSubCommand {
    private final String name;

    public UHCSubCommand(String name) {
        this.name = name;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public abstract UHCCommandCallback shouldBeExecuted(CommandSender sender, String[] args);

    public String getName() {
        return name;
    }
}
