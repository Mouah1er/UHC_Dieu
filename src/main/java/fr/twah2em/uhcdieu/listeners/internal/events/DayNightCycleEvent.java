package fr.twah2em.uhcdieu.listeners.internal.events;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;
import org.jetbrains.annotations.NotNull;

public class DayNightCycleEvent extends WorldEvent {
    private static final HandlerList handlers = new HandlerList();

    private final Cycle cycle;

    public DayNightCycleEvent(@NotNull World world, Cycle cycle) {
        super(world);

        this.cycle = cycle;
    }

    public Cycle cycle() {
        return cycle;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum Cycle {
        DAY,
        NIGHT
    }
}
