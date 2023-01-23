package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Agua implements Serializable
{
    public static ArrayList<Agua> aguasList = new ArrayList<>();

    public static ArrayList<Agua> aguasDataSelecionada(LocalDate date)
    {
        ArrayList<Agua> aguas = new ArrayList<>();

        for(Agua agua : aguasList)
        {
            if(agua.getData().equals(date))
                aguas.add(agua);
        }

        return aguas;
    }

    public static void aguaEditada(Agua agua)
    {
        for(Agua aguas : aguasList)
        {
            if(aguas.getId() == agua.id)
                aguas.setDescricao(agua.getDescricao());
                aguas.setValor(agua.getValor());
        }
    }
    public static void removerAgua(Agua agua)
    {
        for(int i = aguasList.size()-1 ; i >= 0; i--){
            if (aguasList.get(i).getId() == agua.id)
            {
                aguasList.remove(aguasList.get(i));
            }
        }
    }



    private int id;
    private String descricao;
    private Float valor;
    private LocalDate data;
    private LocalTime hora;

    public Agua(int id, String descricao, Float valor, LocalDate data, LocalTime hora) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
