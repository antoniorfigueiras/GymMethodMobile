package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Consulta;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.DetalhesExercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.ParameterizacaoCliente;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;


public class ConsultaJsonParser {

    public static ArrayList<Consulta> parserJsonConsultas(JSONArray response) {
        ArrayList<Consulta> consultas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject consulta = (JSONObject) response.get(i);
                int id = consulta.getInt("id");
                String nome = consulta.getString("nome");
                String data = consulta.getString("data");
                String nutricionista = consulta.getString("nutricionista");

                Consulta auxConsulta = new Consulta(id, nome, data, nutricionista);
                consultas.add(auxConsulta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return consultas;
    }
}
