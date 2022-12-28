package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import pt.ipleiria.estg.dei.gymmethodmobile.R;

public class LoginActivity extends AppCompatActivity {

    // Declarar variaveis
    private EditText etUsername, etPassword;
    private final int MIN_PASS = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
         Intent intent = new Intent(this, MenuMainActivity.class);
         //Para enviar dados ao iniciar a atividade
        startActivity(intent);
          finish(); // Para terminar a atividade currente

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
}