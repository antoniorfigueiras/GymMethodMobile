package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;


public class PlanoJsonParser {

    public static ArrayList<Plano> parserJsonPlanos(JSONArray response) {
        ArrayList<Plano> planos = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject plano = (JSONObject) response.get(i);
                int id = plano.getInt("id");
                String nome = plano.getString("nome");
                String treinador = plano.getString("treinador");

                Plano auxPlano = new Plano(id, nome, treinador);
                planos.add(auxPlano);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return planos;
    }

    public static ArrayList<Exercicio> parserJsonExercicios(JSONArray response) {
        ArrayList<Exercicio> exercicios = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject exercicio = (JSONObject) response.get(i);
                int id = exercicio.getInt("id");
                int plano_id = exercicio.getInt("plano_id");
                String nome = exercicio.getString("nome");
                String equipamento = exercicio.getString("equipamento");
                String tipo_exercicio = exercicio.getString("tipo_exercicio");

                Exercicio auxExercicio = new Exercicio(id, plano_id, nome, equipamento, tipo_exercicio);
                exercicios.add(auxExercicio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exercicios;
    }

    /*public static Plano parserJsonPlano(String response) {
        Plano auxPlano = null;
        try {
            JSONObject plano = new JSONObject(response);
            int id = plano.getInt("id");
            String nome = plano.getString("nome");
            String treinador = plano.getString("treinador");
            auxPlano = new Plano(id, nome, treinador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPlano;
    }*/


}
