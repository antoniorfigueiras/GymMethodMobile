package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.LoginListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PlanosListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    // Declarar variaveis
    private EditText etUsername, etPassword;
    private final int MIN_PASS = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SingletonGestorApp.getInstance(getApplicationContext()).setLoginListener(this);

        // Atribuir as editText ás variaveis para poder acessar
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }

    // validar o email e o login e apresentar o resultado da validação num Toast.
    public void onClickLogin(View view) {

        // Ir buscar o email e password introduzidos
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // se o email for valido
        if (!isUsernameValid(username)) {
            etUsername.setText(R.string.txt_username_invalido);
            return;
        }
        // se a password for valida
        if (!isPasswordValid(password)) {
            etPassword.setError(getString(R.string.txt_password_invalida));
            return;
        }
        // Criar o intent para iniciar uma atividade e passar como parametro o email
         //Indicar a atividade Pai e a atividade a iniciar
        /*Intent intent = new Intent(this, MenuMainActivity.class);
        startActivity(intent);
          finish();*/
        // Para terminar a atividade currente

        SingletonGestorApp.getInstance(getApplicationContext()).loginAPI(username,password,getApplicationContext());

    }

    // Verifica se o email introduzido é válido
    private boolean isUsernameValid(String username)
    {
        if(username == null)
            return false;
        return true;

    }

    // Verifica se a password introduzida é válida
    private boolean isPasswordValid(String pass)
    {
        if(pass==null)
            return false;
        return pass.length()>=MIN_PASS; // verifica se tem no minimo 8 caracteres (true)
    }
    // Iniciar atividade do menu depois do login
    @Override
    public void onValidateLogin(String token, String username, Context context) {
        if (token!=null)
        {
            Intent intent = new Intent(this, MenuMainActivity.class);
            intent.putExtra(MenuMainActivity.USERNAME, username);
            intent.putExtra(MenuMainActivity.TOKEN, token);
            startActivity(intent);
            finish();
        }else
        {
            Toast.makeText(getApplicationContext(),"Erro login",Toast.LENGTH_LONG).show();
        }

    }
}