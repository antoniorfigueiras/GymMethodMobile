package pt.ipleiria.estg.dei.gymmethodmobile.modelos;


public class Consulta {

    private int id;
    private String nome, descricao, data, nutricionista;

    public Consulta(int id, String nome, String data, String nutricionista) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.nutricionista = nutricionista;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(String nutricionista) {
        this.nutricionista = nutricionista;
    }
}
