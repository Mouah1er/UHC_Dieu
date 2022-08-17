package fr.twah2em.uhcdieu;

import fr.twah2em.uhcdieu.commands.StartCommand;
import fr.twah2em.uhcdieu.commands.internal.CommandRegistration;
import fr.twah2em.uhcdieu.game.GameManager;
import fr.twah2em.uhcdieu.game.GameState;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class Main extends JavaPlugin {
    public static final String PREFIX = "§c[§6UHC-Dieu§c] §r";

    private GameManager gameManager;
    private Map<UUID, CompletableFuture<Boolean>> startCommandConfirmation;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);
        this.startCommandConfirmation = new HashMap<>();

        final CommandRegistration commandRegistration = new CommandRegistration(this);

        commandRegistration.registerCommands(
                StartCommand::new
        );

        GameState.gameState(GameState.WAITING);
    }

    public GameManager gameManager() {
        return gameManager;
    }

    public Map<UUID, CompletableFuture<Boolean>> startCommandConfirmation() {
        return startCommandConfirmation;
    }
}
