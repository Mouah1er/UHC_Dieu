package fr.twah2em.uhcdieu.listeners.internal;

import fr.twah2em.uhcdieu.Main;
import org.bukkit.Bukkit;

import java.util.function.Function;

public class UHCListenerRegistration {
    private final Main main;

    public UHCListenerRegistration(Main main) {
        this.main = main;
    }

    @SafeVarargs
    public final void registerListeners(Function<Main, ? extends UHCListener<?>>... listeners) {
        for (Function<Main, ? extends UHCListener<?>> listener : listeners) {
            final UHCListener<?> uhcListener = listener.apply(main);

            Bukkit.getPluginManager().registerEvents(uhcListener, main);
        }
    }
}
