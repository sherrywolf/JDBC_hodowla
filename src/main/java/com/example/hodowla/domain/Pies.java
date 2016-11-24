package com.example.hodowla.domain;

public class Pies {

    private int pies_id;
    private String imie;
    private int rok;
    private String dieta;
    private int rasa_id;

    public Pies() {
        super();
    }

    public Pies(String imie, int rok, String dieta) {
        super();

        this.pies_id = pies_id;
        this.imie = imie;
        this.rok = rok;
        this.dieta = dieta;
        this.rasa_id = rasa_id;
    }

    public int getpies_id() { return pies_id; }

    public void setpies_id(int pies_id) {
        this.pies_id = pies_id;
    }

    public String getimie() {
        return imie;
    }

    public void setimie(String imie) {
        this.imie = imie;
    }

    public int getrok() {
        return rok;
    }

    public void setrok(int rok) {
        this.rok = rok;
    }

    public String getdieta() {
        return dieta;
    }

    public void setdieta(String dieta) {
        this.dieta = dieta;
    }

    public int getrasa_id() {
        return rasa_id;
    }

    public void setrasa_id(int rasa_id) {
        this.rasa_id = rasa_id;
    }
}
