package com.example.hodowla.service;

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

public class Dane {

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/workdb";

    private String createRasaTable = "CREATE TABLE Rasa(id bigint GENERATED BY DEFAULT AS IDENTITY, nazwa varchar(20), opis varchar(500))";
    private String createPiesTable = "CREATE TABLE Pies(id bigint GENERATED BY DEFAULT AS IDENTITY, nazwa varchar(20), rok_ur integer, dieta varchar(100), rasa_id int)";

    //Pies
    private PreparedStatement addPiesStmt;
    private PreparedStatement deleteAllPiesStmt;
    private PreparedStatement getAllPiesStmt;
    private PreparedStatement deletePiesStmt;
    private PreparedStatement updatePiesStmt;
    private PreparedStatement getAllPies_idRasaStmt;

    //Rasa
    private PreparedStatement addRasaStmt;
    private PreparedStatement deleteAllRasaStmt;
    private PreparedStatement getAllRasaStmt;
    private PreparedStatement deleteRasaStmt;
    private PreparedStatement updateRasaStmt;

    private PreparedStatement deletePiesFromRasaStmt;

    private Statement statement;

    public Dane() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            ResultSet rsp = connection.getMetaData().getTables(null, null, null,
                    null);
            boolean tablePiesExists = false;
            boolean tableRasaExists = false;
            while (rsp.next()) {
                if ("Pies".equalsIgnoreCase(rsp.getString("TABLE_NAME"))) {
                    tablePiesExists = true;
                    break;
                }

            }
            ResultSet rsr = connection.getMetaData().getTables(null,null,null,null);
            while (rsr.next()){
                if("Rasa".equalsIgnoreCase(rsr.getString("TABLE_NAME"))){
                    tableRasaExists = true;
                    break;
                }
            }

            if (!tableRasaExists)
                statement.executeUpdate(createRasaTable);
            if (!tablePiesExists)
                statement.executeUpdate(createPiesTable);

            //Rasa
            addRasaStmt = connection
                    .prepareStatement("INSERT INTO Rasa (nazwa, opis) VALUES (?, ?)");
            deleteAllRasaStmt = connection
                    .prepareStatement("DELETE FROM Rasa");
            getAllRasaStmt = connection
                    .prepareStatement("SELECT id, nazwa, opis FROM Rasa");
            deleteRasaStmt =  connection
                    .prepareStatement("DELETE FROM Rasa WHERE id = ?");
            updateRasaStmt = connection
                    .prepareStatement("UPDATE Rasa SET nazwa=?, opis=? WHERE id =?");


            //Pies
            addPiesStmt = connection
                    .prepareStatement("INSERT INTO Pies (nazwa, rok_ur, dieta, rasa_id) VALUES (?, ?, ?, ?)");
            deleteAllPiesStmt = connection
                    .prepareStatement("DELETE FROM Pies");
            getAllPiesStmt = connection
                    .prepareStatement("SELECT id, nazwa, rok_ur, dieta, rasa_id FROM Pies");
            getAllPies_idRasaStmt = connection
                    .prepareStatement("SELECT * FROM Pies WHERE rasa_id = ?");
            deletePiesStmt =  connection
                    .prepareStatement("DELETE FROM Pies WHERE id = ?");
            updatePiesStmt = connection
                    .prepareStatement("UPDATE Pies SET nazwa=?, rok_ur=?, dieta=?, rasa_id=? WHERE id =?");

            deletePiesFromRasaStmt = connection
                    .prepareStatement("DELETE FROM Pies WHERE rasa_id = ?");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        return connection;
    }

    void clearPies() {
        try {
            deleteAllPiesStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void clearRasa() {
        try {
            deleteAllRasaStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addRasa(Rasa rasa) {
        int count = 0;
        try {
            addRasaStmt.setString(1, rasa.getnazwa());
            addRasaStmt.setString(2, rasa.getopis());

            count = addRasaStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int addPies(int id, Pies pies) {
        int count = 0;
        try {
            addPiesStmt.setString(1, pies.getimie());
            addPiesStmt.setInt(2, pies.getrok());
            addPiesStmt.setString(3, pies.getdieta());
            addPiesStmt.setInt(4,id);

            count = addPiesStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int deletePies(int id) {
        int count = 0;
        try {
            deletePiesStmt.setInt(1, id);
            count = deletePiesStmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int deleteRasa(int id) {
        int count = 0;
        try {
            deleteRasaStmt.setInt(1, id);
            count = deleteRasaStmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int deletePiesFromRasa(int id) {
        int count = 0;
        try {
            deletePiesFromRasaStmt.setInt(1, id);
            count = deletePiesFromRasaStmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateRasa(int rasa_id, String nazwa, String opis){
        try {
            updateRasaStmt.setString(1,nazwa);
            updateRasaStmt.setString(2,opis);
            updateRasaStmt.setInt(3, rasa_id);
            updateRasaStmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePies(int pies_id, String imie, int rok, String dieta, int rasa_id){
        try {
            updatePiesStmt.setString(1,imie);
            updatePiesStmt.setInt(2,rok);
            updatePiesStmt.setString(3, dieta);
            updatePiesStmt.setInt(4, rasa_id);
            updatePiesStmt.setInt(5,pies_id);
            updatePiesStmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Pies> getAllPies() {
        List<Pies> pies = new ArrayList<Pies>();

        try {
            ResultSet rsp = getAllPiesStmt.executeQuery();

            while (rsp.next()) {
                Pies p = new Pies();
                p.setpies_id(rsp.getInt("id"));
                p.setimie(rsp.getString("nazwa"));
                p.setrok(rsp.getInt("rok_ur"));
                p.setdieta(rsp.getString("dieta"));
                p.setrasa_id(rsp.getInt("rasa_id"));
                pies.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pies;
    }

    public List<Rasa> getAllRasa() {
        List<Rasa> rasa = new ArrayList<Rasa>();

        try {
            ResultSet rsr = getAllRasaStmt.executeQuery();

            while (rsr.next()) {
                Rasa r = new Rasa();
                r.setrasa_id(rsr.getInt("id"));
                r.setnazwa(rsr.getString("nazwa"));
                r.setopis(rsr.getString("opis"));
                rasa.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rasa;
    }

    public List<Pies> getAllPies_idRasa(int rasa_id) {
        List<Pies> pies = new ArrayList<Pies>();

        try {
            getAllPies_idRasaStmt.setInt(1,rasa_id);
            ResultSet rs = getAllPies_idRasaStmt.executeQuery();

            while (rs.next()) {
                Pies p = new Pies();
                p.setpies_id(rs.getInt("id"));
                p.setimie(rs.getString("nazwa"));
                p.setrok(rs.getInt("rok_ur"));
                p.setdieta(rs.getString("dieta"));
                p.setrasa_id(rs.getInt("rasa_id"));
                pies.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pies;
    }

}
