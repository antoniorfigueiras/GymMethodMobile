package pt.ipleiria.estg.dei.gymmethodmobile.modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="bdGym";
    private static final int DB_VERSION=1;
    private static final String TABLE_PLANOS ="planos";
    private static final String TABLE_EXERCICIOS_PLANO  ="exercicios_plano";
    private static final String TABLE_DETALHES_EXERCICIO  ="detalhes_exercicio";
    private static final String TABLE_PARAMETERIZACAO_CLIENTE  ="parameterizacaoCliente";
    //Table Plano
    private static final String ID="id", NOME="nome", TREINADOR="treinador";
    //Table Exercicios Plano
    private static final String PLANO_ID="plano_id", EQUIPAMENTO="equipamento", TIPO_EXERCICIO="tipo_exercicio";
    // Table Detalhes Exercicio
    private static final String EXERCICIO_PLANO_ID="exercicio_plano_id", DESCRICAO="descricao", DIFICULDADE="dificuldade",
            EXEMPLO="exemplo", SERIES="series", REPETICOES="repeticoes", PESO="peso", TEMPO="tempo";

    // Table Parameterizacao
    private static final String SERIESCLIENTE="seriesCliente", REPETICOESCLIENTE="repeticoesCliente", PESOCLIENTE="pesoCliente", TEMPOCLIENTE="tempoCliente";

    private final SQLiteDatabase db;
    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateTablePlanos ="CREATE TABLE " + TABLE_PLANOS + "(" +
                ID + " INTEGER  PRIMARY KEY, " +
                NOME  + " TEXT NOT NULL, " +
                TREINADOR  + " TEXT NOT NULL " +
                ")";
        sqLiteDatabase.execSQL(sqlCreateTablePlanos);

        String sqlCreateTableExerciciosPlano = "CREATE TABLE " + TABLE_EXERCICIOS_PLANO + "("
                + ID + " INTEGER  PRIMARY KEY, "
                + PLANO_ID  + " INTEGER NOT NULL, "
                + NOME  + " TEXT NOT NULL, "
                + EQUIPAMENTO  + " TEXT,"
                + TIPO_EXERCICIO  + " TEXT,"
                + "FOREIGN KEY ("+PLANO_ID+") REFERENCES "+TABLE_PLANOS+"("+ID+"))";
        sqLiteDatabase.execSQL(sqlCreateTableExerciciosPlano);

        String sqlCreateTableDetalhesExerciciosPlano = "CREATE TABLE " + TABLE_DETALHES_EXERCICIO + "("
                + EXERCICIO_PLANO_ID + " INTEGER  PRIMARY KEY, "
                + SERIES  + " INTEGER,"
                + REPETICOES  + " INTEGER,"
                + PESO  + " INTEGER,"
                + NOME  + " TEXT NOT NULL, "
                + EQUIPAMENTO  + " TEXT, "
                + DESCRICAO  + " TEXT,"
                + DIFICULDADE  + " TEXT,"
                + TEMPO  + " TEXT,"
                + EXEMPLO  + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(sqlCreateTableDetalhesExerciciosPlano);

        String sqlCreateTableParameterizacaoPlano = "CREATE TABLE " + TABLE_PARAMETERIZACAO_CLIENTE + "("
                + ID + " INTEGER  PRIMARY KEY, "
                + EXERCICIO_PLANO_ID + " INTEGER NOT NULL, "
                + SERIESCLIENTE  + " INTEGER,"
                + REPETICOESCLIENTE  + " INTEGER,"
                + PESOCLIENTE  + " INTEGER,"
                + TEMPOCLIENTE  + " TEXT,"
                + "FOREIGN KEY ("+EXERCICIO_PLANO_ID+") REFERENCES "+TABLE_EXERCICIOS_PLANO+"("+ID+"))";
        sqLiteDatabase.execSQL(sqlCreateTableParameterizacaoPlano);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlCreateTablePlanos ="DROP TABLE IF EXISTS " + TABLE_PLANOS;
        String sqlCreateTableExerciciosPlano ="DROP TABLE IF EXISTS " + TABLE_EXERCICIOS_PLANO;
        String sqlCreateTableDetalhesExerciciosPlano ="DROP TABLE IF EXISTS " + TABLE_DETALHES_EXERCICIO;
        String sqlCreateTableParameterizacaoPlano ="DROP TABLE IF EXISTS " + TABLE_PARAMETERIZACAO_CLIENTE;
        sqLiteDatabase.execSQL(sqlCreateTablePlanos);
        sqLiteDatabase.execSQL(sqlCreateTableExerciciosPlano);
        sqLiteDatabase.execSQL(sqlCreateTableDetalhesExerciciosPlano);
        sqLiteDatabase.execSQL(sqlCreateTableParameterizacaoPlano);
        onCreate(sqLiteDatabase);
    }


    //region Planos

    public Plano adicionarPlanoBD(Plano plano)
    {
        ContentValues values = new ContentValues();
        values.put(ID, plano.getId());
        values.put(NOME, plano.getNome());
        values.put(TREINADOR, plano.getTreinador());

        // db.insert retorna -1 em caso de erro ou o id que foi criado
        int id = (int)db.insert(TABLE_PLANOS, null, values);
        if(id>-1)
        {
            plano.setId(id);
            return plano;
        }
        return null;
    }



    public Boolean editarPlanoBD(Plano plano)
    {
        ContentValues values = new ContentValues();
        values.put(NOME, plano.getNome());
        values.put(TREINADOR, plano.getTreinador());
        // db.update retorna o numero de linhas atualizadas
        return db.update(TABLE_PLANOS, values, ID+"=?", new String[]{plano.getId()+""})==1;

    }

    public Boolean removerPlanoBD(int id)
    {
        // db.delete
        return db.delete(TABLE_PLANOS,ID+"=?", new String[]{id+""})==1;
    }

    public void removerAllPlanos()
    {
        db.delete(TABLE_PLANOS, null, null);
    }

    public ArrayList<Plano> getAllPlanosBD(){
        ArrayList<Plano> planos = new ArrayList<>();
        // Ordem de aprsentacao dos dados/ pesquisa
        Cursor cursor = db.query(TABLE_PLANOS, new String[]{ID, NOME, TREINADOR}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                Plano auxPlano = new Plano(cursor.getInt(0), cursor.getString(1), cursor.getString(2));// ID, NOME, TREINADOR

                planos.add(auxPlano);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return planos;
    }

    //endregion

    //region Exercicios

    public Exercicio adicionarExercicioBD(Exercicio exercicio)
    {
        ContentValues values = new ContentValues();
        values.put(ID, exercicio.getId());
        values.put(PLANO_ID, exercicio.getPlano_id());
        values.put(NOME, exercicio.getNome());
        values.put(EQUIPAMENTO, exercicio.getEquipamento());
        values.put(TIPO_EXERCICIO, exercicio.getTipo_exercicio());

        // db.insert retorna -1 em caso de erro ou o id que foi criado
        int id = (int)db.insert(TABLE_EXERCICIOS_PLANO, null, values);
        if(id>-1)
        {
            exercicio.setId(id);
            return exercicio;
        }
        return null;
    }



    public Boolean editarExercicioBD(Exercicio exercicio)
    {
        ContentValues values = new ContentValues();
        values.put(NOME, exercicio.getNome());
        values.put(EQUIPAMENTO, exercicio.getEquipamento());
        values.put(TIPO_EXERCICIO, exercicio.getTipo_exercicio());

        // db.update retorna o numero de linhas atualizadas
        return db.update(TABLE_EXERCICIOS_PLANO, values, ID+"=?", new String[]{exercicio.getId()+""})==1;

    }

    public Boolean removerExercicioBD(int id)
    {
        // db.delete
        return db.delete(TABLE_EXERCICIOS_PLANO,ID+"=?", new String[]{id+""})==1;
    }

    public void removerAllExercicios()
    {
        db.delete(TABLE_EXERCICIOS_PLANO, null, null);
    }

    public ArrayList<Exercicio> getAllExerciciosBD(int plano_id){
        ArrayList<Exercicio> exercicios = new ArrayList<>();
        //Cursor cursor = db.query(TABLE_EXERCICIOS_PLANO,new String[]{ID, PLANO_ID, NOME_EXERCICIO, EQUIPAMENTO, TIPO_EXERCICIO}, null, null, null, null, null);
        Cursor cursor = db.rawQuery( "select * from "+TABLE_EXERCICIOS_PLANO+" WHERE "+PLANO_ID+" = "+plano_id+"", null, null );
        if(cursor.moveToFirst()){
            do {
                Exercicio auxExercicio = new Exercicio(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3),  cursor.getString(4));// ID, PLANO_ID, NOME_EXERCICIO, EQUIPAMENTO, TIPO_EXERCICIO

                exercicios.add(auxExercicio);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return exercicios;
    }
    //endregion

    //region Detalhes do exercicio

    public DetalhesExercicio adicionarDetalhesExercicioBD(DetalhesExercicio detalhesExercicio)
    {
        ContentValues values = new ContentValues();
        values.put(EXERCICIO_PLANO_ID, detalhesExercicio.getExercicio_plano_id());
        values.put(SERIES, detalhesExercicio.getSeries());
        values.put(REPETICOES, detalhesExercicio.getRepeticoes());
        values.put(PESO, detalhesExercicio.getPeso());
        values.put(NOME, detalhesExercicio.getNome());
        values.put(EQUIPAMENTO, detalhesExercicio.getEquipamento());
        values.put(DESCRICAO, detalhesExercicio.getDescricao());
        values.put(DIFICULDADE, detalhesExercicio.getDificuldade());
        values.put(TEMPO, detalhesExercicio.getTempo());
        values.put(EXEMPLO, detalhesExercicio.getExemplo());

        // db.insert retorna -1 em caso de erro ou o id que foi criado
        int id = (int)db.insert(TABLE_DETALHES_EXERCICIO, null, values);
        if(id>-1)
        {
            detalhesExercicio.setExercicio_plano_id(id);
            return detalhesExercicio;
        }
        return null;
    }



    public Boolean editarDetalhesExercicioBD(DetalhesExercicio detalhesExercicio)
    {
        ContentValues values = new ContentValues();
        values.put(SERIES, detalhesExercicio.getSeries());
        values.put(REPETICOES, detalhesExercicio.getRepeticoes());
        values.put(PESO, detalhesExercicio.getPeso());
        values.put(NOME, detalhesExercicio.getNome());
        values.put(EQUIPAMENTO, detalhesExercicio.getEquipamento());
        values.put(DESCRICAO, detalhesExercicio.getDescricao());
        values.put(DIFICULDADE, detalhesExercicio.getDificuldade());
        values.put(TEMPO, detalhesExercicio.getTempo());
        values.put(EXEMPLO, detalhesExercicio.getExemplo());

        // db.update retorna o numero de linhas atualizadas
        return db.update(TABLE_DETALHES_EXERCICIO, values, EXERCICIO_PLANO_ID+"=?", new String[]{detalhesExercicio.getExercicio_plano_id()+""})==1;

    }

    public Boolean removerDetalhesExercicioBD(int id)
    {
        // db.delete
        return db.delete(TABLE_DETALHES_EXERCICIO,EXERCICIO_PLANO_ID+"=?", new String[]{id+""})==1;
    }

    public void removerAllDetalhes()
    {
        db.delete(TABLE_DETALHES_EXERCICIO, null, null);
    }

    public DetalhesExercicio getDetalhesExercicioBD(int exercicio_plano_id){

        DetalhesExercicio detalhesExercicios = null;
        Cursor cursor = db.rawQuery( "select * from "+TABLE_DETALHES_EXERCICIO+" WHERE "+EXERCICIO_PLANO_ID+" = "+exercicio_plano_id+"", null, null );
        if(cursor.moveToFirst()){
            do {

                DetalhesExercicio auxDetalhes = new DetalhesExercicio(
                        cursor.getInt(0), // exercicio_plano_id
                        cursor.getInt(1), // series
                        cursor.getInt(2), // repeticoes
                        cursor.getInt(3), // peso
                        cursor.getString(4), // nome
                        cursor.getString(5), // equipamento
                        cursor.getString(6), // descricao
                        cursor.getString(7), // dificuldade
                        cursor.getString(9), // exemplo
                        cursor.getString(8)); // tempo



                detalhesExercicios = auxDetalhes;
            }while (cursor.moveToNext());
            cursor.close();
        }

        return detalhesExercicios;
    }
    public ArrayList<DetalhesExercicio>  getAllDetalhesExercicioBD(){
        ArrayList<DetalhesExercicio> detalhesExercicios = new ArrayList<>();
        Cursor cursor = db.rawQuery( "select * from "+TABLE_DETALHES_EXERCICIO+"", null, null );
        if(cursor.moveToFirst()){
            do {

                DetalhesExercicio auxDetalhes = new DetalhesExercicio(
                        cursor.getInt(0), // exercicio_plano_id
                        cursor.getInt(1), // series
                        cursor.getInt(2), // repeticoes
                        cursor.getInt(3), // peso
                        cursor.getString(4), // nome
                        cursor.getString(5), // equipamento
                        cursor.getString(6), // descricao
                        cursor.getString(7), // dificuldade
                        cursor.getString(9), // exemplo
                        cursor.getString(8)); // tempo


                detalhesExercicios.add(auxDetalhes);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return detalhesExercicios;
    }
    //endregion

    //region Parameterizacao Cliente

    public ParameterizacaoCliente adicionarParameterizacaoBD(ParameterizacaoCliente parameterizacao)
    {
        ContentValues values = new ContentValues();
        values.put(ID, parameterizacao.getId());
        values.put(EXERCICIO_PLANO_ID, parameterizacao.getExercicio_plano_id());
        values.put(SERIESCLIENTE, parameterizacao.getSeriesCliente());
        values.put(REPETICOESCLIENTE, parameterizacao.getRepeticoesCliente());
        values.put(PESOCLIENTE, parameterizacao.getPesoCliente());
        values.put(TEMPOCLIENTE, parameterizacao.getTempoCliente());

        // db.insert retorna -1 em caso de erro ou o id que foi criado
        int id = (int)db.insert(TABLE_PARAMETERIZACAO_CLIENTE, null, values);
        if(id>-1)
        {
            parameterizacao.setId(id);
            return parameterizacao;
        }
        return null;
    }



    public Boolean editarParameterizacaoBD(ParameterizacaoCliente parameterizacao)
    {
        ContentValues values = new ContentValues();
        values.put(SERIESCLIENTE, parameterizacao.getSeriesCliente());
        values.put(REPETICOESCLIENTE, parameterizacao.getRepeticoesCliente());
        values.put(PESOCLIENTE, parameterizacao.getPesoCliente());
        values.put(TEMPOCLIENTE, parameterizacao.getTempoCliente());

        // db.update retorna o numero de linhas atualizadas
        return db.update(TABLE_PARAMETERIZACAO_CLIENTE, values, ID+"=?", new String[]{parameterizacao.getId()+""})==1;

    }

    public Boolean removerParameterizacaoBD(int id)
    {
        // db.delete
        return db.delete(TABLE_PARAMETERIZACAO_CLIENTE,ID+"=?", new String[]{id+""})==1;
    }

    public void removerAllParameterizacao()
    {
        db.delete(TABLE_PARAMETERIZACAO_CLIENTE, null, null);
    }

    public ParameterizacaoCliente getParameterizacaoBD(int exercicio_plano_id){

        ParameterizacaoCliente parameterizacao = null;
        Cursor cursor = db.rawQuery( "select * from "+TABLE_PARAMETERIZACAO_CLIENTE+" WHERE "+EXERCICIO_PLANO_ID+" = "+exercicio_plano_id+"", null, null );
        if(cursor.moveToFirst()){
            do {

                ParameterizacaoCliente auxParameterizacao = new ParameterizacaoCliente(
                        cursor.getInt(0), // id
                        cursor.getInt(1), // exercicio_plano_id
                        cursor.getInt(2), // series
                        cursor.getInt(3), // repeticoes
                        cursor.getInt(4), // peso
                        cursor.getString(5)); // tempo



                parameterizacao = auxParameterizacao;
            }while (cursor.moveToNext());
            cursor.close();
        }

        return parameterizacao;
    }
    public ArrayList<ParameterizacaoCliente>  getAllParameterizacaoBD(){
        ArrayList<ParameterizacaoCliente> parameterizacao = new ArrayList<>();
        Cursor cursor = db.rawQuery( "select * from "+TABLE_PARAMETERIZACAO_CLIENTE+"", null, null );
        if(cursor.moveToFirst()){
            do {

                ParameterizacaoCliente auxParameterizacao = new ParameterizacaoCliente(
                        cursor.getInt(0), // id
                        cursor.getInt(1), // exercicio_plano_id
                        cursor.getInt(2), // series
                        cursor.getInt(3), // repeticoes
                        cursor.getInt(4), // peso
                        cursor.getString(5)); // tempo


                parameterizacao.add(auxParameterizacao);

            }while (cursor.moveToNext());
            cursor.close();
        }
        return parameterizacao;
    }
    //endregion
}
