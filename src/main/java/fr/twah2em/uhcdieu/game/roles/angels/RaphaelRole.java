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

public class RaphaelRole implements Role {
    @Override
    public Roles roles() {
        return Roles.RAPHAEL;
    }

    @Override
    public String name() {
        return "§7Raphaël";
    }

    @Override
    public String[] description() {
        return new String[]{
                "§8§l>> §b§lRaphaël",
                "§7Raphaël est un des trois archanges.",
                "§7Il peut ressuciter quelqu'un (§6/uhc revive <joueur>§7) mais il perd 3 coeurs pendant 10 minutes (7 coeurs en tout).",
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
        return new UHCPotionEffect[0];
    }

    @Override
    public UHCAttribute[] permaAttributes() {
        return new UHCAttribute[0];
    }

    @Override
    public UHCSpecialPotionEffect[] specialEffects() {
        return new UHCSpecialPotionEffect[0];
    }

    @Override
    public UHCSpecialAttribute[] specialAttributes() {
        return new UHCSpecialAttribute[]{
                new UHCSpecialAttribute(Attribute.GENERIC_MAX_HEALTH, 14D,
                        player -> player.setHealth(14D),
                        player -> {
                            player.setHealth(20D);
                            player.sendMessage("Vous avez récupéré vos 3 coeurs de base.");
                        },
                        600,
                        Integer.MAX_VALUE)
        };
    }
}
