package fr.twah2em.uhcdieu.inventories;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.inventories.UHCInventory.UHCInventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class ChoosePlayersStatusInventory {

    public Inventory inventory(Player player, Main main) {
        final List<UUID> selectionnedPlayers = main.gameManager().selectionnedPlayers();

        return new UHCInventoryBuilder("§aChoisir le status de chaque joueur", 6)
                .withItems(Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(player1 -> !main.gameManager().playingPlayers().contains(player1.getUniqueId()))
                        .filter(player1 -> !main.gameManager().spectators().contains(player1.getUniqueId()))
                        .filter(player1 -> !main.gameManager().admins().contains(player1.getUniqueId()))
                        .map(player1 -> new ItemBuilder(Material.PLAYER_HEAD)
                                .withPlayerHead(player1)
                                .withName("§a" + player1.getName())
                                .withLore(selectionnedPlayers.contains(player1.getUniqueId()) ? "§a§oJoueur sélectionné §7§o(cliquez pour déselectionner)" :
                                        "§c§oJoueur non sélectionné §7§o(cliquez pour sélectionner)")
                                .withPersistentData("uhc-dieu", "player-uuid", player.getUniqueId().toString(), PersistentDataType.STRING)
                                .build())
                        .toList())
                .withClickConsumer(event -> {
                    event.setCancelled(true);
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

                        currentItem.setLore(List.of(selectionnedPlayers.contains(targetPlayer.getUniqueId()) ? "§a§oJoueur sélectionné §7§o(Cliquez pour déselectionner)" :
                                "§c§oJoueur non sélectionné §7§o(Cliquez pour sélectionner)"));
                    }

                    if (currentItem.getType() == Material.EMERALD) {
                        final List<UUID> playingPlayers = main.gameManager().playingPlayers();
                        playingPlayers.addAll(selectionnedPlayers);

                        selectionnedPlayers
                                .stream()
                                .map(Bukkit::getPlayer)
                                .filter(Objects::nonNull)
                                .forEach(player1 -> player1.sendMessage(Main.PREFIX + "§aVous avez été sélectionné pour jouer !"));

                        final List<String> players = selectionnedPlayers
                                .stream()
                                .map(Bukkit::getPlayer)
                                .filter(Objects::nonNull)
                                .map(player1 -> "§e" + player1.getName())
                                .toList();
                        currentItem.setLore(Stream.concat(Stream.of("§aVous avez sélectionné :"), players.stream()).toList());

                        selectionnedPlayers.clear();
                    }
                })
                .buildToBukkitInventory();
    }
}