package fr.xzefir.cordcraft.spigot.request;

import fr.xzefir.cordcraft.spigot.CordCraft;

import java.net.URLEncoder;

public class Sender {

    public static void sendMessageToDiscord(String msg, String pseudo, String token, String guildID) {
        Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=sendChat&token=%s&guildid=%s&pseudo=%s&msg=%s", token, guildID, pseudo, URLEncoder.encode(msg))));
        t.start();
    }

    public static void sendDeathToDiscord(String pseudo, String token, String guildID) {
        Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=playerAction&token=%s&guildid=%s&pseudo=%s&playerAction=death", token, guildID, pseudo)));
        t.start();
    }

    public static void sendLoginToDiscord(String pseudo, String token, String guildID) {
        Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=playerAction&token=%s&guildid=%s&pseudo=%s&playerAction=login", token, guildID, pseudo)));
        t.start();
    }

    public static void sendQuitToDiscord(String pseudo, String token, String guildID) {
        Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=playerAction&token=%s&guildid=%s&pseudo=%s&playerAction=quit", token, guildID, pseudo)));
        t.start();
    }

    public static void sendServerStopToDiscord(String token, String guildID) {
        Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=serverAction&token=%s&guildid=%s&serverAction=stop", token, guildID)));
        t.start();
    }

    public static void sendServerStartToDiscord(String token, String guildID) {
        Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=serverAction&token=%s&guildid=%s&serverAction=start", token, guildID)));
        t.start();
    }
}
