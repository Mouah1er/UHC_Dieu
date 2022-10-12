package fr.twah2em.uhcdieu.commands.internal;

import fr.twah2em.uhcdieu.commands.internal.subcommands.UHCSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public abstract class UHCCommand extends Command {
    private final UHCCommandMetadata uhcCommandMetadata;

    public UHCCommand(UHCCommandMetadata uhcCommandMetadata) {
        super(uhcCommandMetadata.name(), uhcCommandMetadata.description(), uhcCommandMetadata.usage(),
                uhcCommandMetadata.aliases() == null ? Collections.emptyList() : uhcCommandMetadata.aliases());

        this.uhcCommandMetadata = uhcCommandMetadata;

        final boolean hasPermission = uhcCommandMetadata.permission() == null ||
                uhcCommandMetadata.permission().equals("") ||
                uhcCommandMetadata.permission().isEmpty();
        this.setPermission(hasPermission ? null : uhcCommandMetadata.permission());
    }

    public abstract void execute(CommandSender commandSender, String[] args);

    public abstract UHCCommandCallback shouldBeExecuted(CommandSender commandSender, String[] args);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        final boolean shouldBeExecuted = this.shouldBeExecuted(sender, args).call();

        if (shouldBeExecuted) execute(sender, args);

        return shouldBeExecuted;
    }

    protected void executeSubCommand(int index, CommandSender commandSender, String[] args) {
        final UHCSubCommand subCommand = uhcCommandMetadata.subCommands().get(index);

        if (subCommand != null) {
            final boolean shouldBeExecuted = subCommand.shouldBeExecuted(commandSender, args).call();

            if (shouldBeExecuted) subCommand.execute(commandSender, args);
        }
    }
}
