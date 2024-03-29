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
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.ListaPlanosAdaptador;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PlanosListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class ListaPlanosFragment extends Fragment implements PlanosListener {

    private ListView lvPlanos;
    private SearchView searchView;
    public static final int ACT_EXERCICIOS = 1;
    private String token;
    private Integer user_id;
    private String user;
    public ListaPlanosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_planos, container, false);
        setHasOptionsMenu(true);
        lvPlanos = view.findViewById(R.id.lvPlanos);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);


        // Para selecionar um plano da lista
        lvPlanos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ListaExerciciosPlanoActivity.class);
                intent.putExtra("PLANO_ID", (int) id);
                startActivityForResult(intent, ACT_EXERCICIOS);
            }
        });

        SingletonGestorApp.getInstance(getContext()).setPlanosListener(this);
        SingletonGestorApp.getInstance(getContext()).getAllPlanosAPI(getContext(), token);
        return view;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode== Activity.RESULT_OK && requestCode==ACT_DETALHES){
            SingletonGestorApp.getInstance(getContext()).getAllPlanosAPI(getContext(), token); // quando sai dos detalhes e volta a lsta

            switch (intent.getIntExtra(MenuMainActivity.OPERACAO, 0)){
                case MenuMainActivity.ADD:
                    Toast.makeText(getContext(), "Livro adicionado", Toast.LENGTH_SHORT).show();
                    break;
                case MenuMainActivity.EDIT:
                    Toast.makeText(getContext(), "Livro editado", Toast.LENGTH_SHORT).show();
                    break;
                case MenuMainActivity.DELETE:
                    Toast.makeText(getContext(), "Livro eliminado", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }*/

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) { // pesquisa de aulas projeto
        inflater.inflate(R.menu.menu_pesquisa,menu);
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Plano> tempLista = new ArrayList<>();
                for(Plano p: SingletonGestorApp.getInstance(getContext()).getPlanosBD())
                {
                    if (p.getNome().toLowerCase().contains(s.toLowerCase()))
                    {
                        tempLista.add(p);
                    }
                    lvPlanos.setAdapter(new ListaPlanosAdaptador(getContext(), tempLista));
                }
                return true;

            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    //


    @Override
    public void onResume() {
        if (searchView!=null){
            searchView.onActionViewCollapsed();
        }
        super.onResume();
    }


    @Override
    public void onRefreshListaPlanos(ArrayList<Plano> listaPlanos) {
        if(listaPlanos != null){
            lvPlanos.setAdapter(new ListaPlanosAdaptador(getContext(), listaPlanos));
        }
    }
}