package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

public class User {
    private int user_id, telemovel, altura, nif;
    private double peso;
    private String nomeproprio, apelido, codpostal, pais, cidade, morada;


    public User(int user_id, int telemovel, int altura, int nif, double peso, String nomeproprio, String apelido, String codpostal, String pais, String cidade, String morada) {
        this.user_id = user_id;
        this.telemovel = telemovel;
        this.altura = altura;
        this.nif = nif;
        this.peso = peso;
        this.nomeproprio = nomeproprio;
        this.apelido = apelido;
        this.codpostal = codpostal;
        this.pais = pais;
        this.cidade = cidade;
        this.morada = morada;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(int telemovel) {
        this.telemovel = telemovel;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getNomeproprio() {
        return nomeproprio;
    }

    public void setNomeproprio(String nomeproprio) {
        this.nomeproprio = nomeproprio;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getCodpostal() {
        return codpostal;
    }

    public void setCodpostal(String codpostal) {
        this.codpostal = codpostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }


}
