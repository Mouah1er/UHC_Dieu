package fr.twah2em.uhcdieu.game.runnables;

import fr.twah2em.uhcdieu.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class StartGameBukkitRunnable extends BukkitRunnable {
    private int timer = 11;

    private final Main main;

    public StartGameBukkitRunnable(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        timer--;

        if (timer == 0) {
            cancel();

            main.gameManager().startGame();
        } else {
            // timer > 5 green; timer == 5 ou timer == 4 jaune; timer == 3 orange; timer == 2 rouge clair; timer == 1 rouge foncé;
            final String color = (timer > 5 ? "§a" : timer == 5 || timer == 4 ? "§e" : timer == 3 ? "§6" : timer == 2 ? "§c" : "§4") + "§l";

            Bukkit.broadcastMessage(Main.PREFIX + "§aLa partie commence dans " + color + timer + " §asecondes !");
        }
    }
}
