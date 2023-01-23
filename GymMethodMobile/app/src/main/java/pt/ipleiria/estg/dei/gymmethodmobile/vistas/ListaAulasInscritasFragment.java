package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.CalendarAdapter;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.ListaAulasInscritasAdaptador;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.ListaPlanosAdaptador;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.AulasInscritasListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PlanosListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.AulaInscrita;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class ListaAulasInscritasFragment extends Fragment implements AulasInscritasListener {

    private ListView lvAulasIscritas;
    private SearchView searchView;
    private String token;
    private AulaInscrita aulaInscrita;
    public ListaAulasInscritasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_aulas_inscritas, container, false);
        setHasOptionsMenu(true);
        lvAulasIscritas = view.findViewById(R.id.lvAulasInscritas);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);



        // Para selecionar um plano da lista
        lvAulasIscritas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SingletonGestorApp.getInstance(getContext()).unsetAulasInscritasListener();
                aulaInscrita = (AulaInscrita) lvAulasIscritas.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), InscricaoActivity.class);
                intent.putExtra("AULA_INSCRITA", aulaInscrita);
                intent.putExtra("ACTION", (int) 1);
                startActivityForResult(intent, 1);
            }
        });

        SingletonGestorApp.getInstance(getContext()).setAulasInscritasListener(this);
        SingletonGestorApp.getInstance(getContext()).getAulasInscritas(getContext(), token);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode== Activity.RESULT_OK){
            SingletonGestorApp.getInstance(getContext()).setAulasInscritasListener(this);
            SingletonGestorApp.getInstance(getContext()).getAulasInscritas(getContext(), token); // quando sai dos detalhes e volta a lsta
            Toast.makeText(getContext(), "Inscricao removida com sucesso", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onResume() {
        if (searchView!=null){
            searchView.onActionViewCollapsed();
        }
        super.onResume();
    }



    @Override
    public void onRefreshListaAulasInscritas(ArrayList<AulaInscrita> aulasInscritas) {
        if(aulasInscritas != null){
            lvAulasIscritas.setAdapter(new ListaAulasInscritasAdaptador(getContext(), aulasInscritas));
        }
    }
}