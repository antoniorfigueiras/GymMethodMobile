package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PerfilListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.User;

public class PerfilFragment extends Fragment implements PerfilListener {

    private User perfil;
    private String token;
    private EditText etNomeProprio, etApelido, etAltura, etPeso, etTelemovel, etNIF, etMorada, etCidade, etPais, etCodPostal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);

        SingletonGestorApp.getInstance(getContext()).setPerfilListener(this);
        SingletonGestorApp.getInstance(getContext()).getPerfilAPI(getContext(), token);
        etNomeProprio = view.findViewById(R.id.etNomeProprio);
        etApelido = view.findViewById(R.id.etApelido);
        etAltura = view.findViewById(R.id.etAltura);
        etPeso = view.findViewById(R.id.etPeso);
        etTelemovel = view.findViewById(R.id.etTelemovel);
        etNIF = view.findViewById(R.id.etNIF);
        etMorada = view.findViewById(R.id.etMorada);
        etCidade = view.findViewById(R.id.etCidade);
        etPais = view.findViewById(R.id.etPais);
        etCodPostal = view.findViewById(R.id.etCodPostal);
        //carregarPerfil();
        return view;
    }

    /*private void carregarPerfil() {
        etNomeProprio.setText(perfil.getNomeproprio());
    }*/

    @Override
    public void onShowPerfil(User perfil) {
        if (perfil != null) {
            etNomeProprio.setText(perfil.getNomeproprio());
        }
    }
}