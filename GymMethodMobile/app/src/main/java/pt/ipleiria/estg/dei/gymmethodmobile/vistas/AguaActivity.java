package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Agua;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.CalendarUtils;

public class AguaActivity extends AppCompatActivity
{
    private EditText etDescricao, etValor;
    private TextView tvData, tvHora;
    private String token;
    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agua);
        setTitle("Novo Registo");
        initWidgets();
        time = LocalTime.now();
        tvData.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        tvHora.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
        etDescricao = findViewById(R.id.etDescricao);
        etValor = findViewById(R.id.etValor);
        tvData = findViewById(R.id.tvData);
        tvHora = findViewById(R.id.tvHora);
    }

    public void inserirRegisto(View view)
    {
        String descricao = etDescricao.getText().toString();
        Float valor = Float.valueOf(etValor.getText().toString());
        Agua novaAgua = new Agua(0, descricao, valor, CalendarUtils.selectedDate, time);
        //Event.eventsList.add(newEvent);
        SingletonGestorApp.getInstance(getApplicationContext()).adicionarAguaAPI(getApplicationContext(), token, novaAgua);
        finish();
    }
}