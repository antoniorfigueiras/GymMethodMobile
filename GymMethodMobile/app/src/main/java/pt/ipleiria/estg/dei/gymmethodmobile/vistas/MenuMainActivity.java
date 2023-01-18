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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        private NavigationView navigationView;
        private DrawerLayout drawer;
        private Integer user_id;
        private String token;
        private String username;
        private FragmentManager fragmentManager;
        public static final String SHARED_USER="DADOS_USER"; // CHAVE
        public static final String USER_ID="ID"; // ID
        public static final String USERNAME="USER"; // NOME
        public static final String OPERACAO="OPERACAO";
        public static final String PASSWORD="PASSWORD";
        public static final String TOKEN="TOKEN";
        public static final int ADD=10, EDIT=20, DELETE=30;

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
            MenuItem item = menu.getItem(1);
            item.setChecked(true);
            return onNavigationItemSelected(item);
        }

        private void carregarCabecalho() {
            user_id = getIntent().getIntExtra(USER_ID, 0);
            username = getIntent().getStringExtra(USERNAME);
            token= getIntent().getStringExtra(TOKEN);
            SharedPreferences infoUser = getSharedPreferences(SHARED_USER, Context.MODE_PRIVATE);

            if(username!=null && token!=null)  {
                SharedPreferences.Editor editor =infoUser.edit();
                editor.putInt(USER_ID, user_id);
                editor.putString(USERNAME, username);
                editor.putString(TOKEN, token);
                editor.apply();
            }

            else
                username=infoUser.getString(USERNAME, getString(R.string.default_email));

            if (username != null){
                View headerView = navigationView.getHeaderView(0);
                TextView tvEmail = headerView.findViewById(R.id.tvMainEmail); // Para ir buscar ao cabeçalho do navigation view
                tvEmail.setText(username);
            }

        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item .getItemId()) {
                case R.id.planos:

                    fragment = new ListaPlanosFragment();
                    setTitle(item.getTitle());
                    break;

            }
            if (fragment != null)
                fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


}
