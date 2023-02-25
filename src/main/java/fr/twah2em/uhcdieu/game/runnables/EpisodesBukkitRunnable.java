package fr.twah2em.uhcdieu.game.runnables;

import fr.twah2em.uhcdieu.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class EpisodesBukkitRunnable extends BukkitRunnable {
    private final Main main;

    private int episode;

    private int timer = 0;

    public EpisodesBukkitRunnable(Main main) {
        this.main = main;

        this.episode = main.gameManager().episodesManager().episode();

        runTaskTimer(main, 0L, 20L);
    }

    @Override
    public void run() {
        timer++;

        if (episode != 0) {
            //if (timer >= 1200) {
            if (timer >= 10) {
                timer = 0;

                episode++;
                main.gameManager().episodesManager().episode(episode);

                Bukkit.broadcastMessage(Main.PREFIX + "§6Episode " + episode + " !");
            }

            if (episode == 2 && timer == 0) {
                main.gameManager().giveRoleToPlayers();
            }
        }
    }
}
