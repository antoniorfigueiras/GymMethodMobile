package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.DetalhesExercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.ParameterizacaoCliente;



public class AulasJsonParser {

    public static ArrayList<Aula> parserJsonAulas(JSONArray response) {

        ArrayList<Aula> aulas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject aula = (JSONObject) response.get(i);
                int id = aula.getInt("id");
                String nome = aula.getString("nome");
                LocalDate data = LocalDate.parse(aula.getString("data"));
                LocalTime inicio = LocalTime.parse(aula.getString("inicio"));
                LocalTime fim = LocalTime.parse(aula.getString("fim"));
                String status = aula.getString("status");

                Aula auxAula = new Aula(id, nome,  data,  inicio,  fim,  status);
                aulas.add(auxAula);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aulas;
    }


}
