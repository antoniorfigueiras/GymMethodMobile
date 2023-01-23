package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.JsonParser;

public class MainFragment extends Fragment {

    private String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);

        CardView perfil = view.findViewById(R.id.card_perfil);
        CardView plano = view.findViewById(R.id.card_planos);
        CardView aula = view.findViewById(R.id.card_aulas);
        CardView consulta = view.findViewById(R.id.card_consultas);
        CardView agua = view.findViewById(R.id.card_agua);

        //Inicia o perfil
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!JsonParser.isConnectionInternet(getContext())) {
                    Toast.makeText(getContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contentFragment, new PerfilFragment());
                    fr.commit();
                }
            }
        });

        //Inicia os planos
        plano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.contentFragment, new ListaPlanosFragment());
                fr.commit();
            }
        });

        //Inicia as aulas
        aula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!JsonParser.isConnectionInternet(getContext())) {
                    Toast.makeText(getContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getContext(), WeeklyAulasActivity.class));
                }
            }
        });

        //Inicia as consultas
        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!JsonParser.isConnectionInternet(getContext())) {
                    Toast.makeText(getContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contentFragment, new ListaConsultasFragment());
                    fr.commit();
                }
            }
        });

        //Inicia a agua
        agua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}