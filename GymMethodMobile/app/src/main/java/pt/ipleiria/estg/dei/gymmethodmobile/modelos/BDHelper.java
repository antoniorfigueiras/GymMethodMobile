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
    //Table Plano
    private static final String ID="id", NOME="nome", TREINADOR="treinador";
    //Table Exercicios Plano
    private static final String PLANO_ID="plano_id", NOME_EXERCICIO="nome", EQUIPAMENTO="equipamento", TIPO_EXERCICIO="tipo_exercicio";
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
                + NOME_EXERCICIO  + " TEXT NOT NULL, "
                + EQUIPAMENTO  + " TEXT,"
                + TIPO_EXERCICIO  + " TEXT,"
                + "FOREIGN KEY ("+PLANO_ID+") REFERENCES "+TABLE_PLANOS+"("+ID+"))";
        sqLiteDatabase.execSQL(sqlCreateTableExerciciosPlano);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlCreateTablePlanos ="DROP TABLE IF EXISTS " + TABLE_PLANOS;
        String sqlCreateTableExerciciosPlano ="DROP TABLE IF EXISTS " + TABLE_EXERCICIOS_PLANO;
        sqLiteDatabase.execSQL(sqlCreateTablePlanos);
        sqLiteDatabase.execSQL(sqlCreateTableExerciciosPlano);
        onCreate(sqLiteDatabase);
    }

    //region CRUD Planos

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

    //region CRUD Exercicios

    public Exercicio adicionarExercicioBD(Exercicio exercicio)
    {
        ContentValues values = new ContentValues();
        values.put(ID, exercicio.getId());
        values.put(PLANO_ID, exercicio.getPlano_id());
        values.put(NOME_EXERCICIO, exercicio.getNome());
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

    public ArrayList<Exercicio> getAllExerciciosBD(){
        ArrayList<Exercicio> exercicios = new ArrayList<>();
        Cursor cursor = db.query(TABLE_EXERCICIOS_PLANO, new String[]{ID, PLANO_ID, NOME_EXERCICIO, EQUIPAMENTO, TIPO_EXERCICIO}, null, null, null, null, null);
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
}
