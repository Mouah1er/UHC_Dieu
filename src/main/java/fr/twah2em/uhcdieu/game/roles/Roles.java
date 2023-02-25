package fr.twah2em.uhcdieu.game.roles;

import fr.twah2em.uhcdieu.game.GameManager;
import fr.twah2em.uhcdieu.game.roles.angels.*;
import fr.twah2em.uhcdieu.utils.streams.StreamUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public enum Roles {
    JOUEUR(new PlayerRole()),

    // ANGES
    DIEU(new DieuRole()),
    MICHEL(new MichelRole()),
    GABRIEL(new GabrielRole()),
    RAPHAEL(new RaphaelRole()),
    URIEL(new UrielRole()),
    CAMAEL(new CamaelRole()),
    JOFIEL(new JofielRole()),
    /*TSADQIEL(),
    EVEQUE(),
    ARCHEVEQUE(),

    // SOLO
    THANATOS(),

    // DEMONS
    LUCIFER(),
    MEPHISTO(),
    DIABLO(),
    BAAL(),
    ASTAROTH(),
    AZAZEL(),
    PHEONIX(),
    BAALAM(),
    SATANISTE_NOVICE(),
    SATANISTE_AVANCE(),*/
    ;

    public final Role role;

    public static final HashMap<Roles, UUID> rolesByPlayers = new HashMap<>();

    Roles(Role role) {
        this.role = role;
    }

    public Role role() {
        return role;
    }

    public boolean isAngel() {
        return role.side() == Sides.ANGELS;
    }

    public boolean isDemon() {
        return role.side() == Sides.DEMONS;
    }

    public boolean isSolo() {
        return role.side() == Sides.SOLOS;
    }

    public static UUID playerByRole(Role role) {
        return rolesByPlayers.get(role.roles());
    }

    public static Role roleByPlayer(UUID uuid) {
        return StreamUtils.getKeyByValue(rolesByPlayers, uuid).role();
    }

    public static void playerRole(Player player, Roles roles) {
        System.out.println(player.getUniqueId());
        rolesByPlayers.put(roles, player.getUniqueId());

        final Role role = roles.role();
        player.sendMessage(role.description());
        role.giveItems(true);
        System.out.println(role);
        role.applyPermaEffects(true);
    }

    public static void randomRoles() {
        /*final List<Player> remainingPlayers = new ArrayList<>(Bukkit.getOnlinePlayers().stream().toList());

        final List<Roles> angelRoles = new ArrayList<>(Arrays.stream(values()).filter(Roles::isAngel).toList());
        final List<Roles> demonRoles = new ArrayList<>(Arrays.stream(values()).filter(Roles::isDemon).toList());

        Collections.shuffle(remainingPlayers);
        Collections.shuffle(angelRoles);
        Collections.shuffle(demonRoles);

        final Player solo = remainingPlayers.get(0);
        remainingPlayers.remove(0);
        final List<Player> angels = new ArrayList<>(StreamUtils.halfList(remainingPlayers));
        final List<Player> demons = new ArrayList<>(StreamUtils.removeDuplicates(remainingPlayers, angels));

        //playerRole(solo, Roles.THANATOS);

        angels.forEach(player -> {
            playerRole(player, angelRoles.get(0));

            angelRoles.remove(0);
        });

        demons.forEach(player -> {
            playerRole(player, demonRoles.get(0));

            demonRoles.remove(0);
        });*/

        playerRole(Bukkit.getPlayer("Twah2em"), Roles.JOFIEL);
    }

    public static List<UUID> angelPlayers(GameManager gameManager) {
        return new ArrayList<>(gameManager.alivePlayers().stream()
                .filter(player -> roleByPlayer(player).roles().isAngel())
                .toList());
    }

    public static List<UUID> demonPlayers(GameManager gameManager) {
        return new ArrayList<>(gameManager.alivePlayers().stream()
                .filter(player -> roleByPlayer(player).roles().isDemon())
                .toList());
    }

    /*public static UUID soloPlayer() {
        return playerByRole(Roles.THANATOS.role());
    }*/
}
