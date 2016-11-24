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

    @Test
    public void checkDelAllPies(){

        System.out.println("********** TEST USUWANIA **********\n");

        dane.clearPies();

        int piesdb  = dane.getAllPies().size();

        assertEquals(0,piesdb);

        System.out.println("Usuwanie tabeli nastapilo pomyslnie.");

        System.out.println("****** KONIEC TESTU USUWANIA ******\n");

    }

    @Test
    public void checkDelAllRasa(){

        System.out.println("********** TEST USUWANIA **********\n");

        dane.clearRasa();

        int rasadb  = dane.getAllRasa().size();

        assertEquals(0,rasadb);

        System.out.println("Usuwanie tabeli nastapilo pomyslnie.");

        System.out.println("****** KONIEC TESTU USUWANIA ******\n");

    }

    @Test
    public void checkGetAllPies(){

        System.out.println("********** TEST SELECT **********\n");

        dane.clearPies();
        dane.clearRasa();

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Pies pies = new Pies(IMIE_1,ROK_1,DIETA_1);

        assertEquals(1,dane.addRasa(rasa));

        assertEquals(1,dane.addPies(1,pies));

        int piesdb  = dane.getAllPies().size();

        assertEquals(1,piesdb);

        System.out.println("Select tabeli nastapil pomyslnie.");

        System.out.println("****** KONIEC TESTU SELECT ******\n");

    }

    @Test
    public void checkGetAllRasa(){

        System.out.println("********** TEST SELECT **********\n");

        dane.clearRasa();

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Rasa rasa2 = new Rasa(NAME_2,OPIS_2);

        assertEquals(1,dane.addRasa(rasa));
        assertEquals(1,dane.addRasa(rasa2));

        int rasadb  = dane.getAllRasa().size();

        assertEquals(2,rasadb);

        System.out.println("Select tabeli nastapil pomyslnie.");

        System.out.println("****** KONIEC TESTU SELECT ******\n");

    }

    @Test
    public void checkDelRasa(){

        System.out.println("********** TEST DELETE **********\n");

        dane.clearPies();
        dane.clearRasa();

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Rasa rasa2 = new Rasa(NAME_2,OPIS_2);

        assertEquals(1,dane.addRasa(rasa));
        assertEquals(1,dane.addRasa(rasa2));

        List<Rasa> AllRasa = dane.getAllRasa();
        Rasa rasadb = AllRasa.get(dane.getAllRasa().size()-1);
        assertEquals(1, dane.deleteRasa(rasadb.getrasa_id()));
        System.out.println("Rasa o id: " + rasadb.getrasa_id() + " i nazwie: " + rasadb.getnazwa() + " zostala usunieta.");
        System.out.println("****** KONIEC TESTU DELETE ******\n");
    }

    @Test
    public void checkDelPies(){

        System.out.println("********** TEST DELETE **********\n");

        dane.clearPies();
        dane.clearRasa();

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Pies pies = new Pies(IMIE_1,ROK_1,DIETA_1);

        assertEquals(1,dane.addRasa(rasa));
        assertEquals(1,dane.addPies(1,pies));

        List<Pies> AllPies = dane.getAllPies();
        Pies piesdb = AllPies.get(dane.getAllPies().size());
        assertEquals(1, dane.deletePies(piesdb.getpies_id()));
        System.out.println("Pies o id: " + piesdb.getpies_id() + " i nazwie: " + piesdb.getimie() + " zostal usuniety.");
        System.out.println("****** KONIEC TESTU DELETE ******\n");
    }

    @Test
    public void checkDelPiesFromRasa(){

        dane.clearPies();
        dane.clearRasa();

        Rasa rasa = new Rasa(NAME_1,OPIS_1);
        Pies pies = new Pies(IMIE_1,ROK_1,DIETA_1);

        assertEquals(1,dane.addRasa(rasa));
        assertEquals(1,dane.addPies(1,pies));

        System.out.println("********** TEST DELETE **********\n");
        List<Pies> AllPies = dane.getAllPies();
        Pies piesdb = AllPies.get(dane.getAllPies().size());
        assertEquals(1, dane.deletePiesFromRasa(piesdb.getrasa_id()));
        System.out.println("Pies: " + piesdb.getimie() + " zostal usuniety");
        System.out.println("****** KONIEC TESTU DELETE ******\n");
    }

}