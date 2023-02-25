package fr.twah2em.uhcdieu.game.runnables;

import fr.twah2em.uhcdieu.Main;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class StartGameBukkitRunnable extends BukkitRunnable {
    private int timer = 11;

    private final Main main;

    public StartGameBukkitRunnable(Main main) {
        this.main = main;

        runTaskTimer(main, 0L, 20L);
    }

    @Override
    public void run() {
        timer--;

        if (timer == 0) {
            cancel();

            main.gameManager().startGame();
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.resetTitle();
                player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_BELL, Sound.Source.AMBIENT, 1.0F, 1.0F));
            });
        } else {
            // timer > 5 green; timer == 5 ou timer == 4 jaune; timer == 3 orange; timer == 2 rouge clair; timer == 1 rouge foncé;
            final String color = (timer > 5 ? "§a" : timer == 5 || timer == 4 ? "§e" : timer == 3 ? "§6" : timer == 2 ? "§c" : "§4") + "§l";

            Bukkit.broadcastMessage(Main.PREFIX + "§aLa partie commence dans " + color + timer + " §asecondes !");
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitlePart(TitlePart.TITLE, Component.text("§aLa partie commence dans"));
                player.sendTitlePart(TitlePart.SUBTITLE, Component.text(color + timer + " §asecondes !"));
                player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_BELL, Sound.Source.AMBIENT, 1.0F, timer * 0.1F + 1.0F));
            });
        }
    }
}
