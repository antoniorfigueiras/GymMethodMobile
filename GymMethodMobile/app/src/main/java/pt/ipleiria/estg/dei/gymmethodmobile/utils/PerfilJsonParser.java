package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Plano;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.User;


public class PerfilJsonParser {


    public static User parserJsonPerfil(String response) {
        User auxPerfil = null;
        try {
            JSONObject perfil = new JSONObject(response);
            int user_id = perfil.getInt("user_id");
            int telemovel = perfil.getInt("telemovel");
            int altura = perfil.getInt("altura");
            double peso = perfil.getDouble("peso");
            int nif = perfil.getInt("nif");
            String nomeproprio = perfil.getString("nomeproprio");
            String apelido = perfil.getString("apelido");
            String pais = perfil.getString("pais");
            String cidade = perfil.getString("cidade");
            String morada = perfil.getString("morada");
            String codpostal = perfil.getString("codpostal");
            String imagem = perfil.getString("imagem");
            auxPerfil = new User(user_id, telemovel, altura, nif, peso, nomeproprio, apelido, codpostal, pais, cidade, morada, imagem);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPerfil;
    }


}
