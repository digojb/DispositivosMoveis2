package com.example.atividadeavaliativa1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class Principal extends Activity {

    private FrameLayout touchArea;
    private TextView txtCliques;
    private SharedPreferences preferences;
    private int totalCliques = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        txtCliques = findViewById(R.id.txtCliques);
        touchArea = findViewById(R.id.touchArea);
        ImageButton btnConfig = findViewById(R.id.btnConfig);

        preferences = getSharedPreferences("config", MODE_PRIVATE);
        totalCliques = preferences.getInt("cliques", 0);
        atualizarCliques();

        touchArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    criarCirculos(event.getX(), event.getY());
                    salvarCliques();
                }
                return false;
            }
        });

        btnConfig.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mostrarMenu(v);
                return true;
            }
        });
    }

    private void criarCirculos(float x, float y) {
        int[] tamanhos = {100, 70, 40};
        int[] cores = {Color.RED, Color.parseColor("#FFA500"), Color.BLUE};

        for (int i = 0; i < tamanhos.length; i++) {
            GradientDrawable circleDrawable = new GradientDrawable();
            circleDrawable.setShape(GradientDrawable.OVAL);
            circleDrawable.setColor(Color.TRANSPARENT);
            circleDrawable.setStroke(5, cores[i]);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(tamanhos[i], tamanhos[i]);
            params.leftMargin = (int) x - (tamanhos[i] / 2);
            params.topMargin = (int) y - (tamanhos[i] / 2);
            params.gravity = Gravity.TOP | Gravity.START;

            View circleView = new View(this);
            circleView.setBackground(circleDrawable);
            touchArea.addView(circleView, params);
        }
    }

    private void mostrarMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Limpar");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Limpar")) {
                    limparCliques();
                }
                return true;
            }
        });

        popup.show();
    }

    private void atualizarCliques() {
        txtCliques.setText("Cliques: " + totalCliques);
    }
    private void salvarCliques() {
        totalCliques++;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("cliques", totalCliques);
        editor.apply();
        atualizarCliques();
    }
    private void limparCliques() {
        totalCliques = 0;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("cliques", totalCliques);
        editor.apply();
        atualizarCliques();
    }
}
