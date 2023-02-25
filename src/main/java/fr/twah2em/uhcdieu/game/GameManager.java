package fr.twah2em.uhcdieu.game;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.roles.Roles;
import fr.twah2em.uhcdieu.game.runnables.DayNightCycleBukkitRunnable;
import fr.twah2em.uhcdieu.game.runnables.StartGameBukkitRunnable;
import fr.twah2em.uhcdieu.game.utils.TeleportationUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

    public GameManager(Main main) {
        this.main = main;

        this.episodesManager = new EpisodesManager(main);

        this.startCommandConfirmation = new HashMap<>();

        this.alivePlayers = new ArrayList<>();
        this.deadPlayers = new ArrayList<>();
        this.votedPlayers = new ArrayList<>();

        this.spectators = new ArrayList<>();
        this.playingPlayers = new ArrayList<>();
    }

    private void startDayNightCycle() {
        new DayNightCycleBukkitRunnable(main);
    }

    public void startStartingRunnable() {
        GameState.gameState(GameState.STARTING);

        new StartGameBukkitRunnable(main);
    }

    public void startGame() {
        GameState.gameState(GameState.EPISODES);
        this.playingPlayers.addAll(Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getUniqueId)
                .toList());

        Bukkit.broadcastMessage(Main.PREFIX + "§a§lLa partie commence ! L'annonce des rôles se fera dans 20 minutes, bonne chance !");

        this.playingPlayers
                .stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(TeleportationUtils::safeRandomlyTeleportPlayer);

        startDayNightCycle();

        this.episodesManager.episode(1);
        this.episodesManager.startEpisodesRunnable();
    }

    public void giveRoleToPlayers() {
        Roles.randomRoles();
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
}
