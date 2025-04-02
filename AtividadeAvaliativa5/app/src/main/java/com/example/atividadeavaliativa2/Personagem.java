package com.example.atividadeavaliativa2;

import java.io.Serializable;

public class Personagem implements Serializable {
    private long id;
    private String nome;
    private int forca;
    private int inteligencia;
    private int agilidade;
    private String classe;

    public Personagem(long id, String nome, int forca, int inteligencia, int agilidade, String classe) {
        this.id = id;
        this.nome = nome;
        this.forca = forca;
        this.inteligencia = inteligencia;
        this.agilidade = agilidade;
        this.classe = classe;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getForca() {
        return forca;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public int getAgilidade() {
        return agilidade;
    }

    public String getClasse() {
        return classe;
    }
}
