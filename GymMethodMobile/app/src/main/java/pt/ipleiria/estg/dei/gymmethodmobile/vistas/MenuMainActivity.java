package pt.ipleiria.estg.dei.gymmethodmobile.vistas;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.JsonParser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public NavigationView navigationView;
    private DrawerLayout drawer;
    private Integer user_id;
    private String token;
    private String username, image;
    private FragmentManager fragmentManager;
    public static final String SHARED_USER = "DADOS_USER"; // CHAVE
    public static final String USERNAME = "USER"; // NOME
    public static final String IMAGE = "IMAGE"; // NOME
    public static final String OPERACAO = "OPERACAO";
    public static final String PASSWORD = "PASSWORD";
    public static final String TOKEN = "TOKEN";
    public static final int ADD = 10, EDIT = 20, DELETE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

        carregarCabecalho();

        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        carregarFragmentoInicial();
    }


    private boolean carregarFragmentoInicial() {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        username = getIntent().getStringExtra(USERNAME);
        token = getIntent().getStringExtra(TOKEN);
        image = getIntent().getStringExtra(IMAGE);
        SharedPreferences infoUser = getSharedPreferences(SHARED_USER, Context.MODE_PRIVATE);

        if (username != null && token != null) {
            SharedPreferences.Editor editor = infoUser.edit();
            editor.putString(USERNAME, username);
            editor.putString(TOKEN, token);
            editor.apply();
        } else
            username = infoUser.getString(USERNAME, getString(R.string.default_email));

        if (username != null) {
            View headerView = navigationView.getHeaderView(0);
            TextView tvEmail = headerView.findViewById(R.id.tvMainEmail); // Para ir buscar ao cabeçalho do navigation view
            tvEmail.setText(username);
            ImageView imgPerfil = headerView.findViewById(R.id.imgFoto);
            Bitmap bm = StringToBitMap(image);
            imgPerfil.setImageBitmap(bm);
        }
    }
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {

            //Inicia o menu principal
            case R.id.menu:
                fragment = new MainFragment();
                setTitle("Gym Method");
                break;
            //Inicia os planos
            case R.id.planos:
                fragment = new ListaPlanosFragment();
                setTitle(item.getTitle());
                break;
            //Inicia o perfil
            case R.id.perfil:
                if (!JsonParser.isConnectionInternet(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                fragment = new PerfilFragment();
                setTitle(item.getTitle());}
                break;
            case R.id.consultas:
                if (!JsonParser.isConnectionInternet(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                fragment = new ListaConsultasFragment();
                setTitle(item.getTitle());}
                break;
            case R.id.logout:
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.historico_consultas:
                if (!JsonParser.isConnectionInternet(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                    fragment = new ListaHistoricoConsultasFragment();
                    setTitle(item.getTitle());}
                break;
            case R.id.aulas:
                if (!JsonParser.isConnectionInternet(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                startActivity(new Intent(this, WeeklyAulasActivity.class));}
                break;

            case R.id.aulasmarcadas:
                if (!JsonParser.isConnectionInternet(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                    fragment = new ListaAulasInscritasFragment();
                    setTitle(item.getTitle());}
                break;
            case R.id.agua:
                if (!JsonParser.isConnectionInternet(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(this, WeeklyAguaActivity.class));}
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
