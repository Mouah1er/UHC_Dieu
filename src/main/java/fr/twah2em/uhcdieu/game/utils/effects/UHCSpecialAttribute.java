package fr.twah2em.uhcdieu.game.utils.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class UHCSpecialAttribute extends UHCAttribute {
    private final Consumer<Player> actionAfterRecovery;
    private final int useTime;
    private final int recoveryTime;

    public UHCSpecialAttribute(Attribute bukkitAttribute, double value, Consumer<Player> actionAfterApply, Consumer<Player> actionAfterRecovery, int useTime, int recoveryTime) {
        super(bukkitAttribute, value, actionAfterApply);

        this.actionAfterRecovery = actionAfterRecovery;
        this.useTime = useTime;
        this.recoveryTime = recoveryTime;
    }

    public Consumer<Player> actionAfterRecovery() {
        return actionAfterRecovery;
    }

    public int useTime() {
        return useTime;
    }

    public int recoveryTime() {
        return recoveryTime;
    }
}
