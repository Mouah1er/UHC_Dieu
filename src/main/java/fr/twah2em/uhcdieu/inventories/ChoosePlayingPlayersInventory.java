package fr.twah2em.uhcdieu.inventories;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.inventories.UHCInventory.UHCInventoryBuilder;
import fr.twah2em.uhcdieu.utils.misc.Pair;
import fr.twah2em.uhcdieu.utils.streams.StreamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class ChoosePlayingPlayersInventory {
    private final Player player;
    private final Main main;
    private final List<UUID> selectedPlayers;

    public ChoosePlayingPlayersInventory(Player player, Main main) {
        this.player = player;
        this.main = main;
        this.selectedPlayers = main.gameManager().selectedPlayers();
    }

    public Inventory inventory() {
        return new UHCInventoryBuilder("§aChoisir les joueurs qui joueront", 6)
                .withItemsInSlots(itemsInSlots())
                .withClickConsumer(clickConsumer())
                .cancelCloseEvent(() -> main.gameManager().playingPlayers().isEmpty())
                .buildToBukkitInventory();
    }

    private List<Pair<Integer, ItemStack>> itemsInSlots() {
        final List<ItemStack> items = Bukkit.getOnlinePlayers()
                .stream()
                .filter(player1 -> !main.gameManager().playingPlayers().contains(player1.getUniqueId()))
                .filter(player1 -> !main.gameManager().spectators().contains(player1.getUniqueId()))
                .filter(player1 -> !main.gameManager().admins().contains(player1.getUniqueId()))
                .map(player1 -> new ItemBuilder(Material.PLAYER_HEAD)
                        .withPlayerHead(player1)
                        .withName("§a" + player1.getName())
                        .withLore(isPlayerSelected(player1) ? "§a§oJoueur sélectionné §7§o(cliquez pour déselectionner)" :
                                "§c§oJoueur non sélectionné §7§o(cliquez pour sélectionner)")
                        .withPersistentData("uhc-dieu", "player-uuid", player1.getUniqueId().toString(), PersistentDataType.STRING)
                        .build())
                .toList();

        final ItemStack playingItem = new ItemBuilder(Material.EMERALD)
                .withName(selectedPlayers.isEmpty() ? "§cAucun joueur sélectionné" : "§a" + selectedPlayers.size() + " joueur(s) sélectionné(s):")
                .withLore(selectedPlayers.isEmpty() ? Collections.emptyList() : StreamUtils.concatPlayersNameWithColor(selectedPlayers, ChatColor.GRAY))
                .build();

        return StreamUtils
                .appendToStream(
                        items.stream().map(itemStack -> Pair.of(items.indexOf(itemStack), itemStack)),
                        Pair.of(52, playingItem))
                .toList();
    }

    private Consumer<InventoryClickEvent> clickConsumer() {
        return (event) -> {
            event.setCancelled(true);
            final ItemStack currentItem = event.getCurrentItem();

            if (currentItem == null || currentItem.getItemMeta() == null) return;

            if (currentItem.getType() == Material.PLAYER_HEAD) {
                final UUID playerUniqueId = UUID.fromString(
                        Objects.requireNonNull(currentItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey("uhc-dieu", "player-uuid"),
                                PersistentDataType.STRING))
                );
                final Player targetPlayer = Bukkit.getPlayer(playerUniqueId);

                if (targetPlayer == null) return;

                if (!selectedPlayers.contains(targetPlayer.getUniqueId()))
                    selectedPlayers.add(targetPlayer.getUniqueId());
                else selectedPlayers.remove(targetPlayer.getUniqueId());

                currentItem.setLore(List.of(isPlayerSelected(targetPlayer) ? "§a§oJoueur sélectionné §7§o(Cliquez pour déselectionner)" :
                        "§c§oJoueur non sélectionné §7§o(Cliquez pour sélectionner)"));

                final ItemStack emerald = Objects.requireNonNull(event.getInventory().getItem(52));

                updateEmerald(emerald, player);
            }

            if (currentItem.getType() == Material.EMERALD) {
                final List<UUID> playingPlayers = main.gameManager().playingPlayers();
                playingPlayers.addAll(selectedPlayers);

                player.sendMessage("§aVous avez sélectionné les joueurs suivants: " + StreamUtils.concatPlayersNameWithColor(selectedPlayers, ChatColor.GRAY));

                selectedPlayers
                        .stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(player1 -> player1.sendMessage(Main.PREFIX + "§aVous avez été sélectionné pour jouer !"));

                selectedPlayers.clear();

                player.closeInventory();
            }
        };
    }

    private boolean isPlayerSelected(Player player) {
        return selectedPlayers.contains(player.getUniqueId());
    }

    private void updateEmerald(ItemStack emerald, Player opener) {
        emerald.setDisplayName(selectedPlayers.isEmpty() ? "§cAucun joueur sélectionné" : "§a" + selectedPlayers.size() + " joueur(s) sélectionné(s):");
        emerald.setLore(StreamUtils.concatPlayersNameWithColor(selectedPlayers, ChatColor.GRAY));
    }
}