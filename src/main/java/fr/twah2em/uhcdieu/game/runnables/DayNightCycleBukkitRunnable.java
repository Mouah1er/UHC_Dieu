package fr.twah2em.uhcdieu.game.runnables;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.listeners.internal.events.DayNightCycleEvent;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class DayNightCycleBukkitRunnable extends BukkitRunnable {
    private final Main main;
    private final World world;

    public DayNightCycleBukkitRunnable(Main main) {
        this.main = main;
        this.world = main.getServer().getWorlds().get(0);

        runTaskTimer(main, 0, 20);
    }

    @Override
    public void run() {
        System.out.println(world.getTime());
        if (world.getTime() >= 0 && world.getTime() <= 20) {
            System.out.println("call day");
            main.getServer().getPluginManager().callEvent(new DayNightCycleEvent(world, DayNightCycleEvent.Cycle.DAY));
        } else if (world.getTime() >= 12000 && world.getTime() <= 12020) {
            System.out.println("call night");
            main.getServer().getPluginManager().callEvent(new DayNightCycleEvent(world, DayNightCycleEvent.Cycle.NIGHT));
        }
    }
}
