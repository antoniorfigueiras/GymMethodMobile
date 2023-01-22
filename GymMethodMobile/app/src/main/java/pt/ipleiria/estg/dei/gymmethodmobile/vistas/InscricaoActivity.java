package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import pt.ipleiria.estg.dei.gymmethodmobile.R;

public class InscricaoActivity extends AppCompatActivity {

    private int aula_id;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao);

        aula_id = getIntent().getIntExtra("ID_AULA", 0);
        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
    }
}