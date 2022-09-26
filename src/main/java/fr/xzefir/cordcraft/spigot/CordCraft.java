package fr.xzefir.cordcraft.spigot;

import fr.xzefir.cordcraft.spigot.request.Receiver;
import fr.xzefir.cordcraft.spigot.request.Sender;
import fr.xzefir.cordcraft.spigot.utils.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class CordCraft extends JavaPlugin {

    public static final String API_URL = "http://v02.owntech.fr:3001";
    public static final String VERSION = "1.0.2";
    public static final int VERSION_DATA_FILE = 3;

    public static String token = "";
    public static String guildID = "";
    public static String port = "";
    public static String enable = "";

    public static Logger logger = Bukkit.getLogger();

    @Override
    public void onEnable() {
        String fileEmplacement = this.getDataFolder().getAbsolutePath() + ".json";
        File dataFile = new File(fileEmplacement);

        if (!ConfigFile.configCreate(fileEmplacement, dataFile))
            logger.severe("ConfigFile : Error creating config file.");
        if (!ConfigFile.configUpdate(fileEmplacement))
            logger.severe("ConfigFile : Error update config file.");
        if (!ConfigFile.configSetValueFromFile(fileEmplacement))
            logger.severe("ConfigFile : Error loading config file.");

        if (enable.equals("true")) {
            if(port.equals("")) {
                logger.warning("APIWeb : Please configure the port in the config file ().");
            } else {
                try {
                    new Receiver(CordCraft.port);
                    logger.info("APIWeb : Started");
                } catch (Exception e) {
                    logger.severe("APIWeb : Error (port not available)");
                }
            }
            getServer().getPluginManager().registerEvents(new ServerListener(), this);
            logger.info("CordCraft enable !");
            Sender.sendServerStartToDiscord();
        } else
            logger.info("CordCraft is disable.");
    }

    @Override
    public void onDisable() {
        if (enable.equals("true"))
            Sender.sendServerStopToDiscord();
    }
}
