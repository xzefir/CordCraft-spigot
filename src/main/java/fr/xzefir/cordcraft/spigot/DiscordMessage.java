package fr.xzefir.cordcraft.spigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DiscordMessage {

    public static void sendMessage(String msg) {
        String messagetext = msg.replaceAll("&", "§");

        CordCraft.logger.info(messagetext);
        for (Player OnlinePlayer : Bukkit.getServer().getOnlinePlayers()){
            OnlinePlayer.sendMessage(messagetext);
        }
    }
}
