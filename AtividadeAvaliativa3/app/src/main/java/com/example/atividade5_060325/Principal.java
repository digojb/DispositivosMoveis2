package com.example.atividade5_060325;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Principal extends Activity {
    private BancoDados bancoDados;
    private TextView valorTextView;
    private TextView qtdTextView;
    private int valorAleatorio;
    private int quantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        bancoDados = new BancoDados(this);
        bancoDados.abrir();

        valorTextView = findViewById(R.id.valor);
        qtdTextView = findViewById(R.id.qtd);

        Button btnInserir = findViewById(R.id.btnInserir);
        Button btnCalcular = findViewById(R.id.btnCalcular);

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarDividaAleatoria();
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularTotalDividas();
            }
        });

        atualizarValoresExibidos();
    }

    private void gerarDividaAleatoria() {
        Random random = new Random();
        valorAleatorio = random.nextInt(1000) + 1;
        quantidade = random.nextInt(10) + 1;
        Toast.makeText(Principal.this, "Valor aleatório: " + valorAleatorio, Toast.LENGTH_SHORT).show();
    }

    private void calcularTotalDividas() {
        bancoDados.insereDivida(valorAleatorio, quantidade);
        atualizarValoresExibidos();
    }

    private void atualizarValoresExibidos() {
        int total = 0;
        int quantidade = 0;

        Cursor cursor = bancoDados.retornaDividas();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int valor = cursor.getInt(cursor.getColumnIndex(BancoDados.KEY_VALOR));
                int qtd = cursor.getInt(cursor.getColumnIndex(BancoDados.KEY_QUANTIDADE));
                total += valor;
                quantidade++;
            }
            cursor.close();
        }

        valorTextView.setText(String.valueOf(total));
        qtdTextView.setText(String.valueOf(quantidade));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bancoDados.fechar();
    }
}