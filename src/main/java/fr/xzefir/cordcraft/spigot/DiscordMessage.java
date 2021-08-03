package fr.xzefir.cordcraft.spigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DiscordMessage {

    public static void sendMessage(String msg) {
        String textMessage = msg.replaceAll("&", "§");

        CordCraft.logger.info(textMessage);
        for (Player OnlinePlayer : Bukkit.getServer().getOnlinePlayers()){
            OnlinePlayer.sendMessage(textMessage);
        }
    }
}
