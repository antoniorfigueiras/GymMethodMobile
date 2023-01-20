package pt.ipleiria.estg.dei.gymmethodmobile.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Base64;

import pt.ipleiria.estg.dei.gymmethodmobile.modelos.DetalhesExercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.Exercicio;
import pt.ipleiria.estg.dei.gymmethodmobile.modelos.ParameterizacaoCliente;
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

    /*public static ArrayList<DetalhesExercicio>  parserJsonDetalhesExercicio(JSONArray response) {
        ArrayList<DetalhesExercicio> detalhesExercicios = new ArrayList<>();
        int peso;
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject detalhes = (JSONObject) response.get(i);

                int exercicio_plano_id = detalhes.getInt("exercicio_plano_id");
                int series = detalhes.getInt("series");
                int repeticoes = detalhes.getInt("repeticoes");

                if (detalhes.isNull("peso"))
                {
                     peso = 0;
                }else {
                     peso = detalhes.getInt("peso");
                }

                String nome = detalhes.getString("nome");
                String equipamento = detalhes.getString("equipamento");
                String descricao = detalhes.getString("descricao");
                String dificuldade = detalhes.getString("dificuldade");
                String exemplo = detalhes.getString("exemplo");
                String tempo = detalhes.getString("tempo");

                DetalhesExercicio auxDetalhes = new DetalhesExercicio(exercicio_plano_id,  series,  repeticoes,  peso,  nome,  equipamento,  descricao,  dificuldade, exemplo, tempo);
                detalhesExercicios.add(auxDetalhes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detalhesExercicios;
    }*/
    public static DetalhesExercicio parserJsonDetalhesExercicio(JSONArray response) {
       DetalhesExercicio auxDetalhes = null;
        int peso;
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject detalhes = (JSONObject) response.get(i);

                int exercicio_plano_id = detalhes.getInt("exercicio_plano_id");
                int series = detalhes.getInt("series");
                int repeticoes = detalhes.getInt("repeticoes");

                if (detalhes.isNull("peso"))
                {
                    peso = 0;
                }else {
                    peso = detalhes.getInt("peso");
                }

                String nome = detalhes.getString("nome");
                String equipamento = detalhes.getString("equipamento");
                String descricao = detalhes.getString("descricao");
                String dificuldade = detalhes.getString("dificuldade");
                String exemplo = detalhes.getString("exemplo");
                String tempo = detalhes.getString("tempo");

                auxDetalhes = new DetalhesExercicio(exercicio_plano_id,  series,  repeticoes,  peso,  nome,  equipamento,  descricao,  dificuldade, exemplo, tempo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxDetalhes;
    }
    public static ParameterizacaoCliente parserJsonParameterizacao(JSONArray response) {
        ParameterizacaoCliente auxParameterizacao = null;
        int seriesCliente, pesoCliente, repeticoesCliente;
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject parameterizacao = (JSONObject) response.get(i);

                int id = parameterizacao.getInt("id");
                int exercicio_plano_id = parameterizacao.getInt("exercicio_plano_id");

                if (parameterizacao.isNull("seriesCliente"))
                {
                     seriesCliente = 0;
                }else {
                     seriesCliente = parameterizacao.getInt("seriesCliente");
                }
                if (parameterizacao.isNull("repeticoesCliente"))
                {
                     repeticoesCliente = 0;
                }else {
                     repeticoesCliente = parameterizacao.getInt("repeticoesCliente");
                }
                if (parameterizacao.isNull("pesoCliente"))
                {
                     pesoCliente = 0;
                }else {
                     pesoCliente = parameterizacao.getInt("pesoCliente");
                }

                String tempo = parameterizacao.getString("tempoCliente");

                auxParameterizacao = new ParameterizacaoCliente(id, exercicio_plano_id,  seriesCliente,  repeticoesCliente,  pesoCliente, tempo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxParameterizacao;
    }

}
