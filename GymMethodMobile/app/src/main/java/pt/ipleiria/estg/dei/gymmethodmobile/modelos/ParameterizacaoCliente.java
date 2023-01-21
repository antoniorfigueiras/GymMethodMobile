package pt.ipleiria.estg.dei.gymmethodmobile.modelos;


public class ParameterizacaoCliente {

    private int id, exercicio_plano_id, seriesCliente, repeticoesCliente, pesoCliente;
    private String tempoCliente;


    public ParameterizacaoCliente(int id, int exercicio_plano_id, int seriesCliente, int repeticoesCliente, int pesoCliente, String tempoCliente) {
        this.id = id;
        this.exercicio_plano_id = exercicio_plano_id;
        this.seriesCliente = seriesCliente;
        this.repeticoesCliente = repeticoesCliente;
        this.pesoCliente = pesoCliente;
        this.tempoCliente = tempoCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExercicio_plano_id() {
        return exercicio_plano_id;
    }

    public void setExercicio_plano_id(int exercicio_plano_id) {
        this.exercicio_plano_id = exercicio_plano_id;
    }

    public int getSeriesCliente() {
        return seriesCliente;
    }

    public void setSeriesCliente(int seriesCliente) {
        this.seriesCliente = seriesCliente;
    }

    public int getRepeticoesCliente() {
        return repeticoesCliente;
    }

    public void setRepeticoesCliente(int repeticoesCliente) {
        this.repeticoesCliente = repeticoesCliente;
    }

    public int getPesoCliente() {
        return pesoCliente;
    }

    public void setPesoCliente(int pesoCliente) {
        this.pesoCliente = pesoCliente;
    }

    public String getTempoCliente() {
        return tempoCliente;
    }

    public void setTempoCliente(String tempoCliente) {
        this.tempoCliente = tempoCliente;
    }
}
