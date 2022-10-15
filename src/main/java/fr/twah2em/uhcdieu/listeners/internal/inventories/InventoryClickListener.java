package fr.twah2em.uhcdieu.listeners.internal.inventories;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.inventories.UHCInventory;
import fr.twah2em.uhcdieu.listeners.internal.UHCListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener implements UHCListener<InventoryClickEvent> {
    public InventoryClickListener(Main main) {}

    @Override
    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof final UHCInventory inventory) {
            inventory.clickConsumer().accept(event);
        }
    }
}