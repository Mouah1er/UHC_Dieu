package fr.twah2em.uhcdieu.commands;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.commands.internal.UHCCommand;
import fr.twah2em.uhcdieu.commands.internal.UHCCommandCallback;
import fr.twah2em.uhcdieu.commands.internal.UHCCommandMetadata;
import fr.twah2em.uhcdieu.game.GameState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class StartCommand extends UHCCommand {
    private final Main main;

    public StartCommand(Main main) {
        super(new UHCCommandMetadata.UHCCommandMetadataBuilder("start")
                .withDescription("Commencer la partie")
                .withUsage("start")
                .withPermission("uhc.start")
                .build());

        this.main = main;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        final Player player = (Player) commandSender;

        player.sendMessage(Main.PREFIX + "§eÊtes-vous sûr? Tapez §aOui §eou §cNon §edans le chat.");

        final CompletableFuture<Boolean> startCommandConfirmation = new CompletableFuture<>();

        main.gameManager().startCommandConfirmation().put(player.getUniqueId(), startCommandConfirmation);

        startCommandConfirmation.thenAccept(acceptation -> {
            if (acceptation) {
                main.gameManager().choosePlayersStatus(player);
            } else {
                player.sendMessage(Main.PREFIX + "§cVous avez bien annulé le lancement de la partie.");
            }
        });
    }


    @Override
    public UHCCommandCallback shouldBeExecuted(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) return UHCCommandCallback.notAPlayerCallback(commandSender);

        if (!commandSender.hasPermission("uhc.start")) return UHCCommandCallback.permissionCallback(commandSender);

        if (GameState.gameState() != GameState.WAITING) return () -> {
            commandSender.sendMessage(Main.PREFIX + "§cLa partie est déjà en cours.");
            return false;
        };

        return UHCCommandCallback.emptyTrueCallback();
    }
}
