package pt.ipleiria.estg.dei.gymmethodmobile.vistas;

import static pt.ipleiria.estg.dei.gymmethodmobile.utils.CalendarUtils.daysInWeekArray;
import static pt.ipleiria.estg.dei.gymmethodmobile.utils.CalendarUtils.monthYearFromDate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.R;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.AguaAdapter;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.CalendarAdapter;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.CalendarAguaAdapter;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Agua;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.CalendarUtils;

public class WeeklyAguaActivity extends AppCompatActivity implements CalendarAguaAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView lvAgua;
    private String token;
    private  ArrayList<Agua> dailyAguas;
    private Agua agua;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_agua);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        setTitle("Agua");

        SingletonGestorApp.getInstance(getApplicationContext()).setOnItemListenerAgua(this);
        SingletonGestorApp.getInstance(getApplicationContext()).getAguasAPI(getApplicationContext(), token);

        lvAgua.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                agua = (Agua) lvAgua.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DetalhesAguaActivity.class);
                intent.putExtra("AGUA", agua);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initWidgets()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MenuMainActivity.SHARED_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, null);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        lvAgua = findViewById(R.id.lvAguas);
    }


    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAguaAdapter calendarAguaAdapter = new CalendarAguaAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAguaAdapter);
        setAguaAdpater();
    }

    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    // Selecionar dia da semana
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        // Definir a data selecionada na variavel do modelo
        CalendarUtils.selectedDate = date;
        setWeekView();
    }



    // Atualizar depois de inserir evento
    @Override
    protected void onResume()
    {
        super.onResume();
        setAguaAdpater();
    }

    private void setAguaAdpater()
    {
        dailyAguas = Agua.aguasDataSelecionada(CalendarUtils.selectedDate);
        AguaAdapter aguaAdapter = new AguaAdapter(getApplicationContext(), dailyAguas);
        lvAgua.setAdapter(aguaAdapter);
    }

    public void novoRegisto(View view)
    {
        startActivity(new Intent(this, AguaActivity.class));
    }

    @Override
    public void onSetAguas(ArrayList<Agua> listaAguas) {
        if (listaAguas!=null){
            dailyAguas = listaAguas;
            setAguaAdpater();
        }
    }
}








