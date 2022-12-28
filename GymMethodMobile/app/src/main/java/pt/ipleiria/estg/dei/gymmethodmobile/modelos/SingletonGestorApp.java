package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.listeners.LoginListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PlanosListener;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.PlanoJsonParser;

public class SingletonGestorApp {

    private static  SingletonGestorApp instance = null;
    private static RequestQueue volleyQueue = null;
    private LoginListener loginListener;
    private PlanosListener planosListener;

    private ArrayList<Plano> planos;
    private BDHelper planosBD;

    private static final  String APIGetPlanos ="http://10.0.2.2/gymmethod/backend/web/api/plano/get-planos/107";

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
        planosBD = new BDHelper(context);

    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
    public void setPlanosListener(PlanosListener planosListener) {
        this.planosListener = planosListener;
    }
    //region Planos

    public ArrayList<Plano> getPlanosBD() { // return da copia dos planos
        planos = planosBD.getAllPlanosBD();
        return new ArrayList(planos);
    }

    public Plano getPlano(int idPlano){
        for (Plano p : planos){
            if(p.getId() == idPlano)
                return p;
        }
        return null;
    }

    public void adicionarLivroBD(Plano p)
    {
        planosBD.adicionarPlanoBD(p);
    }

    public void adicionarPlanosBD(ArrayList<Plano> planos)
    {
        planosBD.removerAllPlanos();
        for(Plano p : planos)
        {
            adicionarLivroBD(p);
        }
    }

    //endregion


    public void getAllPlanosAPI(final Context context){
        if (!PlanoJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
            if (planosListener!=null)
            {
                planosListener.onRefreshListaPlanos(planosBD.getAllPlanosBD());
            }
        }else
        {
            JsonArrayRequest req=new JsonArrayRequest(Request.Method.GET, APIGetPlanos, null, new Response.Listener<JSONArray>() {
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
