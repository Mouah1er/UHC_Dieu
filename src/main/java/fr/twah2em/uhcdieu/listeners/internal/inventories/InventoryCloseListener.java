package fr.twah2em.uhcdieu.listeners.internal.inventories;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.inventories.UHCInventory;
import fr.twah2em.uhcdieu.listeners.internal.UHCListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCloseListener implements UHCListener<InventoryCloseEvent> {
    private final Main main;

    public InventoryCloseListener(Main main) {
        this.main = main;
    }

    @Override
    @EventHandler
    public void onEvent(InventoryCloseEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof final UHCInventory inventory) {
            if (inventory.cancelCloseEvent()) {
                Bukkit.getScheduler().runTaskLater(main, () -> event.getPlayer().openInventory(inventory.getInventory()), 1L);
            }

            inventory.closeConsumer().accept(event);
        }
    }
}