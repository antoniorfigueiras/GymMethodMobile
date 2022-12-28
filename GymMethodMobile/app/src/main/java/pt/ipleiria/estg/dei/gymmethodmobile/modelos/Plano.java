package pt.ipleiria.estg.dei.gymmethodmobile.modelos;


public class Plano {

    private int id;
    private String nome, treinador;

    public Plano(int id, String nome, String treinador) {
        this.id = id;
        this.nome = nome;
        this.treinador = treinador;

    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getTreinador() {return treinador;}

    public void setTreinador(String treinador) {this.treinador = treinador;}
}
