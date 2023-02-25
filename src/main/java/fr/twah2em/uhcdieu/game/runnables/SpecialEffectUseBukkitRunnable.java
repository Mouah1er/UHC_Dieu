package fr.twah2em.uhcdieu.game.runnables;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialPotionEffect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpecialEffectUseBukkitRunnable extends BukkitRunnable {
    private final UHCSpecialPotionEffect potionEffect;
    private final Player player;

    private int timer;

    public SpecialEffectUseBukkitRunnable(Main main, UHCSpecialPotionEffect potionEffect, Player player) {
        this.potionEffect = potionEffect;
        this.player = player;
        this.timer = potionEffect.useTime();

        runTaskTimer(main, 0, 20);
    }

    @Override
    public void run() {
        timer--;

        if (timer == 0) {
            player.removePotionEffect(potionEffect.bukkitPotionEffect().getType());
            player.sendMessage("§c[§6UHC-Dieu§c] §aVotre effet spécial §b" + potionEffect.name() + " " + (potionEffect.bukkitPotionEffect().getAmplifier() + 1) +
                    "§a a expiré !");
            cancel();
        }
    }
}
