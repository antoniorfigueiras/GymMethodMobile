package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.ListaConsultasAdaptador;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.ConsultasListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Consulta;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class ListaConsultasFragment extends Fragment implements ConsultasListener {

    private ListView lvConsultas;
    private SearchView searchView;
    public static final int ACT_EXERCICIOS = 1;
    private String token;
    private Integer user_id;
    private String user;
    public ListaConsultasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_consultas, container, false);
        setHasOptionsMenu(true);
        lvConsultas = view.findViewById(R.id.lvConsultas);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);


        SingletonGestorApp.getInstance(getContext()).setConsultasListener(this);
        SingletonGestorApp.getInstance(getContext()).getAllConsultasAPI(getContext(), token);
        return view;
    }

    @Override
    public void onRefreshListaConsultas(ArrayList<Consulta> listaConsultas) {
        if (listaConsultas !=null){
            lvConsultas.setAdapter(new ListaConsultasAdaptador(getContext(),listaConsultas));
        }
    }
}