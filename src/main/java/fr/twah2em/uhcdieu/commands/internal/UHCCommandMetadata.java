package fr.twah2em.uhcdieu.commands.internal;

import fr.twah2em.uhcdieu.commands.internal.subcommands.UHCSubCommand;

import java.util.List;

public record UHCCommandMetadata(String name, String description, String usage, List<String> aliases, List<UHCSubCommand> subCommands, String permission) {
}
