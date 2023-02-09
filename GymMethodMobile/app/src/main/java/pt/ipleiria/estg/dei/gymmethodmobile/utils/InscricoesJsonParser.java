package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONException;
import org.json.JSONObject;


public class InscricoesJsonParser {

    public static int parserJsonInscrever(String response) {
        int inscricao_id = 0;
        try {
            JSONObject resposta = new JSONObject(response);

            if (resposta.getBoolean("success")){
                inscricao_id = resposta.getInt("inscricao_id");
            }
            else {
                return inscricao_id;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return inscricao_id;
    }
    public static Boolean parserJsonRemoverInscricao(String response) {
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
