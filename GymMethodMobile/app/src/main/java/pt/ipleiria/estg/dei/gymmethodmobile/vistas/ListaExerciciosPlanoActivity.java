package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Objects;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.ListaExerciciosAdaptador;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.ListaPlanosAdaptador;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.ExerciciosPlanoListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PlanosListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class ListaExerciciosPlanoActivity extends AppCompatActivity implements ExerciciosPlanoListener {

    private ListView lvExercicios;
    private SearchView searchView;
    //public static final int ACT_DETALHES = 1;
    private String token;
    private Integer user_id;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_exercicios_plano);
        setTitle("Exercicios");
        lvExercicios = findViewById(R.id.lvExercicios);

        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
        user_id =  sharedPreferences.getInt(MenuMainActivity.USER_ID, 0);
        user = sharedPreferences.getString(MenuMainActivity.USERNAME, null);

        int plano_id = getIntent().getIntExtra("PLANO_ID", 0);

        SingletonGestorApp.getInstance(getApplicationContext()).setExerciciosPlanoListener(this);
        SingletonGestorApp.getInstance(getApplicationContext()).getExerciciosPlanoAPI(getApplicationContext(), token, plano_id);

        // Para selecionar um exercicio da lista
        lvExercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetalhesExercicioActivity.class);
                intent.putExtra("ID_EXERCICIO_PLANO", (int) id);
                startActivityForResult(intent, 1);
            }
        });

        /*fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesLivroActivity.class);
                startActivityForResult(intent, ACT_DETALHES);
            }
        )};*/



    }

    @Override
    public void onRefreshListaExercicios(ArrayList<Exercicio> listaExercicios) {
        if(listaExercicios != null){
            lvExercicios.setAdapter(new ListaExerciciosAdaptador(getApplicationContext(), listaExercicios));
        }
    }
}