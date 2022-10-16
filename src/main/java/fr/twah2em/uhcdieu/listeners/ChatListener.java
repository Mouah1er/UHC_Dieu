package fr.twah2em.uhcdieu.listeners;

import fr.twah2em.uhcdieu.Main;
import fr.twah2em.uhcdieu.listeners.internal.UHCListener;
import io.papermc.paper.event.player.ChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

// ChatEvent et pas AsyncChatEvent à cause de GameManager#choosePlayersStatus
public class ChatListener implements UHCListener<ChatEvent> {
    private final Main main;

    public ChatListener(Main main) {
        this.main = main;
    }

    @Override
    @EventHandler
    public void onEvent(ChatEvent event) {
        final Player player = event.getPlayer();
        final TextComponent message = (TextComponent) event.message();
        final Map<UUID, CompletableFuture<Boolean>> uuidCompletableFutureMap = main.gameManager().startCommandConfirmation();

        if (uuidCompletableFutureMap.containsKey(player.getUniqueId())) {
            event.setCancelled(true);

            final String content = message.content();
            final boolean isValid = content.equalsIgnoreCase("oui") || content.equalsIgnoreCase("non");

            if (isValid) {
                if (content.equalsIgnoreCase("oui")) {
                    uuidCompletableFutureMap.get(player.getUniqueId()).complete(true);
                } else {
                    uuidCompletableFutureMap.get(player.getUniqueId()).complete(false);
                }

                uuidCompletableFutureMap.remove(player.getUniqueId());
            } else {
                player.sendMessage(Main.PREFIX + "§eVous devez répondre par §aOui §eou par §cNon §epour lancer la partie.");
            }
        }
    }
}
