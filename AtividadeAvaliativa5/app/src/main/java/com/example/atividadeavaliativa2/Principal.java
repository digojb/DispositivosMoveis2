package com.example.atividadeavaliativa2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String DARK_MODE_KEY = "dark_mode";
    private BancoDados bancoDeDados;
    private ArrayList<Personagem> personagens;
    private PersonagemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        bancoDeDados = new BancoDados(this);
        personagens = new ArrayList<>();

        ListView listaPersonagens = findViewById(R.id.listViewPersonagens);
        Button btnAdd = findViewById(R.id.btnAdicionar);
        SwitchCompat btnModoEscuro = findViewById(R.id.btnModoEscuro);

        adapter = new PersonagemAdapter(this, personagens);
        listaPersonagens.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, Cadastro.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        btnModoEscuro.setChecked(isDarkMode);
        btnModoEscuro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(DARK_MODE_KEY, isChecked);
            editor.apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        });
    }

    private void carregarPersonagens() {
        bancoDeDados.abrir();
        Cursor cursor = bancoDeDados.listarPersonagens();
        personagens.clear();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(BancoDados.KEY_ID));
            String nome = cursor.getString(cursor.getColumnIndex(BancoDados.KEY_NOME));
            int forca = cursor.getInt(cursor.getColumnIndex(BancoDados.KEY_FORCA));
            int inteligencia = cursor.getInt(cursor.getColumnIndex(BancoDados.KEY_INTELIGENCIA));
            int agilidade = cursor.getInt(cursor.getColumnIndex(BancoDados.KEY_AGILIDADE));
            String classe = cursor.getString(cursor.getColumnIndex(BancoDados.KEY_CLASSE));
            personagens.add(new Personagem(id, nome, forca, inteligencia, agilidade, classe));
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarPersonagens();
        adapter.notifyDataSetChanged();
    }
}