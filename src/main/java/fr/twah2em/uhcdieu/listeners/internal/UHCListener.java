package fr.twah2em.uhcdieu.listeners.internal;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public interface UHCListener<E extends Event> extends Listener {
    @EventHandler
    void onEvent(E event);
}
