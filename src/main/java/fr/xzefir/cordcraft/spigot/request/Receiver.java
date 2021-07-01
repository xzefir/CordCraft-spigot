package fr.xzefir.cordcraft.spigot.request;

import fr.xzefir.cordcraft.spigot.CordCraft;
import fr.xzefir.cordcraft.spigot.DiscordMessage;
import org.bukkit.Bukkit;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse;

public class Receiver extends NanoHTTPD {

    String configGuildID = CordCraft.getInstance().guildID;
    String configToken = CordCraft.getInstance().token;

    public Receiver(Integer port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> params = session.getParms();

        String msg = GetStringURL(String.format(CordCraft.API_URL + "?action=testConnect&token=%s&guildid=%s", configToken, configGuildID));
        String token = params.get("token");
        String guildID = params.get("guildID");
        String action = params.get("action");

        if (params.get("version") != null)
            msg = CordCraft.VERSION;
        else if (token != null && guildID != null) {
            if (token.equals(configToken) && guildID.equals(configGuildID)) {
                if (action.equals("sendMessage")) {
                    String message = params.get("msg");
                    DiscordMessage.sendMessage(message);
                }
                if (action.equals("infoServ")) {
                    boolean paper;
                    try {
                        Class.forName("com.destroystokyo.paper.utils.PaperPluginLogger");
                        paper = true;
                    } catch (ClassNotFoundException e) {
                        paper = false;
                    }

                    String tps;
                    if (paper) {
                        tps = String.valueOf(Bukkit.getTPS()[0]);
                    } else tps = "Error";
                    String playerOnline = String.valueOf((long) Bukkit.getServer().getOnlinePlayers().size());
                    String maxplayer = String.valueOf(Bukkit.getServer().getMaxPlayers());
                    msg = String.format("%s-%s-%s", tps, playerOnline, maxplayer);
                }
                else if (action.equals("testConnection"))
                    msg = "true";
            }
        }

        return newFixedLengthResponse(msg);
    }

    public static void SimplePingURL(String url) {
        try{
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            in.close();
            con.disconnect();
        }
        catch(IOException ignored){}
    }

    public static String GetStringURL(String url) {
        String result = "";
        try{
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            result = String.valueOf(content);
        }
        catch(IOException ignored){

        }
        return result;
    }
}
