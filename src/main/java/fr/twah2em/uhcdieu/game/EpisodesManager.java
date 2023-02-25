package fr.twah2em.uhcdieu.game;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.runnables.EpisodesBukkitRunnable;

public class EpisodesManager {
    private final Main main;
    private int episode;

    public EpisodesManager(Main main) {
        this.main = main;

        this.episode = 0;
    }

    public void startEpisodesRunnable() {
        new EpisodesBukkitRunnable(main);
    }

    public int episode() {
        return episode;
    }

    public void episode(int episode) {
        this.episode = episode;
    }
}
