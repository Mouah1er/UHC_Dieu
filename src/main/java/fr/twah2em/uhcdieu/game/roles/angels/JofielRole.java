package fr.twah2em.uhcdieu.game.roles.angels;

import fr.twah2em.uhcdieu.game.roles.Role;
import fr.twah2em.uhcdieu.game.roles.Roles;
import fr.twah2em.uhcdieu.game.roles.Sides;
import fr.twah2em.uhcdieu.game.utils.effects.UHCAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCPotionEffect;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialPotionEffect;
import fr.twah2em.uhcdieu.inventories.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JofielRole implements Role {
    @Override
    public Roles roles() {
        return Roles.JOFIEL;
    }

    @Override
    public String name() {
        return "§7Jofiel";
    }

    @Override
    public String[] description() {
        return new String[]{
                "§8§l>> §b§lJofiel",
                "§7Jofiel est un ange.",
                "§7Il a 1 pomme d'or, et chaque kill qu'il fait il gagne une de plus, il a Slowness 1 la nuit et Speed 1 le jour",
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
                new ItemBuilder(Material.GOLDEN_APPLE).build()
        };
    }

    @Override
    public UHCPotionEffect[] permaEffects() {
        return new UHCPotionEffect[]{
                new UHCPotionEffect(new PotionEffect(PotionEffectType.SLOW, 420 * 20, 0)),
                new UHCPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600 * 20, 0))
        };
    }

    @Override
    public boolean applyPermaEffects(boolean apply) {
        final Player player = Bukkit.getPlayer(player());

        if (player == null) return false;

        player.addPotionEffect(permaEffects()[1].bukkitPotionEffect());

        return true;
    }

    @Override
    public UHCAttribute[] permaAttributes() {
        return new UHCAttribute[0];
    }

    @Override
    public UHCSpecialPotionEffect[] specialEffects() {
        return new UHCSpecialPotionEffect[]{
                new UHCSpecialPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0), "Slowness", 420, 780),
                new UHCSpecialPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0), "Speed", 600, 600)
        };
    }

    @Override
    public UHCSpecialAttribute[] specialAttributes() {
        return new UHCSpecialAttribute[0];
    }
}
