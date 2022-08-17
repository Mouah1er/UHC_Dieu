package fr.twah2em.uhcdieu.game;

import fr.twah2em.uhcdieu.Main;

public class GameManager {
    private final Main main;

    public GameManager(Main main) {
        this.main = main;
    }

    public void startGame() {
        GameState.gameState(GameState.STARTING);

    }
}
