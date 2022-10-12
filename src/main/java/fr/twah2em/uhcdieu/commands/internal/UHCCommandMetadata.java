package fr.twah2em.uhcdieu.commands.internal;

import fr.twah2em.uhcdieu.commands.internal.subcommands.UHCSubCommand;

import java.util.Arrays;
import java.util.List;

public class UHCCommandMetadata {
    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;
    private final List<UHCSubCommand> subCommands;
    private final String permission;

    private UHCCommandMetadata(String name, String description, String usage, List<String> aliases, List<UHCSubCommand> subCommands, String permission) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
        this.subCommands = subCommands;

        final boolean hasNoPermission = permission == null || permission.equals("") || permission.isBlank() || permission.isEmpty();
        this.permission = hasNoPermission ? null : permission;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public String usage() {
        return this.usage;
    }

    public List<String> aliases() {
        return this.aliases;
    }

    public List<UHCSubCommand> subCommands() {
        return this.subCommands;
    }

    public String permission() {
        return this.permission;
    }

    public static class UHCCommandMetadataBuilder {
        private final String name;
        private String description;
        private String usage;
        private List<String> aliases;
        private List<UHCSubCommand> subCommands;
        private String permission;

        public UHCCommandMetadataBuilder(String name) {
            this.name = name;
        }

        public UHCCommandMetadataBuilder withDescription(String description) {
            this.description = description;

            return this;
        }

        public UHCCommandMetadataBuilder withUsage(String usage) {
            this.usage = usage;

            return this;
        }

        public UHCCommandMetadataBuilder withAliases(String... aliases) {
            return withAliases(Arrays.asList(aliases));
        }

        public UHCCommandMetadataBuilder withAliases(List<String> aliases) {
            this.aliases = aliases;

            return this;
        }

        public UHCCommandMetadataBuilder withSubCommands(UHCSubCommand... subCommands) {
            return withSubCommands(Arrays.asList(subCommands));
        }

        public UHCCommandMetadataBuilder withSubCommands(List<UHCSubCommand> subCommands) {
            this.subCommands = subCommands;

            return this;
        }

        public UHCCommandMetadataBuilder withPermission(String permission) {
            this.permission = permission;

            return this;
        }

        public UHCCommandMetadata build() {
            return new UHCCommandMetadata(name, description, usage, aliases, subCommands, permission);
        }
    }
}
