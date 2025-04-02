package com.example.aula_27_03_25;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BancoDados {

    private static final String TAG = "BancoDados";

    static String KEY_ID = "_id";
    static String KEY_USUARIO = "usuario";
    static String KEY_COMENTARIO= "comentario";

    String nomeBD = "bdComentarios";
    String nomeTabela = "comentarios";
    int versaoBanco = 1;

    String SQL_CREATE_TABLE = "CREATE TABLE comentarios (" +
            KEY_ID + " integer PRIMARY KEY autoincrement, " +
            KEY_USUARIO + " text not null, " +
            KEY_COMENTARIO + " text);";

    final Context contexto;
    MeuOpenHelper openHelper;
    SQLiteDatabase db;

    public BancoDados(Context ctx) {
        this.contexto = ctx;
        openHelper = new MeuOpenHelper(contexto);
    }

    private class MeuOpenHelper extends SQLiteOpenHelper {

        MeuOpenHelper (Context contexto) {
            super(contexto, nomeBD, null, versaoBanco);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(SQL_CREATE_TABLE);
            } catch (SQLException e) {
                Log.e(TAG, "Erro na criação do BD");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS personagens");
            onCreate(db);
        }
    }

    public BancoDados abrir() throws SQLException {
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void fechar () {
        openHelper.close();
    }

    public long inserir (String usuario, String comentario) {

        ContentValues campos = new ContentValues();
        campos.put(KEY_USUARIO, usuario);
        campos.put(KEY_COMENTARIO, comentario);

        return db.insert(nomeTabela, null, campos);
    }

    public boolean apagar (long id) {
        return db.delete(nomeTabela, KEY_ID + "=" + id,
                null) > 0;
    }

    public Cursor buscaTudo () {

        String[] campos = {KEY_ID, KEY_USUARIO, KEY_COMENTARIO};

        return db.query(nomeTabela, campos, null,
                null, null,
                null, null);
    }

    public boolean atualiza (long id, String usuario, String comentario) {
        ContentValues campos = new ContentValues();
        campos.put(KEY_USUARIO, usuario);
        campos.put(KEY_COMENTARIO, comentario);

        //Toast.makeText(contexto, "ID: " + id, Toast.LENGTH_SHORT).show();

        return db.update(nomeTabela, campos, KEY_ID + "=" + id,
                null) > 0;
    }
}

