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
    private static final String ID="id", NOME="nome", TREINADOR="treinador";
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlCreateTablePlanos ="DROP TABLE IF EXISTS " + TABLE_PLANOS;
        sqLiteDatabase.execSQL(sqlCreateTablePlanos);
        onCreate(sqLiteDatabase);
    }

    // CRUD

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

    public Boolean removerLivroBD(int id)
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
        Cursor cursor = db.query(TABLE_PLANOS, new String[]{NOME, TREINADOR, ID}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                Plano auxPlano = new Plano(cursor.getInt(0), cursor.getString(1), cursor.getString(2));// ID, NOME, TREINADOR

                planos.add(auxPlano);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return planos;
    }
}
