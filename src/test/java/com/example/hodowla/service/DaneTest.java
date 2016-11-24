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

    private final static String NAME_1 = "dalmatynczyk";
    private final static String OPIS_1 = "Jest to pies odwazny, czujny, zrownowazony. Wykazuje wysoki stopien przywiazania do czlonkow rodziny. Aktywny i towarzyski. Dalmatynczyk nie jest psem odpowiednim dla malo ruchliwych osob";

    private final static String NAME_2 = "husky";
    private final static String OPIS_2 = "Wspolczesnie husky syberyjski jest wykorzystywany, tak jak i dawniej, jako pies zaprzegowy, takze jako pies rodzinny.";

    @Test
    public void checkConnection(){
        assertNotNull(dane.getConnection());
    }

    @Test
    public void checkAddRasa(){

        System.out.println("********** TEST DODAWANIA **********\n");

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Rasa rasa2 = new Rasa(NAME_2,OPIS_2);
        dane.clearPies();
        dane.clearRasa();

        assertEquals(1,dane.addRasa(rasa));
        System.out.println("Rasa " + rasa.getnazwa() + " zostala dodana do bazy.");

        List<Rasa> AllRasa = dane.getAllRasa();
        Rasa rasadb = AllRasa.get(dane.getAllRasa().size());

        assertEquals(NAME_1, rasadb.getnazwa());
        assertEquals(OPIS_1, rasadb.getopis());
        System.out.println("Otrzymana rasa z bazy o nazwie: " + rasadb.getnazwa() + " jest poprawna.");

        assertEquals(1,dane.addRasa(rasa2));
        System.out.println("Rasa " + rasa2.getnazwa() + " zostala dodana do bazy.");

        List<Rasa> AllRasa2 = dane.getAllRasa();
        Rasa rasadb2 = AllRasa2.get(dane.getAllRasa().size());

        assertEquals(NAME_2, rasadb2.getnazwa());
        assertEquals(OPIS_2, rasadb2.getopis());
        System.out.println("Otrzymana rasa z bazy o nazwie: " + rasadb2.getnazwa() + " jest poprawna.");


        System.out.println("****** KONIEC TESTU DODAWANIA ******\n");
    }

    @Test
    public void checkAddPies(){
        System.out.println("********** TEST DODAWANIA **********\n");

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Pies pies = new Pies(IMIE_1,ROK_1,DIETA_1);

        dane.clearPies();
        dane.clearRasa();

        assertEquals(1,dane.addRasa(rasa));

        assertEquals(1,dane.addPies(1,pies));
        System.out.println("Pies " + pies.getimie() + " zostal dodany do bazy.");

        List<Pies> AllPies = dane.getAllPies();
        Pies piesdb  = AllPies.get(dane.getAllPies().size());

        assertEquals(IMIE_1, piesdb.getimie());
        assertEquals(ROK_1, piesdb.getrok());
        assertEquals(DIETA_1, piesdb.getdieta());
        System.out.println("Otrzymany pies z bazy o nazwie: " + piesdb.getimie() + " jest poprawny.");

        System.out.println("****** KONIEC TESTU DODAWANIA ******\n");

    }

}