package fr.twah2em.uhcdieu.game.runnables;

import fr.twah2em.uhcdieu.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class StartGameBukkitRunnable extends BukkitRunnable {
    private int timer = 10;

    private final Main main;

    public StartGameBukkitRunnable(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        timer--;

        if (timer == 0) {
            cancel();


        }
    }
}
