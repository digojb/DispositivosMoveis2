package com.example.atividadeavaliativa2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends Activity {

    private BancoDados bancoDeDados;
    private EditText editNome, editForca, editInteligencia, editAgilidade;
    private Spinner spinnerClasse;
    private Personagem personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro);

        bancoDeDados = new BancoDados(this);

        editNome = findViewById(R.id.editNome);
        editForca = findViewById(R.id.editForca);
        editInteligencia = findViewById(R.id.editInteligencia);
        editAgilidade = findViewById(R.id.editAgilidade);
        spinnerClasse = findViewById(R.id.spinner_classe);

        String[] classes = {"Bárbaro", "Ladino", "Mago", "Necromante", "Paladino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClasse.setAdapter(adapter);

        personagem = (Personagem) getIntent().getSerializableExtra("personagem");
        if (personagem != null) {
            editNome.setText(personagem.getNome());
            editForca.setText(String.valueOf(personagem.getForca()));
            editInteligencia.setText(String.valueOf(personagem.getInteligencia()));
            editAgilidade.setText(String.valueOf(personagem.getAgilidade()));
            int position = adapter.getPosition(personagem.getClasse());
            spinnerClasse.setSelection(position);
        }

        Button btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> {
            salvarPersonagem();
            bancoDeDados.fechar();
            finish();
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void salvarPersonagem() {
        if (personagem != null) {
            bancoDeDados.atualizarPersonagem(personagem.getId(),
                    editNome.getText().toString(),
                    Integer.parseInt(editForca.getText().toString()),
                    Integer.parseInt(editInteligencia.getText().toString()),
                    Integer.parseInt(editAgilidade.getText().toString()),
                    spinnerClasse.getSelectedItem().toString());
            return;
        } else {
            String nome = editNome.getText().toString();
            String forca = editForca.getText().toString();
            String inteligencia = editInteligencia.getText().toString();
            String agilidade = editAgilidade.getText().toString();
            String classe = spinnerClasse.getSelectedItem().toString();

            if (nome.isEmpty() || forca.isEmpty() || inteligencia.isEmpty() || agilidade.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bancoDeDados.inserePersonagem(nome, Integer.parseInt(forca), Integer.parseInt(inteligencia), Integer.parseInt(agilidade), classe);
            }
        }
    }
}
