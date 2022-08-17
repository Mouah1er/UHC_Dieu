package fr.twah2em.uhcdieu.commands.internal;

import fr.twah2em.uhcdieu.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface UHCCommandCallback {
    boolean call();

    static UHCCommandCallback emptyFalseCallback() {
        return () -> false;
    }

    static UHCCommandCallback emptyTrueCallback() {
        return () -> true;
    }

    static UHCCommandCallback notAPlayerCallback(CommandSender sender) {
        return () -> {
            sender.sendMessage(Main.PREFIX + "§cVous devez être un joueur pour utiliser cette commande");

            return false;
        };
    }

    static UHCCommandCallback permissionCallback(CommandSender sender) {
        return () -> {
            sender.sendMessage(Main.PREFIX + "§cVous n'avez pas la permission pour faire cela !");

            return false;
        };
    }
}
