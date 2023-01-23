package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Agua;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.ParameterizacaoCliente;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.CalendarUtils;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.JsonParser;

public class DetalhesAguaActivity extends AppCompatActivity
{
    private EditText etDescricao, etValor;
    private TextView tvData, tvHora;
    private String token;
    private LocalTime time;
    private Agua agua;
    private MenuItem itemEditar, itemGuardar, itemCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_agua);
        setTitle("Detalhes");
        initWidgets();
        setWidgets();
        disableAllEditText();
    }

    private void initWidgets()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
        agua = (Agua) getIntent().getSerializableExtra("AGUA");
        etDescricao = findViewById(R.id.etDescricao);
        etValor = findViewById(R.id.etValor);
        tvData = findViewById(R.id.tvData);
        tvHora = findViewById(R.id.tvHora);
    }

    private void setWidgets()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        etDescricao.setText(agua.getDescricao());
        etValor.setText(agua.getValor().toString());
        tvData.setText(agua.getData().toString());
        tvHora.setText(formatter.format(agua.getHora()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes_agua, menu);
        itemEditar = menu.findItem(R.id.itemEditar);
        itemGuardar = menu.findItem(R.id.itemGuardar);
        itemCancelar = menu.findItem(R.id.itemCancelar);
        itemGuardar.setVisible(false);
        itemCancelar.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemEditar:
                if (!JsonParser.isConnectionInternet(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Sem ligação á internet", Toast.LENGTH_LONG).show();
                    return false;
                }else {
                    setTitle("Editar Registo");
                    enableAllEditText();
                    item.setVisible(false);
                    itemGuardar.setVisible(true);
                    itemCancelar.setVisible(true);
                }

                return true;

            case R.id.itemGuardar:
                setTitle("Detalhes");
                agua.setDescricao(etDescricao.getText().toString());
                agua.setValor(Float.valueOf(etValor.getText().toString()));
                SingletonGestorApp.getInstance(getApplicationContext()).editarRegistoAguaAPI(getApplicationContext(), token, agua);
                disableAllEditText();
                Toast.makeText(getApplicationContext(), "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                item.setVisible(false);
                itemEditar.setVisible(true);
                itemCancelar.setVisible(false);
                return true;

            case R.id.itemCancelar:
                setTitle("Detalhes");
                itemGuardar.setVisible(false);
                item.setVisible(false);
                itemEditar.setVisible(true);
                etDescricao.setText(agua.getDescricao());
                etValor.setText(agua.getValor().toString());
                disableAllEditText();
                return true;

            case R.id.itemRemover:
                SingletonGestorApp.getInstance(getApplicationContext()).removerRegistoAguaAPI(getApplicationContext(), token, agua);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        // editText.setKeyListener(null);
        editText.setTextColor(Color.BLACK);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        //editText.setKeyListener(editText.getKeyListener());
        editText.setTextColor(Color.BLACK);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void disableAllEditText() {
        disableEditText(etDescricao);
        disableEditText(etValor);


    }

    private void enableAllEditText() {
        enableEditText(etDescricao);
        enableEditText(etValor);


    }

}