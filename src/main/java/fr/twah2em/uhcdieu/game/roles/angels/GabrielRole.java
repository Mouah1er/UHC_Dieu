package fr.twah2em.uhcdieu.game.roles.angels;

import fr.twah2em.uhcdieu.game.roles.Role;
import fr.twah2em.uhcdieu.game.roles.Roles;
import fr.twah2em.uhcdieu.game.roles.Sides;
import fr.twah2em.uhcdieu.game.utils.effects.UHCAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCPotionEffect;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialPotionEffect;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GabrielRole implements Role {

    @Override
    public Roles roles() {
        return Roles.GABRIEL;
    }

    @Override
    public String name() {
        return "§7Gabriel";
    }

    @Override
    public String[] description() {
        return new String[]{
                "§8§l>> §b§lGabriel",
                "§7Gabriel est un des trois archanges.",
                "§7Il a 3 coeurs en plus (13 en tout) et l'effet Résistance 1 en permanence.",
                "",
                "§7Il doit gagner avec les anges en tuant le clan des Démons, Lucifer et Dieu."
        };
    }

    @Override
    public Sides side() {
        return Sides.ANGELS;
    }

    @Override
    public ItemStack[] items() {
        return new ItemStack[0];
    }

    @Override
    public UHCPotionEffect[] permaEffects() {
        return new UHCPotionEffect[]{
                new UHCPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1))
        };
    }

    @Override
    public UHCAttribute[] permaAttributes() {
        return new UHCAttribute[]{
                new UHCAttribute(Attribute.GENERIC_MAX_HEALTH, 26D, (player -> player.setHealth(26D)))
        };
    }

    @Override
    public UHCSpecialPotionEffect[] specialEffects() {
        return new UHCSpecialPotionEffect[0];
    }

    @Override
    public UHCSpecialAttribute[] specialAttributes() {
        return new UHCSpecialAttribute[0];
    }
}
