package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PerfilListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.User;

public class EditPerfilFragment extends Fragment implements PerfilListener {

    private User perfils;
    private String token;
    private FloatingActionButton fabGuardar;
    public static final String SHARED_USER = "DADOS_USER"; // CHAVE
    private EditText etNomeProprio, etApelido, etAltura, etPeso, etTelemovel, etNIF, etMorada, etCidade, etPais, etCodPostal;
    public static final int MAX_CHAR = 55, MIN_CHAR = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_edit_perfil, container, false);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);


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
        fabGuardar = view.findViewById(R.id.fabGuardar);


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Caso o perfil seja válido vai atualizar os dados do perfil
                if (isPerfilValido()) {
                    perfils.setNomeproprio(etNomeProprio.getText().toString());
                    perfils.setApelido(etApelido.getText().toString());
                    perfils.setTelemovel(Integer.parseInt(etTelemovel.getText().toString()));
                    perfils.setNif(Integer.parseInt(etNIF.getText().toString()));
                    perfils.setAltura(Integer.parseInt(etAltura.getText().toString()));
                    perfils.setPeso(Double.parseDouble(etPeso.getText().toString()));
                    perfils.setMorada(etMorada.getText().toString());
                    perfils.setCodpostal(etCodPostal.getText().toString());
                    perfils.setCidade(etCidade.getText().toString());
                    perfils.setPais(etPais.getText().toString());


                    //atualizar o nome na barra
                    View headerView = ((MenuMainActivity) getActivity()).navigationView.getHeaderView(0);
                    TextView tvEmail = headerView.findViewById(R.id.tvMainEmail); // Para ir buscar ao cabeçalho do navigation view
                    tvEmail.setText(etNomeProprio.getText().toString() + " " + etApelido.getText().toString());

                    SingletonGestorApp.getInstance(getContext()).editPerfilAPI(perfils, getContext(), token);

                    //Volta ao perfil
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contentFragment, new PerfilFragment());
                    fr.commit();
                    //Fecha o teclado
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Toast.makeText(getContext(), "Perfil editado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Corrija os erros", Toast.LENGTH_SHORT).show();
                    //Fecha o teclado
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        SingletonGestorApp.getInstance(getContext()).setPerfilListener(this);
        SingletonGestorApp.getInstance(getContext()).getPerfilAPI(getContext(), token);

        return view;
    }

    //Introduz os dados automaticamente
    @Override
    public void onShowPerfil(User perfil) {
        if (perfil != null) {
            etNomeProprio.setText(perfil.getNomeproprio());
            etApelido.setText(perfil.getApelido());
            etAltura.setText(String.valueOf(perfil.getAltura()));
            etPeso.setText(String.valueOf(perfil.getPeso()));
            etTelemovel.setText(String.valueOf(perfil.getTelemovel()));
            etNIF.setText(String.valueOf(perfil.getNif()));
            etMorada.setText(perfil.getMorada());
            etCidade.setText(perfil.getCidade());
            etPais.setText(perfil.getPais());
            etCodPostal.setText(perfil.getCodpostal());
            perfils = perfil;
        }
    }

    //Verificar se o Perfil é válido caso seja envia true
    private boolean isPerfilValido() {
        String nomeProprio = etNomeProprio.getText().toString();
        String apelido = etApelido.getText().toString();
        int altura = Integer.parseInt(etAltura.getText().toString());
        double peso = Double.parseDouble(etPeso.getText().toString());
        int telemovel = Integer.parseInt(etTelemovel.getText().toString());
        int nif = Integer.parseInt(etNIF.getText().toString());
        String morada = etMorada.getText().toString();
        String cidade = etCidade.getText().toString();
        String pais = etPais.getText().toString();
        String codpostal = etCodPostal.getText().toString();


        if (nomeProprio.length() > MAX_CHAR || nomeProprio.length() < MIN_CHAR) {
            etNomeProprio.setError("Nome inválido");
            return false;
        }
        if (apelido.length() > MAX_CHAR || apelido.length() < MIN_CHAR) {
            etApelido.setError("Apelido Inválido");
            return false;
        }
        if (altura > 300) {
            etAltura.setError("Altura inválida");
            return false;
        }
        if (peso > 999) {
            etPeso.setError("Peso inválido");
            return false;
        }
        if (!(nif >= 100000000 && nif <= 999999999)) {
            etNIF.setError("NIF inválido");
            return false;
        }
        if (pais.length() > MAX_CHAR || pais.length() < MIN_CHAR) {
            etPais.setError("País Inválido");
            return false;
        }
        if (cidade.length() > MAX_CHAR || cidade.length() < MIN_CHAR) {
            etCidade.setError("Cidade Inválida");
            return false;
        }
        if (morada.length() > MAX_CHAR || morada.length() < MIN_CHAR) {
            etMorada.setError("Morada Inválida");
            return false;
        }
        if (codpostal.length() > 8) {
            etCodPostal.setError("Código Postal Inválido");
            return false;
        }
        if (telemovel < 100000000) {
            etTelemovel.setError("Telemóvel Inválido");
            return false;
        } else if (telemovel > 999999999) {
            etTelemovel.setError("Telemóvel Inválido");
            return false;
        }
        return true;
    }
}