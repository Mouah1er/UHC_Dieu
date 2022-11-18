package fr.twah2em.uhcdieu.game;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.runnables.StartGameBukkitRunnable;
import fr.twah2em.uhcdieu.game.utils.TeleportationUtils;
import fr.twah2em.uhcdieu.inventories.ChoosePlayingPlayersInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class GameManager {
    private final Main main;

    private final EpisodesManager episodesManager;

    private final Map<UUID, CompletableFuture<Boolean>> startCommandConfirmation;

    private final List<UUID> alivePlayers;
    private final List<UUID> deadPlayers;
    private final List<UUID> votedPlayers;

    private final List<UUID> spectators;
    private final List<UUID> playingPlayers;
    private final List<UUID> admins;
    private final List<UUID> selectedPlayers;

    public GameManager(Main main) {
        this.main = main;

        this.episodesManager = new EpisodesManager();

        this.startCommandConfirmation = new HashMap<>();

        this.alivePlayers = new ArrayList<>();
        this.deadPlayers = new ArrayList<>();
        this.votedPlayers = new ArrayList<>();

        this.spectators = new ArrayList<>();
        this.playingPlayers = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.selectedPlayers = new ArrayList<>();
    }

    public void startStartingRunnable() {
        GameState.gameState(GameState.STARTING);

        new StartGameBukkitRunnable(main).runTaskTimer(main, 0L, 20L);
    }

    public void startGame() {
        GameState.gameState(GameState.EPISODES);

        Bukkit.broadcastMessage(Main.PREFIX + "§a§lLa partie commence ! L'annonce des rôles se fera dans 20 minutes, bonne chance !");

        this.playingPlayers
                .stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(TeleportationUtils::safeRandomlyTeleportPlayer);
    }

    public void choosePlayersStatus(Player starter) {
        final Inventory inventory = new ChoosePlayingPlayersInventory(starter, main).inventory();

        starter.closeInventory();
        starter.openInventory(inventory);
    }

    public EpisodesManager episodesManager() {
        return episodesManager;
    }

    public Map<UUID, CompletableFuture<Boolean>> startCommandConfirmation() {
        return startCommandConfirmation;
    }

    public List<UUID> alivePlayers() {
        return alivePlayers;
    }

    public List<UUID> deadPlayers() {
        return deadPlayers;
    }

    public List<UUID> votedPlayers() {
        return votedPlayers;
    }

    public List<UUID> spectators() {
        return spectators;
    }

    public List<UUID> playingPlayers() {
        return playingPlayers;
    }

    public List<UUID> admins() {
        return admins;
    }

    public List<UUID> selectedPlayers() {
        return selectedPlayers;
    }
}
