package fr.twah2em.uhcdieu;

import fr.twah2em.uhcdieu.commands.StartCommand;
import fr.twah2em.uhcdieu.commands.internal.UHCCommandRegistration;
import fr.twah2em.uhcdieu.game.GameManager;
import fr.twah2em.uhcdieu.game.GameState;
import fr.twah2em.uhcdieu.listeners.AsyncPlayerChatListener;
import fr.twah2em.uhcdieu.listeners.internal.UHCListenerRegistration;
import fr.twah2em.uhcdieu.listeners.internal.inventories.InventoryClickListener;
import fr.twah2em.uhcdieu.listeners.internal.inventories.InventoryCloseListener;
import fr.twah2em.uhcdieu.listeners.internal.inventories.InventoryOpenListener;
import org.bukkit.Bukkit;
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
                InventoryOpenListener::new,
                InventoryClickListener::new,
                InventoryCloseListener::new,
                AsyncPlayerChatListener::new
        );

        GameState.gameState(GameState.WAITING);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("Le serveur a été stoppé, reconnectez-vous"));
    }

    public GameManager gameManager() {
        return gameManager;
    }
}
