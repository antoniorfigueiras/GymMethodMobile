package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.DetalhesExercicioListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.DetalhesExercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.ParameterizacaoCliente;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class DetalhesExercicioActivity extends AppCompatActivity implements DetalhesExercicioListener {
    private int id;
    private DetalhesExercicio detalhes;
    private ParameterizacaoCliente parameterizacaoCliente;
    private String token;
    private TextView tvNome, tvDescricao, tvEquipamento, tvSeries, tvRepeticoes, tvPeso, tvTempo;
    private EditText etSeriesCliente, etRepeticoesCliente, etPesoCliente, etTempoCLiente;
    private ImageView imgExemlo;
    private FloatingActionButton fabGuardar;


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

        // Float Action  Button
        fabGuardar=findViewById(R.id.fabGuardar);


        SingletonGestorApp.getInstance(getApplicationContext()).getExercicioDetalhesAPI(getApplicationContext(), token, id);
        SingletonGestorApp.getInstance(getApplicationContext()).getParameterizacaoClienteAPI(getApplicationContext(), token, id);


       fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (isParameterizacaoValido()) {

                    parameterizacaoCliente.setPesoCliente(Integer.parseInt(etPesoCliente.getText().toString()));
                    parameterizacaoCliente.setRepeticoesCliente(Integer.parseInt(etRepeticoesCliente.getText().toString()));
                    parameterizacaoCliente.setSeriesCliente(Integer.parseInt(etSeriesCliente.getText().toString()));
                    parameterizacaoCliente.setTempoCliente(etTempoCLiente.getText().toString());
                    SingletonGestorApp.getInstance(getApplicationContext()).atualizarParameterizacaoCliente(getApplicationContext(), token, parameterizacaoCliente);

                }
            //}

        });

    }

    /*private boolean isParameterizacaoValido() {
        String peso = etPesoCliente.getText().toString();
        String repeticoes = etRepeticoesCliente.getText().toString();
        String series = etSeriesCliente.getText().toString();
        String tempo = etTempoCLiente.getText().toString();


        if (peso.length()<1){
            etPesoCliente.setError("Serie invalida");
            return false;
        }
        if (repeticoes.length()<=MIN_CHAR){
            etRepeticoesCliente.setError("Autor invalido");
            return false;
        }
        if (series.length() != MIN_NUMERO){
            etSeriesCliente.setError("Ano invalido");
            return false;
        }
        return true;
    }*/



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(livro!=null){
            getMenuInflater().inflate(R.menu.menu_detalhes_livro,menu);
            return super.onCreateOptionsMenu(menu);
        }
            return false;
    }*/

    /*private void carregarDetalhes() {

        setTitle(detalhes.getNome());
        tvNome.setText(detalhes.getNome());

        /*Bitmap bm = StringToBitMap(detalhes.getExemplo());
        imgExemlo.setImageBitmap(bm);
    }*/

    @Override
    public void onSetDetalhes(DetalhesExercicio detalhesExercicio, ParameterizacaoCliente parameterizacao) {
        if (detalhesExercicio != null){
            setTitle(detalhesExercicio.getNome());
            tvDescricao.setText(detalhesExercicio.getDescricao());
            tvEquipamento.setText(detalhesExercicio.getEquipamento());
            tvSeries.setText(detalhesExercicio.getSeries()+"");
            tvRepeticoes.setText(detalhesExercicio.getRepeticoes()+"");
            tvPeso.setText(detalhesExercicio.getPeso()+"");
            tvTempo.setText(detalhesExercicio.getTempo());

            Bitmap bm = StringToBitMap(detalhesExercicio.getExemplo());
            imgExemlo.setImageBitmap(bm);
        }else if (parameterizacao != null)
        {
            etPesoCliente.setText(parameterizacao.getPesoCliente()+"");
            etRepeticoesCliente.setText(parameterizacao.getRepeticoesCliente()+"");
            etSeriesCliente.setText(parameterizacao.getSeriesCliente()+"");
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
}