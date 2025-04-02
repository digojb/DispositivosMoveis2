package com.example.aula_27_03_25;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Comentarios extends Activity {

    private ListView lista;
    private ArrayList<String> comentarios;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comentarios);

        Button btnVoltarCo = (Button) findViewById(R.id.btnVoltarCo);
        btnVoltarCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lista = (ListView) findViewById(R.id.lista);

        BancoDados bd = new BancoDados(getBaseContext());
        bd.abrir();

        Cursor resultSet = bd.buscaTudo();

        comentarios = new ArrayList<String> ();
        if (resultSet.moveToFirst()) {

            do {
                String pt1 = resultSet.getString(resultSet.getColumnIndex(BancoDados.KEY_USUARIO));
                String pt2 = resultSet.getString(resultSet.getColumnIndex(BancoDados.KEY_COMENTARIO));

                comentarios.add(pt1 + ": " + pt2);
            } while (resultSet.moveToNext());
        }

        ArrayAdapter<String> adaptadorLista = new ArrayAdapter<> (
             getBaseContext(),
             android.R.layout.simple_list_item_1,
             comentarios
        );

        lista.setAdapter(adaptadorLista);

        bd.fechar();
    }
}
