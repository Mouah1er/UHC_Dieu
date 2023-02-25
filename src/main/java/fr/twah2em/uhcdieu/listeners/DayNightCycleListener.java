package fr.twah2em.uhcdieu.listeners;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.roles.Role;
import fr.twah2em.uhcdieu.game.roles.Roles;
import fr.twah2em.uhcdieu.listeners.internal.UHCListener;
import fr.twah2em.uhcdieu.listeners.internal.events.DayNightCycleEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;

public class DayNightCycleListener implements UHCListener<DayNightCycleEvent> {
    public DayNightCycleListener(Main main) {}

    @EventHandler
    @Override
    public void onEvent(DayNightCycleEvent event) {
        final DayNightCycleEvent.Cycle cycle = event.cycle();

        System.out.println("Cycle: " + cycle);

        final Role jofielRole = Roles.JOFIEL.role();
        System.out.println(jofielRole + " e ");
        final Player jofielPlayer = Bukkit.getPlayer(jofielRole.player());

        if (jofielPlayer == null) return;
        System.out.println("test");

        final PotionEffect weaknessEffect = jofielRole.specialEffects()[0].bukkitPotionEffect();
        final PotionEffect regenEffect = jofielRole.specialEffects()[1].bukkitPotionEffect();

        if (cycle == DayNightCycleEvent.Cycle.DAY) {
            jofielPlayer.addPotionEffect(weaknessEffect);
        } else if (cycle == DayNightCycleEvent.Cycle.NIGHT) {
            jofielPlayer.addPotionEffect(regenEffect);
        }
    }
}
