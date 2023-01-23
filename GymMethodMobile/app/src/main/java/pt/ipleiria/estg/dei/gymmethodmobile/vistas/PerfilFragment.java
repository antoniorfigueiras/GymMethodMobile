package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PerfilListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.User;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.JsonParser;

public class PerfilFragment extends Fragment implements PerfilListener {

    private User perfils;
    private String token;
    private FragmentManager fragmentManager;
    private TextView tvNome, tvAltura, tvPeso, tvTelemovel, tvNIF, tvMorada, tvCidade, tvCodPostal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);

        tvNome = view.findViewById(R.id.tvNome);
        tvCidade = view.findViewById(R.id.tvCidade);
        tvTelemovel = view.findViewById(R.id.tvTelemovel);
        tvPeso = view.findViewById(R.id.tvPesos);
        tvAltura = view.findViewById(R.id.tvAltura);
        tvNIF = view.findViewById(R.id.tvNIF);
        tvMorada = view.findViewById(R.id.tvMorada);
        tvCodPostal = view.findViewById(R.id.tvCodPostal);
        ImageView editar = view.findViewById(R.id.editar);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


        //Inicia o editar perfil
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!JsonParser.isConnectionInternet(getContext())) {
                    Toast.makeText(getContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contentFragment, new EditPerfilFragment());
                    fr.commit();
                }
            }
        });
        SingletonGestorApp.getInstance(getContext()).setPerfilListener(this);
        SingletonGestorApp.getInstance(getContext()).getPerfilAPI(getContext(), token);

        return view;
    }

    //Mostra o perfil com os dados do cliente
    @Override
    public void onShowPerfil(User perfil) {
        if (perfil != null) {
            tvNome.setText(perfil.getNomeproprio() + " " + perfil.getApelido());
            tvCidade.setText(perfil.getCidade() + ", " + perfil.getPais());
            tvAltura.setText(String.valueOf(perfil.getAltura()));
            tvPeso.setText(String.valueOf(perfil.getPeso()));
            tvTelemovel.setText(String.valueOf(perfil.getTelemovel()));
            tvNIF.setText(String.valueOf(perfil.getNif()));
            tvMorada.setText(perfil.getMorada());
            tvCodPostal.setText(perfil.getCodpostal());
            perfils = perfil;
        }
    }
}