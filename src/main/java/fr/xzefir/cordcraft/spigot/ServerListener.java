package fr.xzefir.cordcraft.spigot;

import fr.xzefir.cordcraft.spigot.request.Sender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ServerListener implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        String pseudo = event.getPlayer().getName();

        Sender.sendMessageToDiscord(msg, pseudo);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        String pseudo = p.getName();

        Sender.sendDeathToDiscord(pseudo);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        String pseudo = p.getName();

        Sender.sendLoginToDiscord(pseudo);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        String pseudo = p.getName();

        Sender.sendQuitToDiscord(pseudo);
    }
}
