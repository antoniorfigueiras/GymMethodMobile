package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Inscricao implements Serializable
{
    private int id, inscricao_id;
    private String nome, status;
    private LocalDate data;
    private LocalTime inicio, fim;


    public Inscricao(int id, int inscricao_id, String nome, LocalDate data, LocalTime inicio, LocalTime fim, String status)
    {
        this.id = id;
        this.inscricao_id = inscricao_id;
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

    public int getInscricao_id() {
        return inscricao_id;
    }

    public void setInscricao_id(int inscricao_id) {
        this.inscricao_id = inscricao_id;
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
