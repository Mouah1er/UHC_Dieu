package fr.twah2em.uhcdieu.listeners.internal.inventories;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.inventories.UHCInventory;
import fr.twah2em.uhcdieu.listeners.internal.UHCListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryOpenListener implements UHCListener<InventoryOpenEvent> {

    public InventoryOpenListener(Main main) {}

    @Override
    @EventHandler
    public void onEvent(InventoryOpenEvent event) {
        final InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof final UHCInventory inventory) {
            inventory.openConsumer().accept(event);
        }
    }
}