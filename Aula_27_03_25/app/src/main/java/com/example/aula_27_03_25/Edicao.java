package com.example.aula_27_03_25;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edicao extends Activity {

    private EditText edtUsuario;
    private EditText edtComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edicao);

        Button btnVoltarEd = (Button) findViewById(R.id.btnVoltarEd);
        btnVoltarEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtComentario = (EditText) findViewById(R.id.edtComentario);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    public void salvar () {

        String usuario = edtUsuario.getText().toString();
        String comentario = edtComentario.getText().toString();

        BancoDados bd = new BancoDados(getBaseContext());
        bd.abrir();

        bd.inserir(usuario, comentario);

        bd.fechar();

        finish();
    }
}
