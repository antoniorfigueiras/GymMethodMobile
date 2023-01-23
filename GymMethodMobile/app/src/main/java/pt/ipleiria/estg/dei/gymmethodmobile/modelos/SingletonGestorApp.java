package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.gymmethodmobile.adaptadores.CalendarAdapter;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.AulasInscritasListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.ConsultasListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.DetalhesExercicioListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.ExerciciosPlanoListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.InscricoesListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.LoginListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PerfilListener;
import pt.ipleiria.estg.dei.gymmethodmobile.listeners.PlanosListener;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.AulasJsonParser;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.ConsultaJsonParser;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.InscricoesJsonParser;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.JsonParser;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.PerfilJsonParser;
import pt.ipleiria.estg.dei.gymmethodmobile.utils.PlanoJsonParser;

public class SingletonGestorApp {

    private static SingletonGestorApp instance = null;
    private static RequestQueue volleyQueue = null;
    private LoginListener loginListener;
    private PlanosListener planosListener;
    private ConsultasListener consultasListener;
    private ExerciciosPlanoListener exerciciosPlanoListener;
    private PerfilListener perfilListener;
    private DetalhesExercicioListener detalhesListener;
    private CalendarAdapter.OnItemListener onItemListener;
    private InscricoesListener inscricoesListener;
    private AulasInscritasListener aulasInscritasListener;
    private User perfils;

    private ArrayList<Plano> planos;
    private ArrayList<Aula> aulas;
    private ArrayList<AulaInscrita> aulasInscritas;
    private ArrayList<Exercicio> exercicios;
    private ArrayList<DetalhesExercicio> detalhesExercicioList;
    private ArrayList<Consulta> consultas;
    private DetalhesExercicio detalhesExercicios;
    private ArrayList<ParameterizacaoCliente> parameterizacaoList;
    private ParameterizacaoCliente parameterizacao;
    private BDHelper BD;

    private static final String APILogin = "http://10.0.2.2/gymmethod/backend/web/api/auth/login";
    private static final String APIGetPlanos = "http://10.0.2.2/gymmethod/backend/web/api/plano/get-planos";
    private static final String APIGetExerciciosPlano = "http://10.0.2.2/gymmethod/backend/web/api/exercicio-plano/get-exercicios-plano/";
    private static final String APIGetPerfil = "http://10.0.2.2/gymmethod/backend/web/api/user/get-perfil";
    private static final String APIEditPerfil = "http://10.0.2.2/gymmethod/backend/web/api/user/atualizar-perfil";
    private static final String APIGetConsultas = "http://10.0.2.2/gymmethod/backend/web/api/consulta/get-consultas-marcadas";
    private static final String APIGetConsultasConcluidas = "http://10.0.2.2/gymmethod/backend/web/api/consulta/get-consultas-concluidas";
    private static final String APIGetExercicioDetalhes = "http://10.0.2.2/gymmethod/backend/web/api/exercicio-plano/get-exercicio-detalhes/";
    private static final String APIGetParameterizacaoCliente = "http://10.0.2.2/gymmethod/backend/web/api/exercicio-plano/parameterizacao-cliente/";
    private static final String APIAtualizarParameterizacao = "http://10.0.2.2/gymmethod/backend/web/api/parameterizacao/atualizar-parameterizacao-cliente/";
    private static final String APIGetAulas = "http://10.0.2.2/gymmethod/backend/web/api/aulas/get-aulas";
    private static final String APIInscreverAula = "http://10.0.2.2/gymmethod/backend/web/api/inscricoes/inscrever/";
    private static final String APIRemoverInscricaoAula = "http://10.0.2.2/gymmethod/backend/web/api/inscricoes/remover-inscricao/";
    private static final String APIGetAulasInscritas = "http://10.0.2.2/gymmethod/backend/web/api/aulas/get-aulas-inscritas";
    private static final String APIGetInscricoes = "http://10.0.2.2/gymmethod/backend/web/api/inscricoes/get-inscricoes";

    public static synchronized SingletonGestorApp getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorApp(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorApp(Context context) {
        planos = new ArrayList<>();
        exercicios = new ArrayList<>();
        detalhesExercicioList = new ArrayList<>();
        parameterizacaoList = new ArrayList<>();
        BD = new BDHelper(context);

    }

    public void setAulasInscritasListener(AulasInscritasListener aulasInscritasListener) {
        this.aulasInscritasListener = aulasInscritasListener;
    }
    public void unsetAulasInscritasListener() {
        this.aulasInscritasListener = null;
    }

    public void setInscricoesListener(InscricoesListener inscricoesListener) {
        this.inscricoesListener = inscricoesListener;
    }

    public void setOnItemListener(CalendarAdapter.OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setPlanosListener(PlanosListener planosListener) {
        this.planosListener = planosListener;
    }

    public void setConsultasListener(ConsultasListener consultasListener) {
        this.consultasListener = consultasListener;
    }

    public void setExerciciosPlanoListener(ExerciciosPlanoListener exerciciosPlanoListener) {
        this.exerciciosPlanoListener = exerciciosPlanoListener;
    }

    public void setPerfilListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }

    public void setDetalhesListener(DetalhesExercicioListener detalhesListener) {
        this.detalhesListener = detalhesListener;
    }

    //region Planos

    public ArrayList<Plano> getPlanosBD() { // return da copia dos planos
        planos = BD.getAllPlanosBD();
        return new ArrayList(planos);
    }

    public Plano getPlano(int idPlano) {
        for (Plano p : planos) {
            if (p.getId() == idPlano)
                return p;
        }
        return null;
    }

    public void adicionarPlanoBD(Plano p) {
        BD.adicionarPlanoBD(p);
    }

    public void adicionarPlanosBD(ArrayList<Plano> planos) {
        BD.removerAllPlanos();
        for (Plano p : planos) {
            adicionarPlanoBD(p);
        }
    }

    //endregion

    //region Exercicios

    public ArrayList<Exercicio> getExerciciosBD(int plano_id) {
        exercicios = BD.getAllExerciciosBD(plano_id);
        return new ArrayList(exercicios);
    }

    public Exercicio getExercicio(int idExercicioPlano) {
        for (Exercicio e : exercicios) {
            if (e.getId() == idExercicioPlano)
                return e;
        }
        return null;
    }

    public void adicionarExercicioBD(Exercicio e) {
        BD.adicionarExercicioBD(e);
    }


    public void adicionarExerciciosBD(ArrayList<Exercicio> exercicios) {
        BD.removerAllExercicios();
        for (Exercicio e : exercicios) {
            adicionarExercicioBD(e);
        }
    }
    //endregion


    //region Detalhes Exercicio

    public ArrayList<DetalhesExercicio> getAllDetalhesBD() {
        detalhesExercicioList = BD.getAllDetalhesExercicioBD();
        return new ArrayList(detalhesExercicioList);
    }

    public DetalhesExercicio getDetalhes(int exercicio_plano_id) {
        detalhesExercicioList = BD.getAllDetalhesExercicioBD();
        for (DetalhesExercicio d : detalhesExercicioList) {
            if (d.getExercicio_plano_id() == exercicio_plano_id)
                return d;
        }
        return null;
    }

    public void adicionarDetalheBD(DetalhesExercicio d) {
        BD.adicionarDetalhesExercicioBD(d);
    }

    public void adicionarDetalhesBD(DetalhesExercicio detalhesExercicios) {
        if (getDetalhes(detalhesExercicios.getExercicio_plano_id()) == null) {
            adicionarDetalheBD(detalhesExercicios);
        } else {
            BD.editarDetalhesExercicioBD(detalhesExercicios);
        }


    }
    //endregion

    //region Parameterizacao

    public ArrayList<ParameterizacaoCliente> getAllParameterizacaoBD() {
        parameterizacaoList = BD.getAllParameterizacaoBD();
        return new ArrayList(parameterizacaoList);
    }

    public ParameterizacaoCliente getParameterizacao(int exercicio_plano_id) {
        parameterizacaoList = BD.getAllParameterizacaoBD();
        for (ParameterizacaoCliente p : parameterizacaoList) {
            if (p.getExercicio_plano_id() == exercicio_plano_id)
                return p;
        }
        return null;
    }

    public void adicionarParameterizacaoBD(ParameterizacaoCliente p) {
        BD.adicionarParameterizacaoBD(p);
    }

    public void adicionarParameterizacoesBD(ParameterizacaoCliente parameterizacao) {

        if (getParameterizacao(parameterizacao.getExercicio_plano_id()) == null) {
            adicionarParameterizacaoBD(parameterizacao);
        } else {
            BD.editarParameterizacaoBD(parameterizacao);
        }

    }

    public void editarParameterizacaoDB(ParameterizacaoCliente p) {
        ParameterizacaoCliente auxP = getParameterizacao(p.getId());
        if (auxP != null) {
            BD.editarParameterizacaoBD(p);
        }

    }
    //endregion

    public void loginAPI(final String username, final String password, final Context context) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.GET, APILogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String token = JsonParser.parserJsonLogin(response);
                    Integer user_id = JsonParser.parserJsonUser_id(response);
                    String username = JsonParser.parserJsonUsername(response);

                    if (loginListener != null)
                        loginListener.onValidateLogin(token, user_id, username, context);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Username ou password incorreto", Toast.LENGTH_LONG).show();
                    VolleyLog.d("LOGIN: Error" + error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String credentials = username + ":" + password;
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic " + base64EncodedCredentials);
                    return headers;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getPerfilAPI(final Context context, String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.GET, APIGetPerfil + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    perfils = PerfilJsonParser.parserJsonPerfil(response);
                    if (perfilListener != null)
                        perfilListener.onShowPerfil(perfils);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void editPerfilAPI(final User perfil, final Context context, String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.PUT, APIEditPerfil + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (perfilListener != null)
                        perfilListener.onShowPerfil(perfil);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nomeproprio", perfil.getNomeproprio());
                    params.put("apelido", perfil.getApelido());
                    params.put("telemovel", perfil.getTelemovel() + "");
                    params.put("peso", perfil.getPeso() + "");
                    params.put("altura", perfil.getAltura() + "");
                    params.put("nif", perfil.getNif() + "");
                    params.put("codpostal", perfil.getCodpostal() + "");
                    params.put("pais", perfil.getPais());
                    params.put("cidade", perfil.getCidade());
                    params.put("morada", perfil.getMorada());
                    return params;
                }
            };
            volleyQueue.add(req);
        }

    }

    public void getAllConsultasAPI(final Context context, String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetConsultas + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    consultas = ConsultaJsonParser.parserJsonConsultas(response);

                    if (consultasListener != null) {
                        consultasListener.onRefreshListaConsultas(consultas);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void getAllConsultasConcluidasAPI(final Context context, String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetConsultasConcluidas + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    consultas = ConsultaJsonParser.parserJsonConsultas(response);

                    if (consultasListener != null) {
                        consultasListener.onRefreshListaConsultas(consultas);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void getAllPlanosAPI(final Context context, String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
            if (planosListener != null) {
                planosListener.onRefreshListaPlanos(BD.getAllPlanosBD());
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetPlanos + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    planos = PlanoJsonParser.parserJsonPlanos(response);
                    adicionarPlanosBD(planos);

                    if (planosListener != null) {
                        planosListener.onRefreshListaPlanos(planos);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void getExerciciosPlanoAPI(final Context context, String token, int plano_id) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
            if (exerciciosPlanoListener != null) {
                exerciciosPlanoListener.onRefreshListaExercicios(BD.getAllExerciciosBD(plano_id));
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetExerciciosPlano + plano_id + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    exercicios = PlanoJsonParser.parserJsonExercicios(response);
                    adicionarExerciciosBD(exercicios);

                    if (exerciciosPlanoListener != null) {
                        exerciciosPlanoListener.onRefreshListaExercicios(exercicios);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }


    public void getExercicioDetalhesAPI(final Context context, String token, int exercicio_plano_id) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
            if (detalhesListener != null) {
                detalhesListener.onSetDetalhes(BD.getDetalhesExercicioBD(exercicio_plano_id), BD.getParameterizacaoBD(exercicio_plano_id));

            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetExercicioDetalhes + exercicio_plano_id + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    detalhesExercicios = PlanoJsonParser.parserJsonDetalhesExercicio(response);
                    adicionarDetalhesBD(detalhesExercicios);

                    if (detalhesListener != null) {
                        detalhesListener.onSetDetalhes(detalhesExercicios, parameterizacao);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void getParameterizacaoClienteAPI(final Context context, String token, int exercicio_plano_id) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
            if (detalhesListener != null) {

                detalhesListener.onSetDetalhes(detalhesExercicios = null, BD.getParameterizacaoBD(exercicio_plano_id));
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetParameterizacaoCliente + exercicio_plano_id + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    parameterizacao = PlanoJsonParser.parserJsonParameterizacao(response);
                    adicionarParameterizacoesBD(parameterizacao);
                    detalhesExercicios = null;
                    if (detalhesListener != null) {
                        detalhesListener.onSetDetalhes(detalhesExercicios, parameterizacao);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void atualizarParameterizacaoCliente(final Context context, String token, final ParameterizacaoCliente parameterizacao) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.PUT, APIAtualizarParameterizacao + parameterizacao.getId() + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    editarParameterizacaoDB(parameterizacao);
                    detalhesExercicios = null;
                    if (detalhesListener != null) {
                        detalhesListener.onSetDetalhes(detalhesExercicios, parameterizacao);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("seriesCliente", parameterizacao.getSeriesCliente() + "");
                    params.put("repeticoesCliente", parameterizacao.getRepeticoesCliente() + "");
                    params.put("pesoCliente", parameterizacao.getPesoCliente() + "");
                    params.put("tempoCliente", parameterizacao.getTempoCliente());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getAulasAPI(final Context context, String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetAulas + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    aulas = AulasJsonParser.parserJsonAulas(response);
                    Aula.aulasList = aulas;
                    if (onItemListener != null)
                    {
                        onItemListener.onSetAulas(aulas);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void inscreverAulaAPI(final Context context, String token, int idAula) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, APIInscreverAula + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int inscricao_id = InscricoesJsonParser.parserJsonInscrever(response);

                    if (inscricoesListener != null)
                    {
                        inscricoesListener.onInscrever(inscricao_id);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idAula", idAula +"");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }
    public void removerInscricaoAulaAPI(final Context context, String token, int idInscricao, int action){
        if (!JsonParser.isConnectionInternet(context)){
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        }else
        {
            StringRequest req = new StringRequest(Request.Method.DELETE, APIRemoverInscricaoAula + idInscricao + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Boolean success = InscricoesJsonParser.parserJsonRemoverInscricao(response);
                    if (inscricoesListener != null)
                    {
                        inscricoesListener.onRemoverInscricao(action, success); // 0, volta para lista de aulas marcadas, 1 fica na vista detalhes
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);
        }
    }
    public void getAulasInscritas(final Context context, String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Sem ligação á internet", Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, APIGetAulasInscritas + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    aulasInscritas = AulasJsonParser.parserJsonAulasInscritas(response);
                    AulaInscrita.aulasInscritasList = aulasInscritas;
                    if (aulasInscritasListener != null)
                    {
                        aulasInscritasListener.onRefreshListaAulasInscritas(aulasInscritas);
                    }else if (inscricoesListener != null){
                        inscricoesListener.onGetInscricoes(aulasInscritas);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            volleyQueue.add(req);
        }
    }

}
