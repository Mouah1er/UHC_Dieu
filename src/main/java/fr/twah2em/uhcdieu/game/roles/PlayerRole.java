package fr.twah2em.uhcdieu.game.roles;

import fr.twah2em.uhcdieu.game.utils.effects.UHCAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCPotionEffect;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialPotionEffect;
import fr.twah2em.uhcdieu.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PlayerRole implements Role {
    @Override
    public Roles roles() {
        return Roles.JOUEUR;
    }

    @Override
    public String name() {
        return "§7Joueur";
    }

    @Override
    public String[] description() {
        return new String[]{"§cErreur: Vous n'avez aucun role, demandez à l'hote de la partie !"};
    }

    @Override
    public Sides side() {
        return Sides.PLAYER;
    }

    @Override
    public ItemStack[] items() {
        return new ItemStack[]{
                new ItemBuilder(Material.DARK_OAK_DOOR)
                        .withName("§cErreur: Vous n'avez aucun role, demandez à l'hote de la partie !")
                        .build()
        };
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
        return new UHCSpecialAttribute[0];
    }
}
