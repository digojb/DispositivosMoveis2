package com.example.atividade2_13_02_25;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Principal extends Activity {

    private ListView listViewPersonagens;
    private PersonagemAdapter adapter;
    private List<Personagem> personagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        personagens = new ArrayList<>();
        personagens.add(new Personagem("Hugo"));
        personagens.add(new Personagem("Patrícia"));
        personagens.add(new Personagem("Gustavo"));
        personagens.add(new Personagem("Alane"));
        personagens.add(new Personagem("Hugo"));
        personagens.add(new Personagem("Patrícia"));
        personagens.add(new Personagem("Gustavo"));
        personagens.add(new Personagem("Alane"));

        listViewPersonagens = findViewById(R.id.listViewPersonagens);
        adapter = new PersonagemAdapter(this, personagens);
        listViewPersonagens.setAdapter(adapter);

        Button btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(v -> {
            personagens.add(new Personagem("Novo Personagem"));
            adapter.notifyDataSetChanged();
        });
    }
}
