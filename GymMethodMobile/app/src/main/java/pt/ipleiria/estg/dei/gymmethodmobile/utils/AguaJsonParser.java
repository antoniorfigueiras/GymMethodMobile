package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Agua;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Aula;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.AulaInscrita;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.User;


public class AguaJsonParser {

   /* public static Agua parserJsonAgua(String response) {

        Agua agua = null;
        try {

                JSONObject auxAgua = new JSONObject(response);
                int id = auxAgua.getInt("id");
                String descricao = auxAgua.getString("nome");
                float valor = (float) auxAgua.get("valor");
                LocalDate data = LocalDate.parse(auxAgua.getString("data"));
                LocalTime hora = LocalTime.parse(auxAgua.getString("hora"));
                agua = new Agua(id, descricao,  valor,  data,  hora);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return agua;
    }*/
   public static ArrayList<Agua> parserJsonAguas(JSONArray response) {

       ArrayList<Agua> aguas = new ArrayList<>();
       try {
           for (int i = 0; i < response.length(); i++) {
               JSONObject agua = (JSONObject) response.get(i);
               int id = agua.getInt("id");
               String descricao = agua.getString("descricao");
               float valor = (float) agua.getDouble("valor");
               LocalDate data = LocalDate.parse(agua.getString("data"));
               LocalTime hora = LocalTime.parse(agua.getString("hora"));

               Agua auxAgua = new Agua(id, descricao,  valor,  data,  hora);
               aguas.add(auxAgua);
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
       return aguas;
   }

    public static Boolean parserJsonInserirRegisto(String response) {
       Boolean success = false;
       try {
           JSONObject resposta = new JSONObject(response);

           if (resposta.getBoolean("success")){
               success = true;
           }
           else {
               return success;
           }

       } catch (JSONException e) {
           e.printStackTrace();
       }
       return success;
   }

}
