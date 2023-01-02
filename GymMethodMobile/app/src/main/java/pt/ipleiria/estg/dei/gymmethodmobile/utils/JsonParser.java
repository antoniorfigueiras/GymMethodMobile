package pt.ipleiria.estg.dei.gymmethodmobile.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public static String parserJsonLogin(String response) { // static para nao ter de fazer new
        String token = null;
        try {
            JSONObject login = new JSONObject(response);

            if (login.getBoolean("success")){
                token = login.getString("token");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }
    public static JSONObject parserJsonUser(String response) {
        JSONObject user = new JSONObject();
        try {
             user = new JSONObject(response).getJSONObject("perfil");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
    public static Boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
