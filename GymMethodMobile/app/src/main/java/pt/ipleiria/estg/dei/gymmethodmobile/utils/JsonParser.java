package pt.ipleiria.estg.dei.gymmethodmobile.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

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
            else {
               return token;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }
    /*public static Integer parserJsonUser_id(String response) {
        Integer user_id = null;
        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success")){
                user_id = login.getInt("user_id");
            }
            else {
                return user_id;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user_id;
    }*/
    public static String parserJsonUsername(String response) {
        String username = null;
        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success")){
                username = login.getString("username");
            }
            else {
                return username;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }
    public static String parserJsonImage(String response) {
        String image = null;
        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success")){
                image = login.getString("image");
            }
            else {
                return image;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }
    public static Boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
