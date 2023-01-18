package pt.ipleiria.estg.dei.gymmethodmobile.modelos;


import java.sql.Blob;
import java.sql.Time;

public class DetalhesExercicio {

    private int exercicio_plano_id, series, repeticoes, peso;
    private String nome, equipamento, descricao, dificuldade;
    private Blob exemplo;
    private Time tempo;

    public DetalhesExercicio(int exercicio_plano_id, int series, int repeticoes, int peso, String nome, String equipamento, String descricao, String dificuldade, Blob exemplo, Time tempo) {
        this.exercicio_plano_id = exercicio_plano_id;
        this.series = series;
        this.repeticoes = repeticoes;
        this.peso = peso;
        this.nome = nome;
        this.equipamento = equipamento;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.exemplo = exemplo;
        this.tempo = tempo;
    }

    public int getExercicio_plano_id() {
        return exercicio_plano_id;
    }

    public void setExercicio_plano_id(int exercicio_plano_id) {
        this.exercicio_plano_id = exercicio_plano_id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Blob getExemplo() {
        return exemplo;
    }

    public void setExemplo(Blob exemplo) {
        this.exemplo = exemplo;
    }

    public Time getTempo() {
        return tempo;
    }

    public void setTempo(Time tempo) {
        this.tempo = tempo;
    }
}
