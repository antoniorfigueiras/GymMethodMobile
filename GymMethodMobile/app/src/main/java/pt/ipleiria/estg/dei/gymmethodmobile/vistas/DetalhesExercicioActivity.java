package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.DetalhesExercicioListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.DetalhesExercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.ParameterizacaoCliente;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.JsonParser;

public class DetalhesExercicioActivity extends AppCompatActivity implements DetalhesExercicioListener {
    private int id;
    private DetalhesExercicio detalhes;
    private ParameterizacaoCliente parameterizacaoCliente;
    private String token;
    private TextView tvNome, tvDescricao, tvEquipamento, tvSeries, tvRepeticoes, tvPeso, tvTempo;
    private EditText etSeriesCliente, etRepeticoesCliente, etPesoCliente, etTempoCLiente;
    private ImageView imgExemlo;
    private Menu optionsMenu;
    private MenuItem itemEditar, itemGuardar, itemCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_exercicio);
        SingletonGestorApp.getInstance(getApplicationContext()).setDetalhesListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);

        id = getIntent().getIntExtra("ID_EXERCICIO_PLANO", 0);


        // Text View
        tvDescricao = findViewById(R.id.tvDescricao);
        tvEquipamento = findViewById(R.id.tvEquipamento);
        tvSeries = findViewById(R.id.tvSeries);
        tvRepeticoes = findViewById(R.id.tvRepeticoes);
        tvPeso = findViewById(R.id.tvPeso);
        tvTempo = findViewById(R.id.tvTempo);
        imgExemlo = findViewById(R.id.imgExemplo);

        // Edit Text
        etPesoCliente = findViewById(R.id.etPesoCliente);
        etRepeticoesCliente = findViewById(R.id.etRepeticoesCliente);
        etSeriesCliente = findViewById(R.id.etSeriesCliente);
        etTempoCLiente = findViewById(R.id.etTempoCLiente);

        SingletonGestorApp.getInstance(getApplicationContext()).getExercicioDetalhesAPI(getApplicationContext(), token, id);
        SingletonGestorApp.getInstance(getApplicationContext()).getParameterizacaoClienteAPI(getApplicationContext(), token, id);

        disableAllEditText();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhes_exercicio, menu);
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
                    setTitle("Editar: " + detalhes.getNome());
                    enableAllEditText();
                    item.setVisible(false);
                    itemGuardar.setVisible(true);
                    itemCancelar.setVisible(true);
                }

                return true;

            case R.id.itemGuardar:
                setTitle(detalhes.getNome());
                parameterizacaoCliente.setPesoCliente(Integer.parseInt(etPesoCliente.getText().toString()));
                parameterizacaoCliente.setRepeticoesCliente(Integer.parseInt(etRepeticoesCliente.getText().toString()));
                parameterizacaoCliente.setSeriesCliente(Integer.parseInt(etSeriesCliente.getText().toString()));
                parameterizacaoCliente.setTempoCliente(etTempoCLiente.getText().toString());
                SingletonGestorApp.getInstance(getApplicationContext()).atualizarParameterizacaoCliente(getApplicationContext(), token, parameterizacaoCliente);
                disableAllEditText();
                Toast.makeText(getApplicationContext(), "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                item.setVisible(false);
                itemEditar.setVisible(true);
                itemCancelar.setVisible(false);
                return true;

            case R.id.itemCancelar:
                setTitle(detalhes.getNome());
                itemGuardar.setVisible(false);
                item.setVisible(false);
                itemEditar.setVisible(true);
                disableAllEditText();
                etPesoCliente.setText(parameterizacaoCliente.getPesoCliente() + "");
                etRepeticoesCliente.setText(parameterizacaoCliente.getRepeticoesCliente() + "");
                etSeriesCliente.setText(parameterizacaoCliente.getSeriesCliente() + "");
                etTempoCLiente.setText(parameterizacaoCliente.getTempoCliente());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetDetalhes(DetalhesExercicio detalhesExercicio, ParameterizacaoCliente parameterizacao) {
        if (detalhesExercicio != null) {
            setTitle(detalhesExercicio.getNome());
            tvDescricao.setText(detalhesExercicio.getDescricao());
            tvEquipamento.setText(detalhesExercicio.getEquipamento());
            tvSeries.setText(detalhesExercicio.getSeries() + "");
            tvRepeticoes.setText(detalhesExercicio.getRepeticoes() + "");
            tvPeso.setText(detalhesExercicio.getPeso() + "");
            tvTempo.setText(detalhesExercicio.getTempo());

            Bitmap bm = StringToBitMap(detalhesExercicio.getExemplo());
            imgExemlo.setImageBitmap(bm);

            detalhes = detalhesExercicio;
        } else if (parameterizacao != null) {
            etPesoCliente.setText(parameterizacao.getPesoCliente() + "");
            etRepeticoesCliente.setText(parameterizacao.getRepeticoesCliente() + "");
            etSeriesCliente.setText(parameterizacao.getSeriesCliente() + "");
            etTempoCLiente.setText(parameterizacao.getTempoCliente());
            parameterizacaoCliente = parameterizacao;
        }
        //Toast.makeText(getApplicationContext(),"Erro ao carregar exercicio",Toast.LENGTH_LONG).show();
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
        disableEditText(etPesoCliente);
        disableEditText(etRepeticoesCliente);
        disableEditText(etSeriesCliente);
        disableEditText(etTempoCLiente);

    }

    private void enableAllEditText() {
        enableEditText(etPesoCliente);
        enableEditText(etRepeticoesCliente);
        enableEditText(etSeriesCliente);
        enableEditText(etTempoCLiente);

    }


}
