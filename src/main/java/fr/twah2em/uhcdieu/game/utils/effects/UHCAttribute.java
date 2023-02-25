package fr.twah2em.uhcdieu.game.utils.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class UHCAttribute {
    private final Attribute bukkitAttribute;
    private final double value;
    private final Consumer<Player> actionAfterApply;

    public UHCAttribute(Attribute bukkitAttribute, double value, Consumer<Player> actionAfterApply) {
        this.bukkitAttribute = bukkitAttribute;
        this.value = value;
        this.actionAfterApply = actionAfterApply;
    }

    public Attribute bukkitAttribute() {
        return bukkitAttribute;
    }

    public double value() {
        return value;
    }

    public Consumer<Player> actionAfterApply() {
        return actionAfterApply;
    }
}
