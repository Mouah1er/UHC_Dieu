package fr.twah2em.uhcdieu.game.utils.effects;

import org.bukkit.potion.PotionEffect;

public class UHCPotionEffect {
    private final PotionEffect bukkitPotionEffect;

    public UHCPotionEffect(PotionEffect bukkitPotionEffect) {
        this.bukkitPotionEffect = bukkitPotionEffect;
    }

    public PotionEffect bukkitPotionEffect() {
        return bukkitPotionEffect;
    }
}
