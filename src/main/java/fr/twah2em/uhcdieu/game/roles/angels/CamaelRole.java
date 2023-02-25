package fr.twah2em.uhcdieu.game.roles.angels;

import fr.twah2em.uhcdieu.game.roles.Role;
import fr.twah2em.uhcdieu.game.roles.Roles;
import fr.twah2em.uhcdieu.game.roles.Sides;
import fr.twah2em.uhcdieu.game.utils.effects.UHCAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCPotionEffect;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialPotionEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CamaelRole implements Role {
    @Override
    public Roles roles() {
        return Roles.CAMAEL;
    }

    @Override
    public String name() {
        return "§7Camael";
    }

    @Override
    public String[] description() {
        return new String[]{
                "§8§l>> §b§lCamael",
                "§7Camael est un ange.",
                "§7Il a Résistance 1 et donne speed 1 à tous ceux (Démons compris) à moins de 15 blocks.",
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
                new UHCPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0))
        };
    }

    @Override
    public boolean applyPermaEffects(boolean apply) {
        final Player player = Bukkit.getPlayer(player());

        if (player == null) return false;

        player.getNearbyEntities(15, 15, 15)
                .stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .forEach(player1 -> player1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)));

        return Role.super.applyPermaEffects(apply);
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
