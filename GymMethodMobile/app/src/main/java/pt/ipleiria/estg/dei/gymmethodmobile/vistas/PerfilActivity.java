package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.User;

public class PerfilActivity extends AppCompatActivity {

    private User perfil;
    private String token;
    private EditText etNomeProprio, etApelido, etAltura, etPeso, etTelemovel, etNIF, etMorada, etCidade, etPais, etCodPostal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
        SingletonGestorApp.getInstance(getApplicationContext()).getPerfilAPI(getApplicationContext(), token);
        etNomeProprio=findViewById(R.id.etNomeProprio);
        etApelido=findViewById(R.id.etApelido);
        etAltura=findViewById(R.id.etAltura);
        etPeso=findViewById(R.id.etPeso);
        etTelemovel=findViewById(R.id.etTelemovel);
        etNIF=findViewById(R.id.etNIF);
        etMorada=findViewById(R.id.etMorada);
        etCidade=findViewById(R.id.etCidade);
        etPais=findViewById(R.id.etPais);
        etCodPostal=findViewById(R.id.etCodPostal);

        if(perfil !=null)
        {
            carregarPerfil();
        }
    }

    private void carregarPerfil() {
        etNomeProprio.setText(perfil.getNomeproprio());
    }
}