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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class UrielRole implements Role {
    @Override
    public Roles roles() {
        return Roles.URIEL;
    }

    @Override
    public String name() {
        return "§7Uriel";
    }

    @Override
    public String[] description() {
        return new String[]{
                "§8§l>> §b§lUriel",
                "§7Uriel est un ange.",
                "§7Il possède une épée en diamant en feu (fire aspect I) avec 4 obsidiennes et 2 diamants.",
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
                new ItemBuilder(Material.DIAMOND_SWORD)
                        .withEnchant(Enchantment.FIRE_ASPECT)
                        .withName("§cEpée en feu")
                        .build(),
                new ItemBuilder(Material.OBSIDIAN)
                        .withAmout(4)
                        .build(),
                new ItemBuilder(Material.DIAMOND)
                        .withAmout(2)
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
