package com.example.atividadeavaliativa2;

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
    static String KEY_NOME = "nome";
    static String KEY_FORCA = "forca";
    static String KEY_INTELIGENCIA = "inteligencia";
    static String KEY_AGILIDADE = "agilidade";
    static String KEY_CLASSE = "classe";


    String NOME_BANCO = "db_personagens";
    String NOME_TABELA = "personagens";
    int VERSAO_BANCO = 1;

    String SQL_CREATE_TABLE = "CREATE TABLE " + NOME_TABELA + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NOME + " TEXT, " +
            KEY_FORCA + " INTEGER, " +
            KEY_INTELIGENCIA + " INTEGER, " +
            KEY_AGILIDADE + " INTEGER, " +
            KEY_CLASSE + " TEXT);";

    final Context contexto;
    MeuOpenHelper openHelper;
    SQLiteDatabase db;

    public BancoDados(Context ctx) {
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

    public long inserePersonagem(String nome, int forca, int inteligencia, int agilidade, String classe) {
        abrir();
        ContentValues campos = new ContentValues();
        campos.put(KEY_NOME, nome);
        campos.put(KEY_FORCA, forca);
        campos.put(KEY_INTELIGENCIA, inteligencia);
        campos.put(KEY_AGILIDADE, agilidade);
        campos.put(KEY_CLASSE, classe);
        return db.insert(NOME_TABELA, null, campos);
    }

    public Cursor listarPersonagens() {
        String[] colunas = {KEY_ID, KEY_NOME, KEY_FORCA, KEY_INTELIGENCIA, KEY_AGILIDADE, KEY_CLASSE};
        return db.query(NOME_TABELA, colunas, null, null, null, null, null);
    }

    public int atualizarPersonagem(long id, String nome, int forca, int inteligencia, int agilidade, String classe) {
        abrir();
        ContentValues campos = new ContentValues();
        campos.put(KEY_NOME, nome);
        campos.put(KEY_FORCA, forca);
        campos.put(KEY_INTELIGENCIA, inteligencia);
        campos.put(KEY_AGILIDADE, agilidade);
        campos.put(KEY_CLASSE, classe);
        return db.update(NOME_TABELA, campos, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int excluirPersonagem(long id) {
        abrir();
        return db.delete(NOME_TABELA, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
}