package fr.twah2em.uhcdieu.game.roles.angels;

import fr.twah2em.uhcdieu.game.roles.Role;
import fr.twah2em.uhcdieu.game.roles.Roles;
import fr.twah2em.uhcdieu.game.roles.Sides;
import fr.twah2em.uhcdieu.game.utils.effects.UHCAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCPotionEffect;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialPotionEffect;
import fr.twah2em.uhcdieu.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MichelRole implements Role {
    @Override
    public Roles roles() {
        return Roles.MICHEL;
    }

    @Override
    public String name() {
        return "§7Michel";
    }

    @Override
    public String[] description() {
        return new String[]{
                "§8§l>> §b§lMichel",
                "§7Michel est un des trois archanges.",
                "§7En temps normal il a un effet de Resistance 1. Il possède des ailes (une plume) qui lui donne un effet de Jump Boost 1 et de Speed 2 pendant 30 secondes avec un cooldown de 7 minutes",
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
        return new ItemStack[]{
                new ItemBuilder(Material.FEATHER)
                        .withName("§bPouvoir §7§o(Clique droit)")
                        .withLore("Effets pendant 30 secondes:",
                                "§6-§7Jump Boost 1",
                                "§6-§7Speed 2",
                                "",
                                "§7§oCooldown: 7 minutes.")
                        .build()
        };
    }

    @Override
    public UHCPotionEffect[] permaEffects() {
        return new UHCPotionEffect[]{
            new UHCPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1))
        };
    }

    @Override
    public UHCAttribute[] permaAttributes() {
        return new UHCAttribute[0];
    }

    @Override
    public UHCSpecialPotionEffect[] specialEffects() {
        return new UHCSpecialPotionEffect[] {
                new UHCSpecialPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1), "Jump Boost", 30, 7 * 60),
                new UHCSpecialPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2), "Speed", 30, 7 * 60)
        };
    }

    @Override
    public UHCSpecialAttribute[] specialAttributes() {
        return new UHCSpecialAttribute[0];
    }
}
