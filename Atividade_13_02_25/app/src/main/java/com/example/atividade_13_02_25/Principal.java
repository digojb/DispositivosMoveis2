package com.example.atividade_13_02_25;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Random;

public class Principal extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        Spinner spinner = findViewById(R.id.categoria);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new String[]{"Saúde cap", "Mega-sena", "Party de Natal"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        TextView resultado = findViewById(R.id.resultado);

        Button btnGerar = (Button) findViewById(R.id.btnGerar);

        btnGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinner.getSelectedItem().toString()) {
                    case "Saúde cap":
                        resultado.setText(gerarNumerosAleatorios(5));
                        break;
                    case "Mega-sena":
                        resultado.setText(gerarNumerosAleatorios(6));
                        break;
                    case "Party de Natal":
                        resultado.setText(gerarNumerosAleatorios(2));
                        break;
                }
            }
        });
    }

    private String gerarNumerosAleatorios(int valor) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valor; i++) {
            int numero = random.nextInt(60); // Gera um número entre 0 e 99
            sb.append(numero);
            sb.append(" ");
        }
        return sb.toString();
    }
}
