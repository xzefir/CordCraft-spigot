package fr.xzefir.cordcraft.spigot.utils;

import fr.xzefir.cordcraft.spigot.request.Receiver;
import org.json.JSONObject;

public class GenerateToken {

    public static String createToken() {
        String token;

        String API_URL = "https://api.motdepasse.xyz/create/?include_digits&include_lowercase&include_uppercase&password_length=42&quantity=1";
        String JSON = Receiver.GetStringURL(API_URL);

        JSONObject dataParse = new JSONObject(JSON);

        token = dataParse.getJSONArray("passwords").getString(0);

        return token;
    }
}
