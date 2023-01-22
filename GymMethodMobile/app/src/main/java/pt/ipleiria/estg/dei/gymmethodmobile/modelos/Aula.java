package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Aula
{
    public static ArrayList<Aula> aulasList = new ArrayList<>();

    public static ArrayList<Aula> eventsForDate(LocalDate date)
    {
        ArrayList<Aula> aulas = new ArrayList<>();

        for(Aula aula : aulasList)
        {
            if(aula.getData().equals(date))
                aulas.add(aula);
        }

        return aulas;
    }


    private int id;
    private String nome, status;
    private LocalDate data;
    private LocalTime inicio, fim;


    public Aula(int id, String nome, LocalDate data, LocalTime inicio, LocalTime fim, String status)
    {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public LocalDate getData()
    {
        return data;
    }

    public void setData(LocalDate data)
    {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }
}
