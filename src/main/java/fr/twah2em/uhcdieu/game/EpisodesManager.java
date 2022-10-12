package fr.twah2em.uhcdieu.game;

public class EpisodesManager {
    private int episode;

    public EpisodesManager() {
        episode = 0;
    }

    public void startEpisodesRunnable() {

    }

    public int episode() {
        return episode;
    }

    public void episode(int episode) {
        this.episode = episode;
    }
}
