package fr.twah2em.uhcdieu.game;

public enum GameState {
    WAITING,
    STARTING,
    EPISODES,
    END,

    ;

    private static GameState gameState;

    public static void gameState(GameState gameState) {
        GameState.gameState = gameState;
    }

    public static GameState gameState() {
        return gameState;
    }
}
