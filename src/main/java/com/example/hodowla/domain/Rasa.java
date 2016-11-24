package com.example.hodowla.domain;

public class Rasa {

    private int rasa_id;
    private String nazwa;
    private String opis;

    public Rasa() { super(); }

    public Rasa(String nazwa, String opis) {
        super();

        this.rasa_id = rasa_id;
        this.nazwa = nazwa;
        this.opis = opis;

    }

    public int getrasa_id() { return rasa_id; }
    public void setrasa_id(int rasa_id) { this.rasa_id = rasa_id; }

    public String getnazwa() { return  nazwa; }
    public void setnazwa(String nazwa) { this.nazwa = nazwa; }

    public String getopis() { return opis; }
    public void setopis(String opis) { this.opis = opis; }
}