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
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.CalendarAdapter;
import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.AulasAdapter;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.SingletonGestorApp;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.CalendarUtils;

public class WeeklyAulasActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView lvAulas;
    private  ArrayList<Aula> dailyAulas;
    private String token;
    private Aula aula;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_aulas_view);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();

        SingletonGestorApp.getInstance(getApplicationContext()).setOnItemListener(this);
        SingletonGestorApp.getInstance(getApplicationContext()).getAulasAPI(getApplicationContext(), token);

        lvAulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                aula = (Aula) lvAulas.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), InscricaoActivity.class);
                intent.putExtra("AULA", aula);
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
        lvAulas = findViewById(R.id.lvAulas);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
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
        setEventAdpater();
    }

    private void setEventAdpater()
    {

        dailyAulas = Aula.eventsForDate(CalendarUtils.selectedDate);
        AulasAdapter eventAdapter = new AulasAdapter(getApplicationContext(), dailyAulas);
        lvAulas.setAdapter(eventAdapter);
    }

    @Override
    public void onSetAulas(ArrayList<Aula> listaAulas) {
        if (listaAulas!=null){
            dailyAulas = listaAulas;
        }
    }

}








