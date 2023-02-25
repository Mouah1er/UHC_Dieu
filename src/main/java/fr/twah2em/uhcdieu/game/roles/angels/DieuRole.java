package fr.twah2em.uhcdieu.game.roles.angels;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.GameManager;
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
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.Objects;

public class DieuRole implements Role {
    @Override
    public Roles roles() {
        return Roles.DIEU;
    }

    @Override
    public String name() {
        return "§7Dieu";
    }

    @Override
    public String[] description() {
        return new String[]{
                "§8§l>> §3§lDieu",
                "§7Dieu est le chef du clan des anges.",
                "§7En temps normal il a un effet de Resistance 1," +
                        " Force 1 et 12 coeurs. Il possède une étoile du nether qui lui donne 3 coeurs en plus (15 en tout), Force 1, Resistance 2 pendant 3 minutes et s'il est en dessous" +
                        " de 10 coeurs à la fin des 3 minutes il récupère sa vie jusqu'à 10 coeurs. Le cooldown de l'objet est de 30 minutes. De plus il donne l'effet Résistance 1 à tous" +
                        " les anges lorsqu'il est vivant.",
                "",
                "§7Il doit gagner en étant le dernier joueur en vie."
        };
    }

    @Override
    public Sides side() {
        return Sides.ANGELS;
    }

    @Override
    public ItemStack[] items() {
        return new ItemStack[]{
                new ItemBuilder(Material.NETHER_STAR)
                        .withName("§bPouvoir §7§o(Clique droit)")
                        .withLore("§7Effets pendant 3 minutes: ",
                                "§6-§73 coeurs en plus",
                                "§6-§7Force 1 (ne change pas de d'habitude)",
                                "§6-§7Résistance 2",
                                "§6-§7A la fin du pouvoir si tu as moins de 10 coeurs",
                                "§6-§7tu les récupères.",
                                "",
                                "§7§oCooldown: 30 minutes.")
                        .build()
        };
    }

    @Override
    public UHCPotionEffect[] permaEffects() {
        return new UHCPotionEffect[]{
                new UHCPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0)),
                new UHCPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0))
        };
    }

    @Override
    public boolean applyPermaEffects(boolean apply) {
        final GameManager gameManager = JavaPlugin.getPlugin(Main.class).gameManager();

        Roles.angelPlayers(gameManager)
                .stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(angel -> angel.addPotionEffects(Collections.singletonList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0))));

        return Role.super.applyPermaEffects(apply);
    }

    @Override
    public UHCAttribute[] permaAttributes() {
        return new UHCAttribute[]{
                new UHCAttribute(Attribute.GENERIC_MAX_HEALTH, 24D, (player) -> player.setHealth(24D))
        };
    }

    @Override
    public UHCSpecialPotionEffect[] specialEffects() {
        return new UHCSpecialPotionEffect[]{
                new UHCSpecialPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2), "Résistance 1",
                        180, 30 * 60)
        };
    }

    @Override
    public UHCSpecialAttribute[] specialAttributes() {
        return new UHCSpecialAttribute[]{
                new UHCSpecialAttribute(Attribute.GENERIC_MAX_HEALTH, 30D,
                        (player) -> {
                            player.setHealth(30D);
                            player.sendMessage("§aVous avez déclanché votre pouvoir, sa durée est de 3 minutes !");
                        },
                        (player) -> {
                            player.setHealth(24D);
                            player.sendMessage("§aVotre pouvoir est terminé ! Le cooldown de §c30§a minutes commence !");
                        },
                        180, 30 * 60)
        };
    }
}
