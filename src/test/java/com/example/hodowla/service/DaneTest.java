package com.example.hodowla.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.example.hodowla.domain.Pies;
import com.example.hodowla.domain.Rasa;

public class DaneTest {

    Dane dane = new Dane();

    private final static String IMIE_1 = "Puszek";
    private final static int ROK_1 = 2010;
    private final static String DIETA_1 = "Chrupki Chappy";

    private final static String IMIE_2 = "Reksio";
    private final static int ROK_2 = 2016;
    private final static String DIETA_2 = "Karma Royal Canin";

    private final static String NAME_1 = "dalmatynczyk";
    private final static String OPIS_1 = "Jest to pies odwazny, czujny, zrownowazony. Wykazuje wysoki stopien przywiazania do czlonkow rodziny. Aktywny i towarzyski. Dalmatynczyk nie jest psem odpowiednim dla malo ruchliwych osob";

    private final static String NAME_2 = "husky";
    private final static String OPIS_2 = "Wspolczesnie husky syberyjski jest wykorzystywany, tak jak i dawniej, jako pies zaprzegowy, takze jako pies rodzinny.";

    @Test
    public void checkConnection(){
        assertNotNull(Dane.getConnection());
    }

    @Test
    public void checkAdd(){

        System.out.println("********** TEST DODAWANIA **********\n");

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Rasa rasa2 = new Rasa(NAME_2,OPIS_2);
        Dane.clearPies();
        Dane.clearRasa();

        assertEquals(1,Dane.addRasa(rasa));
        System.out.println("Rasa " + rasa.getnazwa() + " zostala dodana do bazy.");

        List<Rasa> AllRasa = Dane.getAllRasa();
        Rasa rasadb = AllRasa.get(Dane.getAllRasa().size());

        assertEquals(NAME_1, rasadb.getnazwa());
        assertEquals(OPIS_1, rasadb.getopis());
        System.out.println("Otrzymana rasa z bazy o nazwie: " + rasadb.getnazwa() + " jest poprawna.");

        assertEquals(1,Dane.addRasa(rasa2));
        System.out.println("Rasa " + rasa2.getnazwa() + " zostala dodana do bazy.");

        List<Rasa> AllRasa2 = Dane.getAllRasa();
        Rasa rasadb2 = AllRasa2.get(Dane.getAllRasa().size());

        assertEquals(NAME_2, rasadb2.getnazwa());
        assertEquals(OPIS_2, rasadb2.getopis());
        System.out.println("Otrzymana rasa z bazy o nazwie: " + rasadb2.getnazwa() + " jest poprawna.");


        System.out.println("****** KONIEC TESTU DODAWANIA ******\n");
    }

}