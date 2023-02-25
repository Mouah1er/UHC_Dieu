package fr.twah2em.uhcdieu.game.utils.effects;

import org.bukkit.potion.PotionEffect;

public class UHCSpecialPotionEffect extends UHCPotionEffect {
    private final String name;
    private final int useTime;
    private final int recoveryTime;

    public UHCSpecialPotionEffect(PotionEffect potionEffect, String name, int useTime, int recoveryTime) {
        super(potionEffect);

        this.name = name;
        this.useTime = useTime;
        this.recoveryTime = recoveryTime;
    }

    public String name() {
        return name;
    }

    public int useTime() {
        return useTime;
    }

    public int recoveryTime() {
        return recoveryTime;
    }
}
