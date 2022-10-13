package fr.twah2em.uhcdieu;

import fr.twah2em.uhcdieu.commands.StartCommand;
import fr.twah2em.uhcdieu.commands.internal.UHCCommandRegistration;
import fr.twah2em.uhcdieu.game.GameManager;
import fr.twah2em.uhcdieu.game.GameState;
import fr.twah2em.uhcdieu.listeners.AsyncPlayerChatListener;
import fr.twah2em.uhcdieu.listeners.internal.UHCListenerRegistration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static final String PREFIX = "§c[§6UHC-Dieu§c] §r";

    private GameManager gameManager;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);

        final UHCCommandRegistration uhcCommandRegistration = new UHCCommandRegistration(this);
        final UHCListenerRegistration uhcListenerRegistration = new UHCListenerRegistration(this);

        uhcCommandRegistration.registerCommands(
                StartCommand::new
        );

        uhcListenerRegistration.registerListeners(
                AsyncPlayerChatListener::new
        );

        GameState.gameState(GameState.WAITING);
    }

    public GameManager gameManager() {
        return gameManager;
    }
}
