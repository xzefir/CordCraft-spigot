package fr.xzefir.cordcraft.spigot;

import fr.xzefir.cordcraft.spigot.request.Receiver;
import fr.xzefir.cordcraft.spigot.request.Sender;
import fr.xzefir.cordcraft.spigot.utils.GenerateToken;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class CordCraft extends JavaPlugin {

    public static final String API_URL = "http://vps.craftmoney.fr:27980/";
    public static final String VERSION = "1.0.0";
    public static final int configVersion = 3;
    public static final Logger logger = Bukkit.getLogger();
    public static CordCraft instance;

    public boolean enabled;
    public int port = 0;
    public String guildID;
    public String token;

    @Override
    public void onEnable() {

        instance = this;

        try {
            //Not override existing config file if exists
            saveDefaultConfig();
        } catch (Exception e) {
            logger.severe("Could not write config");
        }

        enabled = getConfig().getBoolean("bot-enabeld");
        port = getConfig().getInt("port");
        guildID = getConfig().getString("guild-id");

        if (getConfig().getString("secret-token") != null) {
            logger.info("[CordCraft] Token not empty");
            token = getConfig().getString("secret-token");
        } else {
            logger.info("[CordCraft] Token empty");
            String s;
            s = GenerateToken.createToken();
            logger.info("[CordCraft] " + s);
            getConfig().set("secret-token", s);
            saveConfig();
        }

        if (getConfig().getInt("config-version") != 3) {
            enabled = false;
            logger.warning("Wrong Config version, please delete your CordCraft's config.yml and restart your server");
        }


        if (enabled) {
            try {
                new Receiver(port);
                logger.info("[CordCraft] Started");
            } catch (Exception e) {
                logger.severe("[CordCraft] Error, selected port is not available");
            }
            getServer().getPluginManager().registerEvents(new ServerListener(), this);
            logger.info("CordCraft enabled !");
            Sender.sendServerStartToDiscord(token, guildID);
        } else {
            logger.info("CordCraft is disabled. Enable it in Config");
        }
    }

    @Override
    public void onDisable() {
        if (enabled)
            Sender.sendServerStopToDiscord(token, guildID);
    }

    public static CordCraft getInstance() {
        return instance;
    }
}
