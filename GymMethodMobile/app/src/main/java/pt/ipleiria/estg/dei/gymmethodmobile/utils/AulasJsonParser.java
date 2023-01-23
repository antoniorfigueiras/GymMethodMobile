package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.AulaInscrita;
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

    public static ArrayList<AulaInscrita> parserJsonAulasInscritas(JSONArray response) {

        ArrayList<AulaInscrita> aulasInscritas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject aulaInscrita = (JSONObject) response.get(i);
                int id = aulaInscrita.getInt("id");
                int inscricao_id = aulaInscrita.getInt("inscricao_id");
                String nome = aulaInscrita.getString("nome");
                LocalDate data = LocalDate.parse(aulaInscrita.getString("data"));
                LocalTime inicio = LocalTime.parse(aulaInscrita.getString("inicio"));
                LocalTime fim = LocalTime.parse(aulaInscrita.getString("fim"));
                String status = aulaInscrita.getString("status");

                AulaInscrita auxAulaInscrita = new AulaInscrita(id, inscricao_id, nome,  data,  inicio,  fim,  status);
                aulasInscritas.add(auxAulaInscrita);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aulasInscritas;
    }


}
