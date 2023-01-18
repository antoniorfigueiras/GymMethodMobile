package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.DetalhesExercicioListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class DetalhesExercicioActivity extends AppCompatActivity implements DetalhesExercicioListener {

    private Exercicio exercicio;
    private String token;
    private EditText etTitulo, etSerie, etData, etAutor;
    private ImageView imgCapa;
    private FloatingActionButton fabGuardar;
    public static final int MIN_CHAR = 3, MIN_NUMERO=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_exercicio);

        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);

        int id=getIntent().getIntExtra("ID_LIVRO", 0);
        livro= SingletonGestorApp.getInstance(this).getExercicio(id);

        etTitulo=findViewById(R.id.etTitulo);
        etSerie=findViewById(R.id.etSerie);
        etData=findViewById(R.id.etData);
        etAutor=findViewById(R.id.etAutor);
        imgCapa=findViewById(R.id.imgCapa);
        fabGuardar=findViewById(R.id.fabGuardar);

        SingletonGestorApp.getInstance(getApplicationContext()).setDetalhesListener(this);

        if(livro != null){
            carregarLivro();
            fabGuardar.setImageResource(R.drawable.ic_action_guardar);
        }
        else {
            setTitle("Adicionar Livro");
            fabGuardar.setImageResource(R.drawable.ic_action_adicionar);

        }

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLivroValido()) {
                    if (livro != null && token!=null) {
                    livro.setTitulo(etTitulo.getText().toString());
                        livro.setAutor(etAutor.getText().toString());
                        livro.setSerie(etSerie.getText().toString());
                        livro.setAno(Integer.parseInt(etData.getText().toString()));
                        SingletonGestorLivros.getInstance(getApplicationContext()).editarLivroAPI(livro, getApplicationContext(), token);
                    } else {
                        Livro livroAux = new Livro(0, Integer.parseInt(etData.getText().toString()),
                                "http://amsi.dei.estg.ipleiria.pt/img/ipl_semfundo.png",
                                etTitulo.getText().toString(), etSerie.getText().toString(),
                                etAutor.getText().toString());
                        SingletonGestorLivros.getInstance(getApplicationContext()).adicionarLivroAPI(livroAux, getApplicationContext(), token);
                    }
                }
            }

        });

    }

    private boolean isLivroValido() {
        String titulo = etTitulo.getText().toString();
        String autor = etAutor.getText().toString();
        String serie = etSerie.getText().toString();
        String ano = etData.getText().toString();


        if (titulo.length()<MIN_CHAR){
            etTitulo.setError("Serie invalida");
            return false;
        }
        if (autor.length()<=MIN_CHAR){
            etAutor.setError("Autor invalido");
            return false;
        }
        if (ano.length() != MIN_NUMERO){
            etData.setError("Ano invalido");
            return false;
        }else {
            int anoNumero = Integer.parseInt(ano);
            if(anoNumero < 1900 || anoNumero > Calendar.getInstance().get(Calendar.YEAR)){
                etData.setError("Ano Invalido");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemRemvover:
                dialogRemover();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover Livro")
                .setMessage("Tem a certeza que pretende remover o livro?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SingletonGestorLivros.getInstance(getApplicationContext()).removerLivroAPI(livro, getApplicationContext());

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Nao fazer nada
                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(livro!=null){
            getMenuInflater().inflate(R.menu.menu_detalhes_livro,menu);
            return super.onCreateOptionsMenu(menu);
        }
            return false;
    }

    private void carregarLivro() {
        Resources res=getResources();
        String titulo = String.format(res.getString(R.string.act_titulo), livro.getTitulo());
        setTitle(titulo);
        etTitulo.setText(livro.getTitulo());
        etSerie.setText(livro.getSerie());
        etAutor.setText(livro.getAutor());
        etData.setText(livro.getAno()+"");
        //imgCapa.setImageResource(livro.getCapa());
     Glide.with(this)
                .load(livro.getCapa())
                .placeholder(R.drawable.logoipl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgCapa);
    }

    @Override
    public void onRefreshDetalhes(int operacao) {
        Intent intent = new Intent();
        intent.putExtra(MenuMainActivity.OPERACAO, operacao);
        setResult(RESULT_OK, intent);
        finish();
    }
}