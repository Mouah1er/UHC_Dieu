package fr.twah2em.uhcdieu.game;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.runnables.StartGameBukkitRunnable;
import fr.twah2em.uhcdieu.game.utils.TeleportationUtils;
import fr.twah2em.uhcdieu.inventories.ItemBuilder;
import fr.twah2em.uhcdieu.inventories.UHCInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

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

    private final List<UUID> selectionnedPlayers;

    public GameManager(Main main) {
        this.main = main;

        this.episodesManager = new EpisodesManager();

        this.startCommandConfirmation = new HashMap<>();

        this.alivePlayers = new ArrayList<>();
        this.deadPlayers = new ArrayList<>();
        this.votedPlayers = new ArrayList<>();
        this.spectators = new ArrayList<>();
        this.playingPlayers = new ArrayList<>();

        this.selectionnedPlayers = new ArrayList<>();
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
        System.out.println("choosePlayersStatus");
        final Inventory inventory = new UHCInventory.UHCInventoryBuilder("§aChoisir le status de chaque joueur", 5)
                .withItems(Bukkit.getOnlinePlayers()
                        .stream()
                        .map(player -> new ItemBuilder(Material.PLAYER_HEAD)
                                .withPlayerHead(player)
                                .withName("§a" + player.getName())
                                .withLore(selectionnedPlayers.contains(player.getUniqueId()) ? "§a§oJoueur sélectionné (§7cliquez pour déselectionner)" :
                                        "§c§oJoueur non sélectionné (§7§ocliquez pour sélectionné)")
                                .withPersistentData("uhc-dieu", "player-uuid", player.getUniqueId().toString(), PersistentDataType.STRING)
                                .build())
                        .toList())
                .withClickConsumer(event -> {
                    final ItemStack currentItem = event.getCurrentItem();

                    if (currentItem == null || currentItem.getItemMeta() == null) return;

                    if (currentItem.getType() == Material.PLAYER_HEAD) {
                        final UUID playerUniqueId = UUID.fromString(
                                Objects.requireNonNull(currentItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("uhc-dieu", "player-uuid"), PersistentDataType.STRING))
                        );
                        final Player targetPlayer = Bukkit.getPlayer(playerUniqueId);

                        if (targetPlayer == null) return;

                        if (!selectionnedPlayers.contains(targetPlayer.getUniqueId()))
                            selectionnedPlayers.add(targetPlayer.getUniqueId());
                        else selectionnedPlayers.remove(targetPlayer.getUniqueId());

                        currentItem.setLore(Arrays.asList(selectionnedPlayers.contains(targetPlayer.getUniqueId()) ? "§a§oJoueur sélectionné (§7Cliquez pour déselectionner)" :
                                "§c§oJoueur non sélectionné (§7§oCliquez pour sélectionné)"));
                    }
                })
                .buildToBukkitInventory();

        try {

            starter.openInventory(inventory);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        main.gameManager().startStartingRunnable();
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
