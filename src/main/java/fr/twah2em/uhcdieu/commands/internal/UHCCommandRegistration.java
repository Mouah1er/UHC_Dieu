package fr.twah2em.uhcdieu.commands.internal;

import fr.twah2em.uhcdieu.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.util.function.Function;

public class UHCCommandRegistration {
    private final Main main;

    public UHCCommandRegistration(Main main) {
        this.main = main;
    }

    @SafeVarargs
    public final void registerCommands(Function<Main, ? extends UHCCommand>... commands) {
        final CommandMap bukkitCommandMap = Bukkit.getCommandMap();

        for (Function<Main, ? extends UHCCommand> command : commands) {
            final UHCCommand uhcCommand = command.apply(main);

            bukkitCommandMap.register(uhcCommand.getName(), uhcCommand);
        }
    }
}
