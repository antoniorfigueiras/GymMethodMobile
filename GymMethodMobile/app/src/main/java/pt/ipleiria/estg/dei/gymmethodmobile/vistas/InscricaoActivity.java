package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.InscricoesListener;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.AulaInscrita;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.DetalhesExercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;

public class InscricaoActivity extends AppCompatActivity implements InscricoesListener {
    private FragmentManager fragmentManager;
    private String token;
    private Aula aula;
    private int action;
    private AulaInscrita aulaInscrita;
    private int idInscricao;
    private TextView tvData, tvHoraInicio, tvHoraFim, tvStatus, tvInscricaoTitulo;
    private Button btnInscrever, btnRemoverInscricao;
    private ArrayList<AulaInscrita> inscricoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao);
        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
        setTitle("Detalhes");
        initWidgets();
        setWidgets();
        SingletonGestorApp.getInstance(getApplicationContext()).setInscricoesListener(this);
        SingletonGestorApp.getInstance(getApplicationContext()).getAulasInscritas(getApplicationContext(), token);
    }

    private void initWidgets()
    {
        action = getIntent().getIntExtra("ACTION", 0);
        aula = (Aula) getIntent().getSerializableExtra("AULA");
        aulaInscrita = (AulaInscrita) getIntent().getSerializableExtra("AULA_INSCRITA");
        tvData = findViewById(R.id.tvData);
        tvHoraInicio = findViewById(R.id.tvHoraInicio);
        tvHoraFim = findViewById(R.id.tvHoraFim);
        tvStatus = findViewById(R.id.tvStatus);
        tvInscricaoTitulo = findViewById(R.id.tvInscricaoTitulo);
        btnInscrever = findViewById(R.id.btnInscrever);
        btnRemoverInscricao = findViewById(R.id.btnRemoverInscricao);
    }

    private void setWidgets()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (aula != null){
            tvData.setText(aula.getData().toString());
            tvHoraInicio.setText(formatter.format(aula.getInicio()));
            tvHoraFim.setText(formatter.format(aula.getFim()));
            tvStatus.setText(aula.getStatus());
            tvInscricaoTitulo.setText(aula.getNome());
        }else {
            tvData.setText(aulaInscrita.getData().toString());
            tvHoraInicio.setText(formatter.format(aulaInscrita.getInicio()));
            tvHoraFim.setText(formatter.format(aulaInscrita.getFim()));
            tvStatus.setText(aulaInscrita.getStatus());
            tvInscricaoTitulo.setText(aulaInscrita.getNome());
        }

    }

    public void onClickInscrever(View view) {
        SingletonGestorApp.getInstance(getApplicationContext()).inscreverAulaAPI(getApplicationContext(), token, aula.getId());

    }

    public void onClickRemoverInscricao(View view) {
        SingletonGestorApp.getInstance(getApplicationContext()).removerInscricaoAulaAPI(getApplicationContext(), token, idInscricao, action);

    }

    @Override
    public void onGetInscricoes(ArrayList<AulaInscrita> aulasInscritas) {
        if (aulasInscritas != null)
        {
            inscricoes = aulasInscritas;
            if (aulaInscrita == null){
                for (AulaInscrita a : inscricoes) {
                    if (a.getId() == aula.getId())
                    {
                        idInscricao = a.getInscricao_id();
                        btnInscrever.setVisibility(View.GONE);
                        btnRemoverInscricao.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                btnInscrever.setVisibility(View.VISIBLE);
                btnRemoverInscricao.setVisibility(View.GONE);
            }else {

                idInscricao = aulaInscrita.getInscricao_id();
                btnInscrever.setVisibility(View.GONE);
                btnRemoverInscricao.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onInscrever(int id) {
        if (id != 0){
            Toast.makeText(getApplicationContext(),"Inscrito na aula com sucesso!",Toast.LENGTH_LONG).show();
            btnInscrever.setVisibility(View.GONE);
            btnRemoverInscricao.setVisibility(View.VISIBLE);
            idInscricao = id;
        }
        else {
            Toast.makeText(getApplicationContext(),"Erro ao inscrever na aula!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRemoverInscricao(int action, Boolean success) {

        if (success){
            if (action == 1){
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Inscrição removida com sucesso!",Toast.LENGTH_LONG).show();
                btnInscrever.setVisibility(View.VISIBLE);
                btnRemoverInscricao.setVisibility(View.GONE);
            }
        }else {
            Toast.makeText(getApplicationContext(),"Erro ao remover inscricao!",Toast.LENGTH_LONG).show();
        }
    }
}