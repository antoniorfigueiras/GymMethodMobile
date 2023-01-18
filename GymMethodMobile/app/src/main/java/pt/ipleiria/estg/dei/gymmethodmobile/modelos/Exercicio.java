package pt.ipleiria.estg.dei.gymmethodmobile.modelos;


public class Exercicio {

    private int id, plano_id;
    private String nome, equipamento, tipo_exercicio;

    public Exercicio(int id, int plano_id, String nome, String equipamento, String tipo_exercicio) {
        this.id = id;
        this.plano_id = plano_id;
        this.nome = nome;
        this.equipamento = equipamento;
        this.tipo_exercicio = tipo_exercicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlano_id() {
        return plano_id;
    }

    public void setPlano_id(int plano_id) {
        this.plano_id = plano_id;
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

    public String getTipo_exercicio() {
        return tipo_exercicio;
    }

    public void setTipo_exercicio(String tipo_exercicio) {
        this.tipo_exercicio = tipo_exercicio;
    }
}
