package com.example.atividade5_060325;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BancoDados {

    private final static String TAG = "BancoDados";

    static String KEY_ID = "_id";
    static String KEY_VALOR = "valor";
    static String KEY_QUANTIDADE = "quantidade";

    String NOME_BANCO = "db_dividas";
    String NOME_TABELA = "dividas";
    int VERSAO_BANCO = 1;

    String SQL_CREATE_TABLE = "CREATE TABLE dividas (" +
            KEY_ID + " integer PRIMARY KEY autoincrement, " +
            KEY_VALOR + " integer, " +
            KEY_QUANTIDADE + " integer);";

    final Context contexto;
    MeuOpenHelper openHelper;
    SQLiteDatabase db;

    public BancoDados (Context ctx) {
        this.contexto = ctx;
        openHelper = new MeuOpenHelper(contexto);
    }

    private class MeuOpenHelper extends SQLiteOpenHelper {

        MeuOpenHelper (Context contexto) {
            super(contexto, NOME_BANCO, null, VERSAO_BANCO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(SQL_CREATE_TABLE);
            } catch (SQLException e) {
                Log.e(TAG, "Erro na criação do BD.");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS dividas");
            onCreate(db);
        }
    }

    public BancoDados abrir() throws SQLException {
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void fechar() {
        openHelper.close();
    }

    public long insereDivida (int valor, int quantidade) {
        ContentValues campos = new ContentValues();
        campos.put(KEY_VALOR, valor);
        campos.put(KEY_QUANTIDADE, quantidade);
        return db.insert(NOME_TABELA, null, campos);
    }

    public Cursor retornaDividas () {
        String [] camposApresentados = {KEY_ID, KEY_VALOR, KEY_QUANTIDADE};
        return db.query (NOME_TABELA, camposApresentados, null, null, null, null, null);
    }
}