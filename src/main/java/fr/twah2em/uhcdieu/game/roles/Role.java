package fr.twah2em.uhcdieu.game.roles;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.game.runnables.SpecialEffectUseBukkitRunnable;
import fr.twah2em.uhcdieu.game.utils.effects.UHCAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCPotionEffect;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialAttribute;
import fr.twah2em.uhcdieu.game.utils.effects.UHCSpecialPotionEffect;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public interface Role {

    Roles roles();

    default UUID player() {
        return Roles.playerByRole(this);
    }

    String name();

    String[] description();

    Sides side();

    ItemStack[] items();

    /**
     * @param give if true, gives the items to the player and returns true, if false returns false
     * @return returns false if the items could not be given or if the parameter is false, returns true if the items were given and if the parameter is true.
     */
    default boolean giveItems(boolean give) {
        final Player player = Bukkit.getPlayer(player());

        if (player == null || !give) {
            return false;
        }

        player.getInventory().addItem(items());

        return true;
    }

    UHCPotionEffect[] permaEffects();

    /**
     * @param apply if true, applies the effects to the player and returns true, if false returns false
     * @return returns false if the effects could not be applied or if the parameter is false, returns true if the effects were applied and if the parameter is true.
     */
    default boolean applyPermaEffects(boolean apply) {
        final Player player = Bukkit.getPlayer(player());

        if (player == null || !apply || permaEffects() == null) {
            return false;
        }

        player.addPotionEffects(Arrays.asList(Arrays.stream(permaEffects()).map(UHCPotionEffect::bukkitPotionEffect).toArray(PotionEffect[]::new)));

        return true;
    }

    UHCAttribute[] permaAttributes();

    /**
     * @param apply if true, applies the attributes to the player and returns true, if false returns false
     * @return returns false if the attributes could not be applied or if the parameter is false, returns true if the attributes were applied and if the parameter is true.
     */
    default boolean applyPermaAttributes(boolean apply) {
        final Player player = Bukkit.getPlayer(player());

        if (player == null || !apply || permaAttributes() == null) {
            return false;
        }

        for (UHCAttribute attribute : permaAttributes()) {
            final AttributeInstance attributeInstance = Objects.requireNonNull(player.getAttribute(attribute.bukkitAttribute()));
            attributeInstance.setBaseValue(attribute.value());

            attribute.actionAfterApply().accept(player);
        }

        return true;
    }

    // the special effects with their use and recovery times
    UHCSpecialPotionEffect[] specialEffects();

    default boolean applySpecialEffects(Main main) {
        final Player player = Bukkit.getPlayer(player());

        if (player == null || specialEffects() == null) {
            return false;
        }

        for (UHCSpecialPotionEffect potionEffect : specialEffects()) {
            player.addPotionEffect(potionEffect.bukkitPotionEffect());

            new SpecialEffectUseBukkitRunnable(main, potionEffect, player);
        }

        return true;
    }

    UHCSpecialAttribute[] specialAttributes();

    default boolean applySpecialAttributes() {
        final Player player = Bukkit.getPlayer(player());

        if (player == null || specialAttributes() == null) {
            return false;
        }

        for (UHCSpecialAttribute attribute : specialAttributes()) {
            final AttributeInstance attributeInstance = Objects.requireNonNull(player.getAttribute(attribute.bukkitAttribute()));
            attributeInstance.setBaseValue(attribute.value());

            attribute.actionAfterApply().accept(player);
        }

        return true;
    }
}
