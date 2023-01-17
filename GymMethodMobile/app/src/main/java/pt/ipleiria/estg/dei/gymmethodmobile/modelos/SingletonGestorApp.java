package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.gymmethodmobile.listeners.LoginListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PlanosListener;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.JsonParser;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.PlanoJsonParser;
import pt.ipleiria.estg.dei.gymmethodmobile.vistas.MenuMainActivity;

public class SingletonGestorApp {

    private static  SingletonGestorApp instance = null;
    private static RequestQueue volleyQueue = null;
    private LoginListener loginListener;
    private PlanosListener planosListener;

    private ArrayList<Plano> planos;
    private BDHelper BD;

    private static final  String APILogin ="http://10.0.2.2/gymmethod/backend/web/api/auth/login";
    private static final  String APIGetPlanos ="http://10.0.2.2/gymmethod/backend/web/api/plano/get-planos";
    private static final  String APIGetExerciciosPlano ="http://10.0.2.2/gymmethod/backend/web/api/exercicio-plano/get-exercicios-plano/";


    public static synchronized SingletonGestorApp getInstance(Context context){
        if(instance == null)
        {
            instance = new SingletonGestorApp(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }
    private SingletonGestorApp(Context context){
        planos = new ArrayList<>();
        BD = new BDHelper(context);

    }
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setPlanosListener(PlanosListener planosListener) {
        this.planosListener = planosListener;
    }
    //region Planos

    public ArrayList<Plano> getPlanosBD() { // return da copia dos planos
        planos = BD.getAllPlanosBD();
        return new ArrayList(planos);
    }

    public Plano getPlano(int idPlano){
        for (Plano p : planos){
            if(p.getId() == idPlano)
                return p;
        }
        return null;
    }

    public void adicionarPlanoBD(Plano p)
    {
        BD.adicionarPlanoBD(p);
    }

    public void adicionarPlanosBD(ArrayList<Plano> planos)
    {
        BD.removerAllPlanos();
        for(Plano p : planos)
        {
            adicionarPlanoBD(p);
        }
    }

    //endregion
    public void loginAPI(final String username, final String password, final Context context){
        if (!JsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        }else
        {
            StringRequest req = new StringRequest(Request.Method.GET, APILogin,  new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                        String token = JsonParser.parserJsonLogin(response);
                        Integer user_id = JsonParser.parserJsonUser_id(response);
                        String username = JsonParser.parserJsonUsername(response);

                    if (loginListener != null)
                        loginListener.onValidateLogin(token, user_id, username, context);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Username ou password incorreto", Toast.LENGTH_LONG).show();
                    VolleyLog.d("LOGIN: Error" + error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String credentials = username + ":" + password;
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + base64EncodedCredentials);
                    return headers;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getAllPlanosAPI(final Context context, String token){
        if (!JsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
            if (planosListener!=null)
            {
                planosListener.onRefreshListaPlanos(BD.getAllPlanosBD());
            }
        }else
        {
                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetPlanos + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    planos = PlanoJsonParser.parserJsonPlanos(response);
                    adicionarPlanosBD(planos);

                    if (planosListener!=null)
                    {
                        planosListener.onRefreshListaPlanos(planos);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void getExerciciosPlanoAPI(final Context context, String token, int plano_id){
        if (!JsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
            if (planosListener!=null)
            {
                planosListener.onRefreshListaPlanos(BD.getAllPlanosBD());
            }
        }else
        {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetExerciciosPlano + plano_id + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    planos = PlanoJsonParser.parserJsonPlanos(response);
                    adicionarPlanosBD(planos);

                    if (planosListener!=null)
                    {
                        planosListener.onRefreshListaPlanos(planos);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }



}
