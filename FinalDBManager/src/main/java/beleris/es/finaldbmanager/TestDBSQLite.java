/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package beleris.es.finaldbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Emilio David Diaus López 2008-2026
 */
public class TestDBSQLite {

    static void ExecuteQueryTableDBWithOptions(String CreateTablesAnalStat, Connection cnConn) {

        String Statement = CreateTablesAnalStat;
        Statement stsSQL_Statement = null;
        boolean bRes = false;

        try {
            stsSQL_Statement = cnConn.createStatement();
            bRes = stsSQL_Statement.execute(Statement);
            stsSQL_Statement.close();

        } catch (SQLException ex) { // ignore }
            bRes = false;
            System.out.println("Statement: " + ex.getLocalizedMessage());

        }
        try {
            if (stsSQL_Statement != null) {
                stsSQL_Statement.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }
    //  final String SQLite_ServerName = "jdbc:sqlite:";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection cnConn = null;
        try {

            DriverManager.registerDriver(new org.sqlite.JDBC());
            cnConn = DriverManager.getConnection("jdbc:sqlite:test.db");
            if (cnConn != null) {
                var meta = cnConn.getMetaData();
                System.out.println(meta.getDriverName() + " driver initialized");
                //System.out.println("A new database has been created.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        String CreateTablesAnalStat = "CREATE TABLE IF NOT EXISTS authorities ("
                + "  id_authorities INTEGER PRIMARY KEY,"
                + "  pricauthor text NOT NULL,"
                + "  otherauthor text NOT NULL"
                + ");";
        ExecuteQueryTableDBWithOptions(CreateTablesAnalStat, cnConn);
        try {
            if (cnConn != null) {
                cnConn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

}
