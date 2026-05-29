/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finaldbmanager;

import beleris.es.finalinformationmanager.LoggingManagerGenerator;
import static beleris.es.finalinformationmanager.MainErrorManager.ShowDError;
import beleris.es.finalprimaryclasses.CLAllObjectList;
import beleris.es.finalprimaryclasses.CLDBColumnsList;
import beleris.es.finalprimaryclasses.CL_DBCV;
import beleris.es.finalprimaryclasses.CL_DBColumns;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Properties;
import java.util.logging.Level;
import org.openide.util.Exceptions;

/**
 *
 * @author Emilio David Diaus López 2008-2026
 */
public class FDBMan {

    /**
     *
     */
    public static final int DB_MYSQL_Selected = 0;

    /**
     *
     */
    public static final int DB_SQLite_Selected = 1;

    /**
     *
     */
    public static final int DB_PostgreSQL_Selected = 2;
    private int idbtouseselected = DB_SQLite_Selected;
    private Connection cnConn;
    private int ierrCode;
    private String serrSQLMessage;
    private String serrSQLState;
    private ResultSet rsCResSet;
    private String sMySQL_DefaultServerNConn = "localhost";
    private int iMySQL_PortNConn = 3306;
    private MysqlConnectionPoolDataSource mcpdsPoolDataSource;
    private final String MySQL_ServerName = "jdbc:mysql://localhost:3306";
    private final String PostgreSQL_ServerName = "jdbc:postgresql://localhost";
    private final String SQLite_ServerName = "jdbc:sqlite:";
    private PreparedStatement stsSQL_Statement;
    String sDBUser = "";
    String sDBPass = "";
    private String sDBName;
    private boolean bError = false;

    /**
     *
     * @param Url
     * @param sTmpDBPass
     * @param sTmpDBName
     * @param sTmpDBUser
     * @param idbtouse
     */
    public FDBMan(String Url, String sTmpDBPass, String sTmpDBName, String sTmpDBUser, int idbtouse) {
        this.sDBPass = sTmpDBPass;
        this.sDBUser = sTmpDBUser;
        this.sDBName = sTmpDBName;
        idbtouseselected = idbtouse;
        switch (idbtouseselected) {

            case DB_MYSQL_Selected -> {
                MySql_DSPoolInitSimple(Url, this.sDBName, this.sDBUser, this.sDBPass);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_DSPoolInitSimple(Url, this.sDBName, this.sDBUser, this.sDBPass);
            }
            case DB_SQLite_Selected -> {
                SQLite_DriverInit(this.sDBName);
            }
            default -> {
                MySql_DSPoolInitSimple(Url, this.sDBName, this.sDBUser, this.sDBPass);
            }
        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param sFieldtoSearch
     * @param sFieldValuetoSearch
     */
    public void SelectSingleResult(String sTmpDBTable, String sFieldtoSearch, String sFieldValuetoSearch) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_SelectSingleResult(sTmpDBTable, sFieldtoSearch, sFieldValuetoSearch);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_SelectSingleResult(sTmpDBTable, sFieldtoSearch, sFieldValuetoSearch);
            }
            case DB_SQLite_Selected -> {
                CLAllObjectList<CL_DBCV> FieldList = new CLAllObjectList<>();
                FieldList.add(new CL_DBCV(sFieldtoSearch, sFieldValuetoSearch));
                SQLite_SelectManyResults(sTmpDBTable, FieldList);
            }
            default -> {
                MySQL_SelectSingleResult(sTmpDBTable, sFieldtoSearch, sFieldValuetoSearch);
            }
        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param sFieldtoSearch
     * @param sFieldValuetoSearch
     */
    public void MySQL_SelectSingleResult(String sTmpDBTable, String sFieldtoSearch, String sFieldValuetoSearch) {

        //  String sSQLQueryStr = "SELECT * FROM `" + AntiSQLInjection(sTmpDBTable) + "` WHERE  " + AntiSQLInjection(sFieldtoSearch)
        //        + "='" + AntiSQLInjection(sFieldValuetoSearch) + "';";
        setbError(false);
        String sMySQL_Statement = "SELECT * FROM " + sTmpDBTable + " WHERE " + sFieldtoSearch + "=?";

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            stsSQL_Statement.setString(1, sFieldValuetoSearch);
            //  rsCResSet = stsSQL_Statement.executeQuery(sSQLQueryStr);
            setRsCResSet(stsSQL_Statement.executeQuery());
//            rsCResSet.next();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al seleccionar datos de la tabla:" + sTmpDBTable);
            setRsCResSet(null);
        } finally {
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param sFieldtoSearch
     * @param sFieldValuetoSearch
     */
    public void PostgreSQL_SelectSingleResult(String sTmpDBTable, String sFieldtoSearch, String sFieldValuetoSearch) {

        //  String sSQLQueryStr = "SELECT * FROM `" + AntiSQLInjection(sTmpDBTable) + "` WHERE  " + AntiSQLInjection(sFieldtoSearch)
        //        + "='" + AntiSQLInjection(sFieldValuetoSearch) + "';";
        setbError(false);
        String sMySQL_Statement = "SELECT * FROM " + sTmpDBTable + " WHERE " + sFieldtoSearch + "=?";

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            stsSQL_Statement.setString(1, sFieldValuetoSearch);
            //  rsCResSet = stsSQL_Statement.executeQuery(sSQLQueryStr);
            setRsCResSet(stsSQL_Statement.executeQuery());
//            rsCResSet.next();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al seleccionar datos de la tabla:" + sTmpDBTable);
            setRsCResSet(null);
        } finally {
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param sFieldtoSearch
     * @param sFieldValuetoSearch
     * @param DBParametersEnter
     * @param DBParametersReturn
     */
    public void SelectManyResults(String sTmpDBTable, String sFieldtoSearch, String sFieldValuetoSearch, CLAllObjectList<String> DBParametersEnter, CLAllObjectList<String> DBParametersReturn) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_SelectManyResults(sTmpDBTable, sFieldtoSearch, sFieldValuetoSearch, DBParametersEnter, DBParametersReturn);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_SelectManyResults(sTmpDBTable, sFieldtoSearch, sFieldValuetoSearch, DBParametersEnter, DBParametersReturn);
            }
            case DB_SQLite_Selected -> {
                CLAllObjectList<CL_DBCV> FieldList = new CLAllObjectList<>();
                FieldList.add(new CL_DBCV(sFieldtoSearch, sFieldValuetoSearch));
                SQLite_SelectManyResults(sTmpDBTable, FieldList);
            }
            default -> {
                MySQL_SelectManyResults(sTmpDBTable, sFieldtoSearch, sFieldValuetoSearch, DBParametersEnter, DBParametersReturn);
            }
        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param FieldList
     */
    public void SQLite_SelectManyResults(String sTmpDBTable, CLAllObjectList<CL_DBCV> FieldList) {
        try {
            var sSQLite_Statement = "SELECT ";
            int iindex = 0;
            int itotalsize = FieldList.size();
            while (iindex < itotalsize) {
                CL_DBCV Field = FieldList.get(iindex);
                String sField = Field.getsTableDBField();
                sSQLite_Statement = sSQLite_Statement + "" + sField;
                iindex++;
                if (iindex < itotalsize) {
                    sSQLite_Statement = sSQLite_Statement + ",";
                }
            }

            sSQLite_Statement = sSQLite_Statement + " FROM" + sTmpDBTable + ";";

            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmntStatement = cnConn.createStatement();
            // create a new table
            stmntStatement.executeQuery(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al seleccionar datos de la tabla:" + sTmpDBTable);
        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param sFieldtoSearch
     * @param sFieldValuetoSearch
     * @param DBParametersEnter
     * @param DBParametersReturn
     */
    public void MySQL_SelectManyResults(String sTmpDBTable, String sFieldtoSearch, String sFieldValuetoSearch, CLAllObjectList<String> DBParametersEnter, CLAllObjectList<String> DBParametersReturn) {

        //  String sSQLQueryStr = "SELECT * FROM `" + AntiSQLInjection(sTmpDBTable) + "` WHERE  " + AntiSQLInjection(sFieldtoSearch)
        //        + "='" + AntiSQLInjection(sFieldValuetoSearch) + "';";
        setbError(false);
        String sMySQL_Statement = "SELECT * FROM " + this.sDBName + "." + sTmpDBTable + " WHERE " + sFieldtoSearch + "=?";

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            stsSQL_Statement.setString(1, sFieldValuetoSearch);
            //  rsCResSet = stsSQL_Statement.executeQuery(sSQLQueryStr);
            setRsCResSet(stsSQL_Statement.executeQuery());
            if (getRsCResSet().next()) {
                if (DBParametersEnter != null) {
                    String DBCampo = null, sDBSetResult = null;
                    int iindex = 1, iLastpar = 0;
                    iLastpar = DBParametersEnter.size();
                    for (iindex = 1; iindex <= iLastpar; iindex++) {
                        DBCampo = DBParametersEnter.get(iindex - 1);
                        if (DBCampo != null) {
                            sDBSetResult = getRsCResSet().getString(DBCampo);
                            DBParametersReturn.add(sDBSetResult);
                        }
                    }
                }
            }

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al seleccionar datos de la tabla:" + sTmpDBTable);
        } finally {
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param sFieldtoSearch
     * @param sFieldValuetoSearch
     * @param DBParametersEnter
     * @param DBParametersReturn
     */
    public void PostgreSQL_SelectManyResults(String sTmpDBTable, String sFieldtoSearch, String sFieldValuetoSearch, CLAllObjectList<String> DBParametersEnter, CLAllObjectList<String> DBParametersReturn) {

        //  String sSQLQueryStr = "SELECT * FROM `" + AntiSQLInjection(sTmpDBTable) + "` WHERE  " + AntiSQLInjection(sFieldtoSearch)
        //        + "='" + AntiSQLInjection(sFieldValuetoSearch) + "';";
        setbError(false);
        String sMySQL_Statement = "SELECT * FROM " + this.sDBName + "." + sTmpDBTable + " WHERE " + sFieldtoSearch + "=?";

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            stsSQL_Statement.setString(1, sFieldValuetoSearch);
            //  rsCResSet = stsSQL_Statement.executeQuery(sSQLQueryStr);
            setRsCResSet(stsSQL_Statement.executeQuery());
            if (getRsCResSet().next()) {
                if (DBParametersEnter != null) {
                    String DBCampo = null, sDBSetResult = null;
                    int iindex = 1, iLastpar = 0;
                    iLastpar = DBParametersEnter.size();
                    for (iindex = 1; iindex <= iLastpar; iindex++) {
                        DBCampo = DBParametersEnter.get(iindex - 1);
                        if (DBCampo != null) {
                            sDBSetResult = getRsCResSet().getString(DBCampo);
                            DBParametersReturn.add(sDBSetResult);
                        }
                    }
                }
            }

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al seleccionar datos de la tabla:" + sTmpDBTable);
        } finally {
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param FieldList
     */
    public void CreateTable(String sTmpDBTable, CLAllObjectList<CL_DBCV> FieldList) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_CreateTableWithFields(sTmpDBTable, FieldList);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_CreateTableWithFields(sTmpDBTable, FieldList);
            }
            case DB_SQLite_Selected -> {
                SQLlite_CreateTableWithFields(sTmpDBTable, FieldList);
            }
            default -> {
                MySQL_CreateTableWithFields(sTmpDBTable, FieldList);
            }

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void CreateTable(String sTmpDBTable) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_CreateTable(sTmpDBTable);
            }
            case DB_SQLite_Selected -> {
                SQLite_CreateTable(sTmpDBTable);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_CreateTable(sTmpDBTable);
            }

            default -> {
                MySQL_CreateTable(sTmpDBTable);
            }

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void CreateDB(String sTmpDBTable) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_CreateDB(sTmpDBTable);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_CreateDB(sTmpDBTable);
            }
            case DB_SQLite_Selected -> {
                SQLite_CreateDB(sTmpDBTable);
            }
            default -> {
                MySQL_CreateDB(sTmpDBTable);
            }

        }
    }

    /**
     *
     * @param stmpDBTable
     */
    public void MySQL_CreateDB(String stmpDBTable) {

        String sSQLite_Statement = "CREATE DATABASE  IF NOT EXISTS `" + stmpDBTable + "`;";

        boolean bRes = false;
        setbError(false);

        try {
            stsSQL_Statement = cnConn.prepareStatement(sSQLite_Statement);

            bRes = stsSQL_Statement.execute();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al crear tabla:" + stmpDBTable);
        } finally {
            bRes = false;
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param stmpDBTable
     */
    public void PostgreSQL_CreateDB(String stmpDBTable) {

        String sSQLite_Statement = "CREATE DATABASE  IF NOT EXISTS `" + stmpDBTable + "`;";

        boolean bRes = false;
        setbError(false);

        try {
            stsSQL_Statement = cnConn.prepareStatement(sSQLite_Statement);

            bRes = stsSQL_Statement.execute();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al crear tabla:" + stmpDBTable);
        } finally {
            bRes = false;
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void SQLite_CreateDB(String sTmpDBTable) {

        String sSQLite_Statement = "CREATE DATABASE  IF NOT EXISTS `" + sTmpDBTable + "`;";

        boolean bRes = false;
        setbError(false);

        try {
            stsSQL_Statement = cnConn.prepareStatement(sSQLite_Statement);

            bRes = stsSQL_Statement.execute();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al crear tabla:" + sTmpDBTable);
        } finally {
            bRes = false;
            SQLite_CloseConn();

        }
    }

    /**
     *
     * @param sDB
     * @return
     */
    public boolean DBTableExist(String sDB) {
        boolean result = false;
        return result;
    }

    /**
     *
     * @param sTmpDBTable
     * @param FieldList
     */
    public void SQLlite_CreateTableWithFields(String sTmpDBTable, CLAllObjectList<CL_DBCV> FieldList) {
        try {
            var sSQLite_Statement = "CREATE TABLE IF NOT EXISTS " + sTmpDBTable + "("
                    + "	id " + sTmpDBTable + " INTEGER PRIMARY KEY,";
            int iindex = 0;
            int itotalsize = FieldList.size();
            while (iindex < itotalsize) {
                CL_DBCV Field = FieldList.get(iindex);
                String sField = Field.getsTableDBField();
                String sValue = Field.getsValue();
                sSQLite_Statement = sSQLite_Statement + "" + sField + " " + sValue;
                iindex++;
                if (iindex < itotalsize) {
                    sSQLite_Statement = sSQLite_Statement + ",";
                }
            }

            sSQLite_Statement = sSQLite_Statement + ");";

            // cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName+);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.execute(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al crear tabla con campos:" + sTmpDBTable);
        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param FieldList
     */
    public void MySQL_CreateTableWithFields(String sTmpDBTable, CLAllObjectList<CL_DBCV> FieldList) {

        var sMySQL_Statement = "CREATE TABLE IF NOT EXISTS " + sTmpDBTable + "("
                + "	id " + sTmpDBTable + " INTEGER PRIMARY KEY,";
        int iindex = 0;
        int itotalsize = FieldList.size();
        while (iindex < itotalsize) {
            CL_DBCV Field = FieldList.get(iindex);
            String sField = Field.getsTableDBField();
            String sValue = Field.getsValue();
            sMySQL_Statement = sMySQL_Statement + "" + sField + " " + sValue;
            iindex++;
            if (iindex < itotalsize) {
                sMySQL_Statement = sMySQL_Statement + ",";
            }
        }

        boolean bRes = false;
        setbError(false);

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);

            bRes = stsSQL_Statement.execute();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al crear tabla:" + sTmpDBTable);
        } finally {
            bRes = false;
            MySQL_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param FieldList
     */
    public void PostgreSQL_CreateTableWithFields(String sTmpDBTable, CLAllObjectList<CL_DBCV> FieldList) {

        var sMySQL_Statement = "CREATE TABLE IF NOT EXISTS " + sTmpDBTable + "("
                + "	id " + sTmpDBTable + " INTEGER PRIMARY KEY,";
        int iindex = 0;
        int itotalsize = FieldList.size();
        while (iindex < itotalsize) {
            CL_DBCV Field = FieldList.get(iindex);
            String sField = Field.getsTableDBField();
            String sValue = Field.getsValue();
            sMySQL_Statement = sMySQL_Statement + "" + sField + " " + sValue;
            iindex++;
            if (iindex < itotalsize) {
                sMySQL_Statement = sMySQL_Statement + ",";
            }
        }

        boolean bRes = false;
        setbError(false);

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);

            bRes = stsSQL_Statement.execute();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al crear tabla:" + sTmpDBTable);
        } finally {
            bRes = false;
            PostgreSQL_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param FieldList
     */
    public void UpdateTable(String sTmpDBTable, CLAllObjectList<CL_DBCV> FieldList) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_UpdateTable(sTmpDBTable, FieldList, null, null);
            }
            case DB_SQLite_Selected -> {
                SQLite_UpdateTable(sTmpDBTable, FieldList);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_UpdateTable(sTmpDBTable, FieldList, null, null);
            }
            default -> {
                MySQL_UpdateTable(sTmpDBTable, FieldList, null, null);
            }

        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param FieldList
     */
    public void SQLite_UpdateTable(String sTmpDBTable, CLAllObjectList<CL_DBCV> FieldList) {
        try {
            var sSQLite_Statement = "UPDATE " + sTmpDBTable + " SET ";
            int iindex = 0;
            int itotalsize = FieldList.size();
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            while (iindex < itotalsize) {
                CL_DBCV Field = FieldList.get(iindex);
                String sField = Field.getsTableDBField();
                String sValue = Field.getsValue();
                sSQLite_Statement = sSQLite_Statement + "" + sField + " " + sValue;
                iindex++;
                if (iindex < itotalsize) {
                    sSQLite_Statement = sSQLite_Statement + ",";
                }
            }
            sSQLite_Statement = sSQLite_Statement + ";";
            iindex = 0;
            var stmt = cnConn.prepareStatement(sSQLite_Statement);
            while (iindex < itotalsize) {
                CL_DBCV Field = FieldList.get(iindex);
                String sValue = Field.getsValue();
                iindex++;
                stmt.setString(iindex + 1, sValue);
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {

            ShowSQLExceptionError(ex, " Error al actualizar datos en la base de datos desde el panel:" + sTmpDBTable);
        }

    }

    /**
     *
     * @param sTempDBTable
     * @param DBParameters
     * @param sIndex
     * @param siIndexItem
     */
    public void MySQL_UpdateTable(String sTempDBTable, CLAllObjectList<CL_DBCV> DBParameters, String sIndex, String siIndexItem) {

        String sMySQL_Statement = "UPDATE " + this.sDBName + "." + sTempDBTable + " SET ";
        ListIterator<CL_DBCV> mlIterador = DBParameters.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += Elemento.getsTableDBField() + "=?";
            if (mlIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += " WHERE " + this.sDBName + "." + sTempDBTable + "." + sIndex + " = " + siIndexItem;
        /*   if (DBParameters != null) {
            mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                sMySQL_Statement += Elemento.getsTableDBField() + "=?";
                if (mlIterador.hasNext()) {
                    sMySQL_Statement += " AND ";
                }
            }*/
        sMySQL_Statement += " ;";
        int iindex = 1;
        mlIterador = DBParameters.listIterator();

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsValue());
                iindex++;
            }
            int iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al actualizar datos en la base de datos desde el panel:" + sTempDBTable);

        } finally {
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTempDBTable
     * @param DBParameters
     * @param sIndex
     * @param siIndexItem
     */
    public void PostgreSQL_UpdateTable(String sTempDBTable, CLAllObjectList<CL_DBCV> DBParameters, String sIndex, String siIndexItem) {

        String sMySQL_Statement = "UPDATE " + this.sDBName + "." + sTempDBTable + " SET ";
        ListIterator<CL_DBCV> mlIterador = DBParameters.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += Elemento.getsTableDBField() + "=?";
            if (mlIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += " WHERE " + this.sDBName + "." + sTempDBTable + "." + sIndex + " = " + siIndexItem;
        /*   if (DBParameters != null) {
            mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                sMySQL_Statement += Elemento.getsTableDBField() + "=?";
                if (mlIterador.hasNext()) {
                    sMySQL_Statement += " AND ";
                }
            }*/
        sMySQL_Statement += " ;";
        int iindex = 1;
        mlIterador = DBParameters.listIterator();

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsValue());
                iindex++;
            }
            int iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al actualizar datos en la base de datos desde el panel:" + sTempDBTable);

        } finally {
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTempDBTable
     */
    public void DeleteDataTable(String sTempDBTable) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                FDBMan.this.MySQL_DeleteDataTable(sTempDBTable);
            }
            case DB_SQLite_Selected -> {
                SQLite_DeleteDataTable(sTempDBTable);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_DeleteDataTable(sTempDBTable);
            }
            default -> {
                FDBMan.this.MySQL_DeleteDataTable(sTempDBTable);
            }

        }
    }

    /**
     *
     * @param sTempDBTable
     * @param DBField
     */
    public void DeleteDataTable(String sTempDBTable, CL_DBCV DBField) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_DeleteDataTable(sTempDBTable, DBField);
            }
            case DB_SQLite_Selected -> {
                SQLite_DeleteDataTable(sTempDBTable, DBField);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_DeleteDataTable(sTempDBTable, DBField);
            }

            default -> {
                FDBMan.this.MySQL_DeleteDataTable(sTempDBTable, DBField);
            }

        }
    }

    /**
     *
     * @param sTempDBTable
     * @param Campo
     */
    public void MySQL_DeleteDataTable(String sTempDBTable, CL_DBCV Campo) {

        String sMySQL_Statement = "DELETE FROM " + sTempDBTable + " WHERE " + Campo.getsTableDBField() + " = ?;";
        //idfi_lista_control_acceso = ?";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            stsSQL_Statement.setString(1, Campo.getsValue());

            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar datos de la tabla:" + sTempDBTable);
        } finally {
            iRes = 0;
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTempDBTable
     */
    public void MySQL_DeleteDataTable(String sTempDBTable) {

        String sMySQL_Statement = "DELETE FROM " + sTempDBTable + ";";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar datos de la tabla:" + sTempDBTable);
        } finally {
            iRes = 0;
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTempDBTable
     * @param Campo
     */
    public void PostgreSQL_DeleteDataTable(String sTempDBTable, CL_DBCV Campo) {

        String sMySQL_Statement = "DELETE FROM " + sTempDBTable + " WHERE " + Campo.getsTableDBField() + " = ?;";
        //idfi_lista_control_acceso = ?";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            stsSQL_Statement.setString(1, Campo.getsValue());

            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar datos de la tabla:" + sTempDBTable);
        } finally {
            iRes = 0;
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTempDBTable
     */
    public void PostgreSQL_DeleteDataTable(String sTempDBTable) {

        String sMySQL_Statement = "DELETE FROM " + sTempDBTable + ";";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar datos de la tabla:" + sTempDBTable);
        } finally {
            iRes = 0;
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTempDBTable
     */
    public void SQLite_DeleteDataTable(String sTempDBTable) {
        try {
            var sSQLite_Statement = "DELETE FROM " + this.sDBName + "." + sTempDBTable + ";";
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.executeQuery(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al borrar datos de la tabla:" + sTempDBTable);
        }

    }

    /**
     *
     * @param sTempDBTable
     * @param Campo
     */
    public void SQLite_DeleteDataTable(String sTempDBTable, CL_DBCV Campo) {
        try {
            var sSQLite_Statement = "DELETE FROM " + sTempDBTable + " WHERE " + Campo.getsTableDBField() + " = ?;";
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.executeQuery(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al borrar datos de la tabla:" + sTempDBTable);
        }

    }

    /**
     *
     * @param sTempDBTable
     * @param Columns
     * @param InsertFields
     */
    public void InsertDatainDB(String sTempDBTable, ArrayList<String> Columns, CLAllObjectList<CL_DBCV> InsertFields) {
        switch (getDbusingpar()) {
            case DB_MYSQL_Selected -> {
                MySQL_InsertDatainDB(sTempDBTable, Columns, InsertFields);
            }
            case DB_SQLite_Selected -> {
                SQLite_InsertDatainDB(sTempDBTable, Columns, InsertFields);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_InsertDatainDB(sTempDBTable, Columns, InsertFields);
            }
            default -> {
                MySQL_InsertDatainDB(sTempDBTable, Columns, InsertFields);
            }

        }
    }

    /**
     *
     * @param sTempDBTable
     * @param InsertFields
     */
    public void InsertDatainDB(String sTempDBTable, CLAllObjectList<CL_DBCV> InsertFields) {
        switch (getDbusingpar()) {
            case DB_MYSQL_Selected -> {
                MySQL_InsertDatainDB(sTempDBTable, InsertFields);
            }
            case DB_SQLite_Selected -> {
                SQLite_InsertDatainDB(sTempDBTable, InsertFields);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_InsertDatainDB(sTempDBTable, InsertFields);
            }

            default -> {
                MySQL_InsertDatainDB(sTempDBTable, InsertFields);
            }

        }
    }

    /**
     *
     * @param sTempDBTable
     * @param CL
     */
    public void InsertDatainDB(String sTempDBTable, CLDBColumnsList CL) {
        switch (getDbusingpar()) {
            case DB_MYSQL_Selected -> {
                MySQL_InsertDatainDB(sTempDBTable, CL);
            }
            case DB_SQLite_Selected -> {
                SQLite_InsertDatainDB(sTempDBTable, CL);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_InsertDatainDB(sTempDBTable, CL);
            }
            default -> {
                MySQL_InsertDatainDB(sTempDBTable, CL);
            }

        }
    }

    /**
     *
     * @param sTempDBTable
     * @param CL
     */
    public void MySQL_InsertDatainDB(String sTempDBTable, CLDBColumnsList CL) {

        //int indextotal=CL.getElements().size();
        String sMySQL_Statement = "INSERT INTO " + sDBName + "." + sTempDBTable + "  (";
        ListIterator<CL_DBColumns> mlColIterador = CL.getElements().listIterator();
        while (mlColIterador.hasNext()) {
            CL_DBColumns Elemento = mlColIterador.next();
            sMySQL_Statement += "\'" + Elemento.getAlsColumns() + "\'";
            if (mlColIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += ") VALUES (";
        int itindex = CL.getElements().size();
        for (int iidx = 0; iidx < itindex; iidx++) {
            CL_DBColumns Elemento = CL.getElements().get(iidx);
            int ifidx2 = Elemento.getElementList().size();
            for (int iidx2 = 0; iidx2 < ifidx2; iidx2++) {
                sMySQL_Statement += "?";
                if (iidx2 < ifidx2) {
                    sMySQL_Statement += ",";
                }
            }
            sMySQL_Statement += ")";
        }
        sMySQL_Statement += ";";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            itindex = CL.getElements().size();
            // stsSQL_Statement.setString(1, AntiSQLInjection(sTempDBTable));
            for (int iidx = 0; iidx < itindex; iidx++) {
                CL_DBColumns Elemento = CL.getElements().get(iidx);
                int ifidx2 = Elemento.getElementList().size();
                for (int idx2 = 0; idx2 < ifidx2; idx2++) {
                    stsSQL_Statement.setString(idx2 + 1, Elemento.getElementList().get(idx2));
                }
            }

            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTempDBTable);
        } finally {
            MySQL_CloseConn();

        }

    }

    /**
     *
     * @param sTempDBTable
     * @param CL
     */
    public void PostgreSQL_InsertDatainDB(String sTempDBTable, CLDBColumnsList CL) {

        //int indextotal=CL.getElements().size();
        String sMySQL_Statement = "INSERT INTO " + sDBName + "." + sTempDBTable + "  (";
        ListIterator<CL_DBColumns> mlColIterador = CL.getElements().listIterator();
        while (mlColIterador.hasNext()) {
            CL_DBColumns Elemento = mlColIterador.next();
            sMySQL_Statement += "\'" + Elemento.getAlsColumns() + "\'";
            if (mlColIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += ") VALUES (";
        int itindex = CL.getElements().size();
        for (int iidx = 0; iidx < itindex; iidx++) {
            CL_DBColumns Elemento = CL.getElements().get(iidx);
            int ifidx2 = Elemento.getElementList().size();
            for (int iidx2 = 0; iidx2 < ifidx2; iidx2++) {
                sMySQL_Statement += "?";
                if (iidx2 < ifidx2) {
                    sMySQL_Statement += ",";
                }
            }
            sMySQL_Statement += ")";
        }
        sMySQL_Statement += ";";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            itindex = CL.getElements().size();
            // stsSQL_Statement.setString(1, AntiSQLInjection(sTempDBTable));
            for (int iidx = 0; iidx < itindex; iidx++) {
                CL_DBColumns Elemento = CL.getElements().get(iidx);
                int ifidx2 = Elemento.getElementList().size();
                for (int idx2 = 0; idx2 < ifidx2; idx2++) {
                    stsSQL_Statement.setString(idx2 + 1, Elemento.getElementList().get(idx2));
                }
            }

            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTempDBTable);
        } finally {
            PostgreSQL_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param CL
     */
    public void SQLite_InsertDatainDB(String sTmpDBTable, CLDBColumnsList CL) {

        //int indextotal=CL.getElements().size();
        String sSQLite_Statement = "INSERT INTO " + sDBName + "." + sTmpDBTable + "  (";
        ListIterator<CL_DBColumns> mlColIterador = CL.getElements().listIterator();
        while (mlColIterador.hasNext()) {
            CL_DBColumns Elemento = mlColIterador.next();
            sSQLite_Statement += "\'" + Elemento.getAlsColumns() + "\'";
            if (mlColIterador.hasNext()) {
                sSQLite_Statement += ",";
            }
        }
        sSQLite_Statement += ") VALUES (";
        int itindex = CL.getElements().size();
        for (int iidx = 0; iidx < itindex; iidx++) {
            CL_DBColumns Elemento = CL.getElements().get(iidx);
            int ifidx2 = Elemento.getElementList().size();
            for (int iidx2 = 0; iidx2 < ifidx2; iidx2++) {
                try {
                    stsSQL_Statement.setString(iidx2, Elemento.getElementList().get(iidx2));
                } catch (SQLException ex) {
                    ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
                }
                sSQLite_Statement += "?,?)";
                sSQLite_Statement += ",(";
            }
        }
        sSQLite_Statement += ";";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sSQLite_Statement);
            itindex = CL.getElements().size();
            // stsSQL_Statement.setString(1, AntiSQLInjection(sTmpDBTable));
            for (int iidx = 0; iidx < itindex; iidx++) {
                CL_DBColumns Elemento = CL.getElements().get(iidx);
                int ifidx2 = Elemento.getElementList().size();
                for (int iidx2 = 0; iidx2 < ifidx2; iidx2++) {
                    stsSQL_Statement.setString(iidx2, Elemento.getElementList().get(iidx2));
                }
            }

            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            SQLite_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param InsertFields
     */
    public void MySQL_InsertDatainDB(String sTmpDBTable, CLAllObjectList<CL_DBCV> InsertFields) {

        ListIterator<CL_DBCV> mlColIterador = InsertFields.listIterator();
        String sMySQL_Statement = "INSERT INTO " + sDBName + "." + sTmpDBTable + "  (";

        while (mlColIterador.hasNext()) {
            CL_DBCV sElemento = mlColIterador.next();
            sMySQL_Statement += "'" + sElemento.getsTableDBField() + "'";
            if (mlColIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += ") VALUES (";
        ListIterator<CL_DBCV> mlIterador = InsertFields.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += "?,?)";
            if (mlIterador.hasNext()) {
                sMySQL_Statement += ",(";
            }
        }
        sMySQL_Statement += ";";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            int itindex = 1;
            // stsSQL_Statement.setString(1, AntiSQLInjection(sTmpDBTable));
            mlIterador = InsertFields.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setInt(itindex, Integer.parseInt(Elemento.getsValue()));
                stsSQL_Statement.setString(itindex + 1, Elemento.getsTableDBField());
                itindex = itindex + 2;
            }
            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            MySQL_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param InsertFields
     */
    public void PostgreSQL_InsertDatainDB(String sTmpDBTable, CLAllObjectList<CL_DBCV> InsertFields) {

        ListIterator<CL_DBCV> mlColIterador = InsertFields.listIterator();
        String sMySQL_Statement = "INSERT INTO " + sDBName + "." + sTmpDBTable + "  (";

        while (mlColIterador.hasNext()) {
            CL_DBCV sElemento = mlColIterador.next();
            sMySQL_Statement += "\'" + sElemento + "\'";
            if (mlColIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += ") VALUES (";
        ListIterator<CL_DBCV> mlIterador = InsertFields.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += "?,?)";
            if (mlIterador.hasNext()) {
                sMySQL_Statement += ",(";
            }
        }
        sMySQL_Statement += ";";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            int itindex = 1;
            // stsSQL_Statement.setString(1, AntiSQLInjection(sTmpDBTable));
            mlIterador = InsertFields.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setInt(itindex, Integer.parseInt(Elemento.getsValue()));
                stsSQL_Statement.setString(itindex + 1, Elemento.getsTableDBField());
                itindex = itindex + 2;
            }
            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            PostgreSQL_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param ColumnsName
     * @param InsertFields
     */
    public void PostgreSQL_InsertDatainDB(String sTmpDBTable, ArrayList<String> ColumnsName, CLAllObjectList<CL_DBCV> InsertFields) {

        ListIterator<CL_DBCV> mlIterador = null;
        ListIterator<String> mlColIterador = ColumnsName.listIterator();
        String sMySQL_Statement = "INSERT INTO " + sDBName + "." + sTmpDBTable + "  (";

        while (mlColIterador.hasNext()) {
            String sElemento = mlColIterador.next();
            sMySQL_Statement += "\'" + sElemento + "\'";
            if (mlColIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += ") VALUES (";
        mlIterador = InsertFields.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += "?,?";
            if (mlIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += ");";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            int itindex = 1;
            int icolindex = 1;
            icolindex = ColumnsName.size();
            mlIterador = InsertFields.listIterator();
            int lastitem = InsertFields.size();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(itindex, String.format("%d", Integer.valueOf(Elemento.getsValue())));
                stsSQL_Statement.setString(itindex + 1, Elemento.getsTableDBField());
                if (mlIterador.hasNext()) {
                    Elemento = mlIterador.next();
                    stsSQL_Statement.setString(itindex + 2, Elemento.getsTableDBField());
                }

                itindex = itindex + 3;
            }
            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            PostgreSQL_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param ColumnsName
     * @param InsertFields
     */
    public void MySQL_InsertDatainDB(String sTmpDBTable, ArrayList<String> ColumnsName, CLAllObjectList<CL_DBCV> InsertFields) {

        ListIterator<CL_DBCV> mlIterador = null;
        ListIterator<String> mlColIterador = ColumnsName.listIterator();
        String sMySQL_Statement = "INSERT INTO " + sDBName + "." + sTmpDBTable + "  (";
        int icolindex = ColumnsName.size();
        while (mlColIterador.hasNext()) {
            String sElemento = mlColIterador.next();
            sMySQL_Statement += "" + sElemento + "";
            if (mlColIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += ") VALUES ";

        int idxmaxelements = InsertFields.size();
        sMySQL_Statement += "(";
        for (int idxitem = 0; idxitem < idxmaxelements; idxitem++) {

            //mlIterador = InsertFields.listIterator();
            // while (mlIterador.hasNext()) {
            //    CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += "?";
            //   if (mlIterador.hasNext()) {
            sMySQL_Statement += ",";
            //   }
        }
        if (sMySQL_Statement.endsWith(",")) {
            sMySQL_Statement = sMySQL_Statement.substring(0, sMySQL_Statement.length() - 1);
        }
        sMySQL_Statement += ")";
        //}

        sMySQL_Statement += ";";
        setbError(false);
        int iRes;

        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            int itindex = 1;
            // int icolindex = 1;
            //icolindex = ColumnsName.size();

            int idxmaxrows = InsertFields.size();
            //  for (int idxrows = 0; idxrows < idxmaxrows; idxrows++) {
            mlIterador = mlIterador = InsertFields.listIterator();
            //  int lastitem = InsertFields.size();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(itindex, Elemento.getsValue());
                //stsSQL_Statement.setString(itindex + 1, Elemento.getsTableDBField());
                //if (mlIterador.hasNext()) {
                //    Elemento = mlIterador.next();
                //stsSQL_Statement.setString(itindex + 2, Elemento.getsTableDBField());
                // }

                itindex++;
            }
            // }

            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            MySQL_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param InsertFields
     */
    public void SQLite_InsertDatainDB(String sTmpDBTable, CLAllObjectList<CL_DBCV> InsertFields) {

        ListIterator<CL_DBCV> mlIterador = InsertFields.listIterator();
        String sSQLite_Statement = "INSERT INTO " + sDBName + "." + sTmpDBTable + "  ";
        sSQLite_Statement += "VALUES (";
        mlIterador = InsertFields.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sSQLite_Statement += "?,?)";
            if (mlIterador.hasNext()) {
                sSQLite_Statement += ",(";
            }
        }
        sSQLite_Statement += ";";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sSQLite_Statement);
            int itindex = 1;
            // stsSQL_Statement.setString(1, AntiSQLInjection(sTmpDBTable));
            mlIterador = InsertFields.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setInt(itindex, Integer.parseInt(Elemento.getsValue()));
                stsSQL_Statement.setString(itindex + 1, Elemento.getsTableDBField());
                itindex = itindex + 2;
            }
            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            SQLite_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     * @param ColumnsName
     * @param InsertFields
     */
    public void SQLite_InsertDatainDB(String sTmpDBTable, ArrayList<String> ColumnsName, CLAllObjectList<CL_DBCV> InsertFields) {
        ListIterator<CL_DBCV> mlIterador = null;
        ListIterator<String> mlColIterador = ColumnsName.listIterator();
        String sSQLite_Statement = "INSERT INTO " + sDBName + "." + sTmpDBTable + "  (";

        while (mlColIterador.hasNext()) {
            String sElemento = mlColIterador.next();
            sSQLite_Statement += "\'" + sElemento + "\'";
            if (mlColIterador.hasNext()) {
                sSQLite_Statement += ",";
            }
        }
        sSQLite_Statement += ") VALUES (";
        mlIterador = InsertFields.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sSQLite_Statement += "?,?";
            if (mlIterador.hasNext()) {
                sSQLite_Statement += ",";
            }
        }
        sSQLite_Statement += ");";
        setbError(false);
        int iRes;
        try {
            stsSQL_Statement = cnConn.prepareStatement(sSQLite_Statement);
            int itindex = 1;
            mlIterador = InsertFields.listIterator();
            int lastitem = InsertFields.size();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(itindex, String.format("%d", Integer.valueOf(Elemento.getsValue())));
                stsSQL_Statement.setString(itindex + 1, Elemento.getsTableDBField());
                itindex = itindex + 2;
            }
            iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            SQLite_CloseConn();

        }

    }

    /**
     *
     * @param sTmpDBTable
     */
    public void TruncateTable(String sTmpDBTable) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_TruncateTable(sTmpDBTable);
            }
            case DB_SQLite_Selected -> {
                SQLite_TruncateTable(sTmpDBTable);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_TruncateTable(sTmpDBTable);
            }
            default -> {
                MySQL_TruncateTable(sTmpDBTable);
            }

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void PostgreSQL_TruncateTable(String sTmpDBTable) {

        var sMySQL_Statement = "TRUNCATE TABLE" + sTmpDBTable + ";";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar los datos de la tabla:" + sTmpDBTable);
        } finally {
            iRes = 0;
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void MySQL_TruncateTable(String sTmpDBTable) {

        var sMySQL_Statement = "TRUNCATE TABLE" + sTmpDBTable + ";";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar los datos de la tabla:" + sTmpDBTable);
        } finally {
            iRes = 0;
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void SQLite_TruncateTable(String sTmpDBTable) {
        try {
            var sSQLite_Statement = "TRUNCATE TABLE" + sTmpDBTable + ";";
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.executeQuery(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al borrar los datos de la tabla:" + sTmpDBTable);
        }

    }

    /**
     *
     * @param sTmpDBTable
     */
    public void DropTable(String sTmpDBTable) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_DropTable(sTmpDBTable);
            }
            case DB_SQLite_Selected -> {
                SQLite_DropTable(sTmpDBTable);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_DropTable(sTmpDBTable);
            }
            default -> {
                MySQL_DropTable(sTmpDBTable);
            }

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void SQLite_DropTable(String sTmpDBTable) {
        try {
            var sSQLite_Statement = "DROP TABLE IF EXISTS " + this.sDBName + "." + sTmpDBTable + ";";
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.executeQuery(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al borrar los datos de la tabla:" + sTmpDBTable);
        }

    }

    /**
     *
     * @param sTmpDBTable
     */
    public void PostgreSQL_DropTable(String sTmpDBTable) {

        String sMySQL_Statement = "DROP TABLE IF EXISTS " + sTmpDBTable + ";";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar la tabla:" + sTmpDBTable);
        } finally {
            iRes = 0;
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void MySQL_DropTable(String sTmpDBTable) {

        String sMySQL_Statement = "DROP TABLE IF EXISTS " + sTmpDBTable + ";";
        int iRes = 0;
        setbError(false);
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            iRes = stsSQL_Statement.executeUpdate();
            // setiInsertedLastRow(iRes);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al borrar la tabla:" + sTmpDBTable);
        } finally {
            iRes = 0;
            MySQL_CloseConn();

        }
    }

    /**
     *
     */
    public void SQLite_CloseConn() {
        try {

            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            cnConn.close();
            // create a new table

        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al cerrar la conexxión SQLite:");
        }

    }

    /**
     *
     */
    public void Close() {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_CloseDSPoolConn();
            }
            case DB_SQLite_Selected -> {
                SQLite_CloseConn();
            }
            default -> {
                MySQL_CloseDSPoolConn();
            }
        }
    }

    void SQLite_DriverInit(String sTmpDBName) {
        try {
            cnConn = DriverManager.getConnection(SQLite_ServerName + sTmpDBName + ".db");
            if (cnConn != null) {
                var meta = cnConn.getMetaData();
                System.out.println(meta.getDriverName() + " driver initialized");
                //System.out.println("A new database has been created.");
            }
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al inicializar el driver SQLite:");
        }

    }

    /**
     *
     */
    public void MySQL_CloseDSPoolConn() {

        setbError(false);

        try {
            if (cnConn != null) {
                cnConn.close();
                cnConn = null;
            }
        } catch (SQLException ex) {
            setIerrCode(ex.getErrorCode());
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            setbError(true);

        } finally {
            if (isbError()) {
                ShowSQLExceptionError(null, " Error al cerrar la conexión de MySQL:" + serrSQLMessage);
            }
        }

        try {
            if (getRsCResSet() != null) {
                getRsCResSet().close();
                setRsCResSet(null);
            }
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al cerrar el conjunto de la conexión de MySQL:");

        } finally {
            if (isbError()) {
                setRsCResSet(null);
            }
        }

        if (getMcpdsDataOrigin() != null) {
            setMcpdsDataOrigin(null);
        }

    }

    /**
     *
     * @param stmpURL
     * @param sTmpDBName
     * @param stmpUser
     * @param stmpPass
     */
    public final void MySql_DSPoolInitSimple(String stmpURL, String sTmpDBName, String stmpUser, String stmpPass) {
        setMcpdsDataOrigin(new MysqlConnectionPoolDataSource());
        getMcpdsDataOrigin().setServerName(sMySQL_DefaultServerNConn);
        getMcpdsDataOrigin().setURL(stmpURL);
        getMcpdsDataOrigin().setUser(stmpUser);
        getMcpdsDataOrigin().setPassword(stmpPass);
        getMcpdsDataOrigin().setPortNumber(iMySQL_PortNConn);
        getMcpdsDataOrigin().setDatabaseName(sTmpDBName);

        SetupConnection();
        //  try {
        //      getMcpdsDataOrigin().setServerTimezone("UTC");
        //   }
        //   catch (SQLException ex) {
        //       Logger.getLogger(DBManagerGenerator.class.getName()).log(Level.SEVERE, null, ex);
        //   }
        //dspoolDataSrc.setPortNumber(getPortNumberConnection());
    }

    /**
     *
     * @param stmpURL
     * @param sTmpDBName
     * @param stmpUser
     * @param stmpPass
     */
    public final void PostgreSQL_DSPoolInitSimple(String stmpURL, String sTmpDBName, String stmpUser, String stmpPass) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        Properties props = new Properties();
        props.setProperty("user", stmpUser);
        props.setProperty("password", stmpPass);
        //props.setProperty("ssl", "true");
        try {
            cnConn = DriverManager.getConnection(stmpURL, props);
            //  try {
            //      getMcpdsDataOrigin().setServerTimezone("UTC");
            //   }
            //   catch (SQLException ex) {
            //       Logger.getLogger(DBManagerGenerator.class.getName()).log(Level.SEVERE, null, ex);
            //   }
            //dspoolDataSrc.setPortNumber(getPortNumberConnection());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    /**
     *
     */
    public void SetupConnection() {
        if (getMcpdsDataOrigin() != null) {
            try {
                cnConn = getMcpdsDataOrigin().getConnection();
            } catch (SQLException ex) {
                ShowSQLExceptionError(ex, " Error al configurar la la conexión de MySQL:");
            } finally {
                MySQL_CloseConn();
            }
        }

    }

    private void MySQL_CloseConn() {
        if (isbError()) {
            MySQL_CloseDSPoolConn();
            ShowSQLExceptionError(null, " Error al cerrar la conexión de MySQL:");
        }
    }

    /**
     * @return the mcpdsPoolDataSource
     */
    public MysqlConnectionPoolDataSource getMcpdsDataOrigin() {
        return mcpdsPoolDataSource;
    }

    /**
     * @param mcpdsDataOrigin the mcpdsPoolDataSource to set
     */
    public void setMcpdsDataOrigin(MysqlConnectionPoolDataSource mcpdsDataOrigin) {
        this.mcpdsPoolDataSource = mcpdsDataOrigin;
    }

    /**
     * @return the bError
     */
    public boolean isbError() {
        return bError;
    }

    /**
     * @param bError the bError to set
     */
    public void setbError(boolean bError) {
        this.bError = bError;
    }

    /**
     * @return the ierrCode
     */
    public int getIerrCode() {
        return ierrCode;
    }

    /**
     * @param ierrCode the ierrCode to set
     */
    public void setIerrCode(int ierrCode) {
        this.ierrCode = ierrCode;
    }

    /**
     *
     * @param sTmpDBTable
     * @return
     */
    public int getLastRow(String sTmpDBTable) {

        int iresult = 0;

        try {

            String Statement = "select count(*) from " + sDBName + "." + sTmpDBTable + ";";

            stsSQL_Statement = cnConn.prepareStatement(Statement);
            ResultSet rs = stsSQL_Statement.executeQuery();
            while (rs.next()) {
                iresult = rs.getInt("count(*)");
            }
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al obtener la úĺtima fila:" + sTmpDBTable);
        }
        //return iresult;
        /*
        try {
            isokrow=rsCResSet.last(); //go to last row;
        }
        catch (SQLException ex) {
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al desplazarme al último registro , conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
        try {
            iresult = rsCResSet.getRow();
        }

        catch (SQLException ex) {
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al desplazarme al último registro , conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
         try {
            isokrow=rsCResSet.first(); //go to forst row;
        }
        catch (SQLException ex) {
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al desplazarme al último registro , conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
        return iresult;
    }

    public int getActualRow() {

        int iresult = 0;

        setbError(false);

        try {
            iresult = rsCResSet.getRow();

        }
        catch (SQLException ex) { // ignore }
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{this.getClass()
                .getName(), ex.getLocalizedMessage()});
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            iresult = 0;
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al obtener registro actual, conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
        finally {
            MySQL_CloseConn();

        //return iresult;
        /*
        try {
            isokrow=rsCResSet.last(); //go to last row;
        }
        catch (SQLException ex) {
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al desplazarme al último registro , conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
        try {
            iresult = rsCResSet.getRow();
        }

        catch (SQLException ex) {
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al desplazarme al último registro , conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
         try {
            isokrow=rsCResSet.first(); //go to forst row;
        }
        catch (SQLException ex) {
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al desplazarme al último registro , conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
        return iresult;
    }

    public int getActualRow() {

        int iresult = 0;

        setbError(false);

        try {
            iresult = rsCResSet.getRow();

        }
        catch (SQLException ex) { // ignore }
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{this.getClass()
                .getName(), ex.getLocalizedMessage()});
            setbError(true);
            ierrCode = ex.getErrorCode();
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = ex.getSQLState();
            iresult = 0;
            LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
            // ShowDError(" Error al obtener registro actual, conjunto de datos  " + rsCResSet.toString() + "." + serrSQLMessage);
            //  MySQL_CloseDSPoolConn();
        }
        finally {
            MySQL_CloseConn();

        }*/

        return iresult;

    }

    /**
     *
     * @param sTmpDBTable
     */
    public void ExecuteQueryTableDBWithOptions(String sTmpDBTable) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_ExecuteQueryTableDBWithOptions(sTmpDBTable);
            }
            case DB_SQLite_Selected -> {
                SQLite_ExecuteQueryTableDBWithOptions(sTmpDBTable);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_ExecuteQueryTableDBWithOptions(sTmpDBTable);
            }
            default -> {
                MySQL_ExecuteQueryTableDBWithOptions(sTmpDBTable);
            }

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void SQLite_ExecuteQueryTableDBWithOptions(String sTmpDBTable) {

        String Statement = sTmpDBTable;
        boolean execute = false;

        try {
            stsSQL_Statement = cnConn.prepareStatement(Statement);
            execute = stsSQL_Statement.execute();

        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al actualizar datos fichero en la tabla " + sTmpDBTable);
        } finally {

        }

    }

    /**
     *
     * @param sTmpDBTable
     */
    public void MySQL_ExecuteQueryTableDBWithOptions(String sTmpDBTable) {

        String Statement = sTmpDBTable;
        boolean bRes = false;
        setbError(false);

        try {
            stsSQL_Statement = cnConn.prepareStatement(Statement);
            bRes = stsSQL_Statement.execute();
            setRsCResSet(stsSQL_Statement.getResultSet());

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al actualizar datos fichero en la tabla " + sTmpDBTable);
        } finally {
            bRes = false;
            //MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     */
    public void PostgreSQL_ExecuteQueryTableDBWithOptions(String sTmpDBTable) {

        String Statement = sTmpDBTable;
        boolean bRes = false;
        setbError(false);

        try {
            stsSQL_Statement = cnConn.prepareStatement(Statement);
            bRes = stsSQL_Statement.execute();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al actualizar datos fichero en la tabla " + sTmpDBTable);
        } finally {
            bRes = false;
            PostgreSQL_CloseConn();
        }
    }

    /**
     *
     * @param sTmpDBTable
     * @return
     */
    public CLAllObjectList<String> GetColumnsName(String sTmpDBTable) {
        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);
        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                LCA = MYSQL_GetColumnsName(sTmpDBTable);
            }
            case DB_SQLite_Selected -> {
                LCA = SQLite_GetColumnsName(sTmpDBTable);
            }
            case DB_PostgreSQL_Selected -> {
                LCA = PostgreSQL_GetColumnsName(sTmpDBTable);
            }

            default -> {
                LCA = MYSQL_GetColumnsName(sTmpDBTable);
            }

        }
        return LCA;
    }

    /**
     *
     * @param sDBTableName
     * @return
     */
    public CLAllObjectList<String> MYSQL_GetColumnsName(String sDBTableName) {
        String sqlStat = "select column_name as column_name "
                + "from information_schema.columns "
                + "where table_name = '" + sDBTableName + "' "
                + "order by ordinal_position;";
        CLAllObjectList<String> GCN = new CLAllObjectList<>();

        ExecuteQueryTableDBWithOptions(sqlStat);
        boolean result = true;
        if (getRsCResSet() != null) {
            while (result) {
                try {
                    result = getRsCResSet().next();
                } catch (SQLException ex) {
                    ShowSQLExceptionError(ex, " Error al buscar columnas de tabla " + sDBTableName);
                }
                if (result) {
                    String st = "";
                    try {
                        st = getRsCResSet().getString(1);
                    } catch (SQLException ex) {
                        ShowSQLExceptionError(ex, " Error al buscar columnas de tabla " + sDBTableName);
                    }
                    GCN.add(st);
                }

            }
        }

        return GCN;
    }

    /**
     *
     * @param sTmpDBTable
     * @param sColumnName
     * @return
     */
    public CLAllObjectList<String> LoadList(String sTmpDBTable, String sColumnName) {
        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);
        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                LCA = MySQL_LoadList(sTmpDBTable, sColumnName);
            }
            case DB_SQLite_Selected -> {
                LCA = SQLite_LoadList(sTmpDBTable, sColumnName);
            }
            case DB_PostgreSQL_Selected -> {
                LCA = PostgreSQL_LoadList(sTmpDBTable, sColumnName);
            }

            default -> {
                LCA = MySQL_LoadList(sTmpDBTable, sColumnName);
            }

        }
        return LCA;
    }

    /**
     *
     * @param sTmpDBTable
     * @param sColumnName
     * @param tmpClient
     * @param tmpEnterprise
     * @return
     */
    public CLAllObjectList<String> LoadList(String sTmpDBTable, String sColumnName, String tmpClient, String tmpEnterprise) {
        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);
        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                LCA = MySQL_LoadList(sTmpDBTable, sColumnName, tmpClient, tmpEnterprise);
            }
            case DB_SQLite_Selected -> {
                LCA = SQLite_LoadList(sTmpDBTable, sColumnName, tmpClient, tmpEnterprise);
            }
            case DB_PostgreSQL_Selected -> {
                LCA = PostgreSQL_LoadList(sTmpDBTable, sColumnName, tmpClient, tmpEnterprise);
            }

            default -> {
                LCA = MySQL_LoadList(sTmpDBTable, sColumnName, tmpClient, tmpEnterprise);
            }

        }
        return LCA;
    }

    /**
     *
     * @param sTable
     * @param sColumnName
     * @return
     */
    public CLAllObjectList<String> MySQL_LoadList(String sTable, String sColumnName) {

        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);

        int Count = getLastRow(sTable);
        MySQL_SelectAllRecords(sTable);
        try {
            getRsCResSet().first();
        } catch (SQLException ex) {
            //ignorar
        }

        for (int it = 0; it < Count; it++) {
            String sTmp_01 = getQueryResult(sColumnName);
            LCA.add(sTmp_01);
            try {
                getRsCResSet().next();
            } catch (SQLException ex) {
                ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sTable);
            }
        }
        return LCA;
    }

    /**
     *
     * @param sSQLParams
     * @param DBParameters
     */
    public void SelectAllRecordsWithSQLParams(String sSQLParams, CLAllObjectList<String> DBParameters) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_SelectAllRecordWithDefinedStatement(sSQLParams, DBParameters);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_SelectAllRecordWithDefinedStatement(sSQLParams, DBParameters);
            }
            case DB_SQLite_Selected -> {
                CLAllObjectList<CL_DBCV> FieldList = new CLAllObjectList<>();
                // FieldList.addCol(new CL_DBCV(DBParameters, sFieldValuetoSearch));
                SQLite_SelectManyResults(sDBName, FieldList);
            }
            default -> {
                MySQL_SelectAllRecordWithDefinedStatement(sSQLParams, DBParameters);
            }
        }

    }

    /**
     *
     * @param sSQLParams
     * @param DBParameters
     */
    public void PostgreSQL_SelectAllRecordWithDefinedStatement(String sSQLParams, CLAllObjectList<String> DBParameters) {
        setbError(false);
        String Statement = sSQLParams;

        try {
            stsSQL_Statement = cnConn.prepareStatement(Statement);
            if (DBParameters != null) {
                String dbFieldDB = null;
                int iindex = 1, lastpar = 0;
                lastpar = DBParameters.size();

                for (iindex = 1; iindex <= lastpar; iindex++) {
                    dbFieldDB = DBParameters.get(iindex - 1);
                    if (dbFieldDB != null) {
                        stsSQL_Statement.setString(iindex, dbFieldDB);
                    }
                }
            }
            //    stsSQL_Statement.setString(1, sTable);

            //       stsSQL_Statement = cnConn.createStatement();
            setRsCResSet(stsSQL_Statement.executeQuery());

            getRsCResSet().next();
        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sSQLParams);
        } catch (NullPointerException ex) {
            ierrCode = 0xF555;
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = "error, no puedo conectar al servidor";
            ShowSQLExceptionError(null, serrSQLState);

        } finally {
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sSQLParams
     * @param DBParameters
     */
    public void MySQL_SelectAllRecordWithDefinedStatement(String sSQLParams, CLAllObjectList<String> DBParameters) {
        setbError(false);
        String Statement = sSQLParams;

        try {
            stsSQL_Statement = cnConn.prepareStatement(Statement);
            if (DBParameters != null) {
                String dbFieldDB = null;
                int iindex = 1, lastpar = 0;
                lastpar = DBParameters.size();

                for (iindex = 1; iindex <= lastpar; iindex++) {
                    dbFieldDB = DBParameters.get(iindex - 1);
                    if (dbFieldDB != null) {
                        stsSQL_Statement.setString(iindex, dbFieldDB);
                    }
                }
            }
            //    stsSQL_Statement.setString(1, sTable);

            //       stsSQL_Statement = cnConn.createStatement();
            setRsCResSet(stsSQL_Statement.executeQuery());

            getRsCResSet().next();
        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sSQLParams);
        } catch (NullPointerException ex) {
            ierrCode = 0xF555;
            serrSQLMessage = ex.getLocalizedMessage();
            serrSQLState = "error, no puedo conectar al servidor";
            ShowSQLExceptionError(null, serrSQLState);

        } finally {
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpTable
     */
    public void MySQL_SelectAllRecords(String sTmpTable) {
        String sMySQL_Statement = "SELECT * FROM " + sDBName + "." + sTmpTable + ";";
        MySQL_SelectAllRecordWithDefinedStatement(sMySQL_Statement, null);
    }

    /**
     *
     * @param sTmpTable
     * @param tmpClient
     * @param tmpEnterprise
     */
    public void MySQL_SelectAllRecords(String sTmpTable, String tmpClient, String tmpEnterprise) {
        String sMySQL_Statement = "SELECT * FROM " + sDBName + "." + sTmpTable + " WHERE idclient='" + tmpClient + " AND identerprise=" + tmpEnterprise + " ;";
        MySQL_SelectAllRecordWithDefinedStatement(sMySQL_Statement, null);
    }

    /**
     *
     * @param sTmpTable
     */
    public void PostgreSQL_SelectAllRecords(String sTmpTable) {
        String sMySQL_Statement = "SELECT * FROM " + sDBName + "." + sTmpTable + ";";
        PostgreSQL_SelectAllRecordWithDefinedStatement(sMySQL_Statement, null);
    }

    /**
     *
     * @param sTmpTable
     * @param tmpClient
     * @param tmpEnterprise
     */
    public void PostgreSQL_SelectAllRecords(String sTmpTable, String tmpClient, String tmpEnterprise) {
        String sMySQL_Statement = "SELECT * FROM " + sDBName + "." + sTmpTable + " WHERE idclient='" + tmpClient + " AND identerprise=" + tmpEnterprise + " ;";
        PostgreSQL_SelectAllRecordWithDefinedStatement(sMySQL_Statement, null);
    }

    /**
     *
     * @param sTable
     */
    public void SQLite_SelectAllRecords(String sTable) {
        String sSQLite_Statement = "SELECT * FROM " + sDBName + "." + sTable + ";";
        SQLite_SelectManyResults(sSQLite_Statement, null);
    }

    /**
     *
     * @param sTable
     * @param tmpClient
     * @param tmpEnterprise
     */
    public void SQLite_SelectAllRecords(String sTable, String tmpClient, String tmpEnterprise) {
        String sSQLite_Statement = "SELECT * FROM " + sDBName + "." + sTable + " WHERE idclient='" + tmpClient + " AND identerprise=" + tmpEnterprise + " ;";
        SQLite_SelectManyResults(sSQLite_Statement, null);
    }

    /**
     *
     * @param sTmpDBName
     * @param sTmpDBTableName
     * @param Columns
     * @return
     */
    public CLAllObjectList<CLAllObjectList<String>> MySQL_LoadList_Table(String sTmpDBName, String sTmpDBTableName, CLAllObjectList<String> Columns) {
        // CLAllObjectList<String> Columns = MYSQL_GetColumnsName(sTmpDBName, sTmpDBTableName);
        CLAllObjectList<CLAllObjectList<String>> TheTable = new CLAllObjectList<>();
        //int Count = getLastRow(sTmpDBTableName);
        //MySQL_SelectAllRecords(sTmpDBTableName);
        int colnumber = Columns.size();
        for (int col = 0; col < colnumber; col++) {
            CLAllObjectList<String> MiColl = new CLAllObjectList<>();
            TheTable.add(MiColl);
            TheTable.get(col).addAll(LoadList(sTmpDBTableName, Columns.get(col)));
        }
        return TheTable;
    }

    /**
     *
     * @param sTmpDBName
     * @param sTmpDBTableName
     * @param Columns
     * @return
     */
    public CLAllObjectList<CLAllObjectList<String>> PostgreSQL_LoadList_Table(String sTmpDBName, String sTmpDBTableName, CLAllObjectList<String> Columns) {
        // CLAllObjectList<String> Columns = MYSQL_GetColumnsName(sTmpDBName, sTmpDBTableName);
        CLAllObjectList<CLAllObjectList<String>> TheTable = new CLAllObjectList<>();
        //int Count = getLastRow(sTmpDBTableName);
        //MySQL_SelectAllRecords(sTmpDBTableName);
        int colnumber = Columns.size();
        for (int col = 0; col < colnumber; col++) {
            CLAllObjectList<String> MiColl = new CLAllObjectList<>();
            TheTable.add(MiColl);
            TheTable.get(col).addAll(LoadList(sTmpDBTableName, Columns.get(col)));
        }
        return TheTable;
    }

    /**
     *
     * @param sTmpDBName
     * @param sTmpDBTableName
     * @param Columns
     * @return
     */
    public CLAllObjectList<CLAllObjectList<String>> SQLite_LoadList_Table(String sTmpDBName, String sTmpDBTableName, CLAllObjectList<String> Columns) {
        // CLAllObjectList<String> Columns = MYSQL_GetColumnsName(sTmpDBName, sTmpDBTableName);
        CLAllObjectList<CLAllObjectList<String>> TheTable = new CLAllObjectList<>();
        //int Count = getLastRow(sTmpDBTableName);
        //MySQL_SelectAllRecords(sTmpDBTableName);
        int colnumber = Columns.size();
        for (int col = 0; col < colnumber; col++) {
            CLAllObjectList<String> MiColl = new CLAllObjectList<>();
            TheTable.add(MiColl);
            TheTable.get(col).addAll(LoadList(sTmpDBTableName, Columns.get(col)));
        }
        return TheTable;
    }

    /**
     *
     * @param sTable
     * @param Scolumnname
     * @return
     */
    public CLAllObjectList<String> PostgreSQL_LoadList(String sTable, String Scolumnname) {
        CLAllObjectList<String> LCA = new CLAllObjectList<String>(1);
        int Count = getLastRow(sTable);
        PostgreSQL_SelectAllRecords(sTable);
        try {
            getRsCResSet().first();
        } catch (SQLException ex) {
            //ignorar
        }

        for (int it = 0; it < Count; it++) {
            String sTmp_01 = getQueryResult(Scolumnname);
            LCA.add(sTmp_01);
            try {
                getRsCResSet().next();
            } catch (SQLException ex) {
                ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sTable);
            }
        }
        return LCA;
    }

    /**
     *
     * @param sTable
     * @param Scolumnname
     * @return
     */
    public CLAllObjectList<String> SQLite_LoadList(String sTable, String Scolumnname) {
        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);
        int Count = getLastRow(sTable);
        MySQL_SelectAllRecords(sTable);
        try {
            getRsCResSet().first();
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sTable);
        }

        for (int it = 0; it < Count; it++) {
            String sTmp_01 = getQueryResult(Scolumnname);
            LCA.add(sTmp_01);
            try {
                getRsCResSet().next();
            } catch (SQLException ex) {
                ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sTable);
            }
        }
        return LCA;
    }

    /**
     *
     * @param sTmpDBField
     * @return
     */
    public String getQueryResult(String sTmpDBField) {
        String sResFieldSearched = "";

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                sResFieldSearched = MySQL_getQueryResult(sTmpDBField);
            }
            case DB_PostgreSQL_Selected -> {
                sResFieldSearched = PostgreSQL_getQueryResult(sTmpDBField);
            }
            case DB_SQLite_Selected -> {
                String sFieldValuetoSearch = "";
                CLAllObjectList<CL_DBCV> FieldList = new CLAllObjectList<>();
                FieldList.add(new CL_DBCV(sTmpDBField, sFieldValuetoSearch));
                SQLite_SelectManyResults(sDBName, FieldList);
                sResFieldSearched = FieldList.get(0).getsValue();
            }
            default -> {
                sResFieldSearched = MySQL_getQueryResult(sTmpDBField);
            }
        }
        return sResFieldSearched;

    }

    /**
     *
     * @param sTmpDBField
     * @return
     */
    public String PostgreSQL_getQueryResult(String sTmpDBField) {

        String sResFieldSearched = "";
        String sFieldDataRowSearched = "";

        setbError(false);

        try {
            sFieldDataRowSearched = getRsCResSet().getString(sTmpDBField);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al efectuar la consulta de datos " + sTmpDBField);
        } finally {
            PostgreSQL_CloseConn();

        }

        if (CompareDBStr(sFieldDataRowSearched, "") == false) {
            sResFieldSearched = sFieldDataRowSearched;
        }

        return sResFieldSearched;

    }

    /**
     *
     * @param sTmpDBField
     * @return
     */
    public String MySQL_getQueryResult(String sTmpDBField) {

        String sResFieldSearched = "";
        String sFieldDataRowSearched = "";

        setbError(false);

        try {
            sFieldDataRowSearched = getRsCResSet().getString(sTmpDBField);

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al efectuar la consulta de datos " + sTmpDBField);
        } finally {
            MySQL_CloseConn();

        }

        if (CompareDBStr(sFieldDataRowSearched, "") == false) {
            sResFieldSearched = sFieldDataRowSearched;
        }

        return sResFieldSearched;

    }

    /**
     *
     * @param sTmpStr1
     * @param sTmpStr2
     * @return
     */
    public boolean CompareDBStr(String sTmpStr1, String sTmpStr2) {

        boolean bresult = false;
        int icresult = -1;

        if ((sTmpStr1 == null) || (sTmpStr2 == null)) {
        } else {

            icresult = sTmpStr1.compareTo(sTmpStr2);

            if (icresult == 0) {
                bresult = true;
            }
        }
        return bresult;
    }

    /**
     *
     * @param sDBTable
     * @param DBParameters
     * @param sIndex
     * @param siIndexItem
     */
    public void UpdateDatainDB(String sDBTable, CLAllObjectList<CL_DBCV> DBParameters, String sIndex, String siIndexItem) {

        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                MySQL_UpdateDatainDB(sDBTable, DBParameters, sIndex, siIndexItem);
            }
            case DB_PostgreSQL_Selected -> {
                PostgreSQL_UpdateDatainDB(sDBTable, DBParameters, sIndex, siIndexItem);
            }
            case DB_SQLite_Selected -> {
                SQLite_UpdateDatainDB(sDBTable, DBParameters, sIndex, siIndexItem);
            }
            default -> {
                MySQL_UpdateDatainDB(sDBTable, DBParameters, sIndex, siIndexItem);
            }
        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param DBParameters
     * @param sIndex
     * @param siIndexItem
     */
    public void PostgreSQL_UpdateDatainDB(String sTmpDBTable, CLAllObjectList<CL_DBCV> DBParameters, String sIndex, String siIndexItem) {

        String sMySQL_Statement = "UPDATE " + sDBName + "." + sTmpDBTable + " SET ";
        ListIterator<CL_DBCV> mlIterador = DBParameters.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += Elemento.getsTableDBField() + "=?";
            if (mlIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += " WHERE " + sDBName + "." + sTmpDBTable + "." + sIndex + " = " + siIndexItem;
        /*   if (DBParameters != null) {
            mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                sMySQL_Statement += Elemento.getsTableDBField() + "=?";
                if (mlIterador.hasNext()) {
                    sMySQL_Statement += " AND ";
                }
            }*/
        sMySQL_Statement += " ;";
        int iindex = 1;
        mlIterador = DBParameters.listIterator();
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            /*mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsTableDBField());
                iindex++;
            }*/
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsValue());
                iindex++;
            }

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            PostgreSQL_CloseConn();

        }

        try {
            int iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            PostgreSQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param DBParameters
     * @param sIndex
     * @param siIndexItem
     */
    public void MySQL_UpdateDatainDB(String sTmpDBTable, CLAllObjectList<CL_DBCV> DBParameters, String sIndex, String siIndexItem) {

        String sMySQL_Statement = "UPDATE " + sDBName + "." + sTmpDBTable + " SET ";
        ListIterator<CL_DBCV> mlIterador = DBParameters.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sMySQL_Statement += Elemento.getsTableDBField() + "=?";
            if (mlIterador.hasNext()) {
                sMySQL_Statement += ",";
            }
        }
        sMySQL_Statement += " WHERE " + sDBName + "." + sTmpDBTable + "." + sIndex + " = " + siIndexItem;
        /*   if (DBParameters != null) {
            mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                sMySQL_Statement += Elemento.getsTableDBField() + "=?";
                if (mlIterador.hasNext()) {
                    sMySQL_Statement += " AND ";
                }
            }*/
        sMySQL_Statement += " ;";
        int iindex = 1;
        mlIterador = DBParameters.listIterator();
        try {
            stsSQL_Statement = cnConn.prepareStatement(sMySQL_Statement);
            /*mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsTableDBField());
                iindex++;
            }*/
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsValue());
                iindex++;
            }

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            MySQL_CloseConn();

        }

        try {
            int iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            MySQL_CloseConn();

        }
    }

    /**
     *
     * @param sTmpDBTable
     * @param DBParameters
     * @param sIndex
     * @param siIndexItem
     */
    public void SQLite_UpdateDatainDB(String sTmpDBTable, CLAllObjectList<CL_DBCV> DBParameters, String sIndex, String siIndexItem) {

        String sSQLite_Statement = "UPDATE " + sDBName + "." + sTmpDBTable + " SET ";
        ListIterator<CL_DBCV> mlIterador = DBParameters.listIterator();
        while (mlIterador.hasNext()) {
            CL_DBCV Elemento = mlIterador.next();
            sSQLite_Statement += Elemento.getsTableDBField() + "=?";
            if (mlIterador.hasNext()) {
                sSQLite_Statement += ",";
            }
        }
        sSQLite_Statement += " WHERE " + sDBName + "." + sTmpDBTable + "." + sIndex + " = " + siIndexItem;
        /*   if (DBParameters != null) {
            mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                sMySQL_Statement += Elemento.getsTableDBField() + "=?";
                if (mlIterador.hasNext()) {
                    sMySQL_Statement += " AND ";
                }
            }*/
        sSQLite_Statement += " ;";
        int iindex = 1;
        mlIterador = DBParameters.listIterator();
        try {
            stsSQL_Statement = cnConn.prepareStatement(sSQLite_Statement);
            /*mlIterador = DBParameters.listIterator();
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsTableDBField());
                iindex++;
            }*/
            while (mlIterador.hasNext()) {
                CL_DBCV Elemento = mlIterador.next();
                stsSQL_Statement.setString(iindex, Elemento.getsValue());
                iindex++;
            }

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            Close();

        }

        try {
            int iRes = stsSQL_Statement.executeUpdate();

        } catch (SQLException ex) { // ignore }
            ShowSQLExceptionError(ex, " Error al insertar datos en la base de datos desde el panel:" + sTmpDBTable);
        } finally {
            Close();

        }
    }

    /**
     * @return the idbtouseselected
     */
    public int getDbusingpar() {
        return idbtouseselected;
    }

    /**
     * @param dbusingpar the idbtouseselected to set
     */
    public void setDbusingpar(int dbusingpar) {
        this.idbtouseselected = dbusingpar;
    }

    /**
     *
     * @param ex
     * @param sError
     */
    public void ShowSQLExceptionError(SQLException ex, String sError) {
        setbError(true);
        ierrCode = ex.getErrorCode();
        serrSQLMessage = ex.getLocalizedMessage();
        serrSQLState = ex.getSQLState();
        // rsCResSet = null;
        LoggingManagerGenerator.getGlobalLogger().log(Level.SEVERE, "{0}. Estado {1}", new Object[]{serrSQLMessage, serrSQLState});
        ShowDError(sError + "." + serrSQLMessage);
    }

    private void MySQL_CreateTable(String sTmpDBTable) {
        try {
            var sSQLite_Statement = "CREATE TABLE IF NOT EXISTS " + sTmpDBTable + "("
                    + "	id " + sTmpDBTable + " INTEGER PRIMARY KEY;";
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.execute(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al crear tabla sin campos:" + sTmpDBTable);
        }
    }

    private void PostgreSQL_CreateTable(String sTmpDBTable) {
        try {
            var sSQLite_Statement = "CREATE TABLE IF NOT EXISTS " + sTmpDBTable + "("
                    + "	id " + sTmpDBTable + " INTEGER PRIMARY KEY;";
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.execute(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al crear tabla sin campos:" + sTmpDBTable);
        }
    }

    private void SQLite_CreateTable(String sTmpDBTable) {
        try {
            var sSQLite_Statement = "CREATE TABLE IF NOT EXISTS " + sTmpDBTable + "("
                    + "	id " + sTmpDBTable + " INTEGER PRIMARY KEY;";
            //cnConn = DriverManager.getConnection(SQLite_ServerName + sDBName);
            var stmt = cnConn.createStatement();
            // create a new table
            stmt.execute(sSQLite_Statement);
        } catch (SQLException ex) {
            ShowSQLExceptionError(ex, " Error al crear tabla sin campos:" + sTmpDBTable);
        }

    }

    private void PostgreSQL_CloseConn() {
        try {
            stsSQL_Statement.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        try {
            cnConn.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     *
     * @param ActualDBName
     * @param ActualDBTable
     * @param Columns
     * @param ActualClient
     * @param ActualEnterprise
     * @return
     */
    public CLAllObjectList<CLAllObjectList<String>> MySQL_LoadList_Table(String ActualDBName, String ActualDBTable, CLAllObjectList<String> Columns, String ActualClient, String ActualEnterprise) {
        // CLAllObjectList<String> Columns = MYSQL_GetColumnsName(sTmpDBName, sTmpDBTableName);
        CLAllObjectList<CLAllObjectList<String>> TheTable = new CLAllObjectList<>();
        //int Count = getLastRow(sTmpDBTableName);
        //MySQL_SelectAllRecords(sTmpDBTableName);
        int colnumber = Columns.size();
   
        for (int col = 0; col < colnumber; col++) {
            CLAllObjectList<String> MiColl = new CLAllObjectList<>();
            TheTable.add(MiColl);
            TheTable.get(col).addAll(MySQL_LoadList(ActualDBTable, Columns.get(col)));
        }
        return TheTable;
    }

    private CLAllObjectList<String> MySQL_LoadList(String sTmpDBTable, String sColumnName, String tmpClient, String tmpEnterprise) {

        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);

        int Count = getLastRow(sTmpDBTable);
        MySQL_SelectAllRecords(sTmpDBTable, tmpClient, tmpEnterprise);
        try {
            getRsCResSet().first();
        } catch (SQLException ex) {
            //ignorar
        }

        for (int it = 0; it < Count; it++) {
            String sTmp_01 = getQueryResult(sColumnName);
            LCA.add(sTmp_01);
            try {
                getRsCResSet().next();
            } catch (SQLException ex) {
                ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sTmpDBTable + " Cliente " + tmpClient + " Empresa " + tmpEnterprise);
            }
        }
        return LCA;
    }

    private CLAllObjectList<String> SQLite_LoadList(String sTmpDBTable, String sColumnName, String tmpClient, String tmpEnterprise) {

        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);

        int Count = getLastRow(sTmpDBTable);
        SQLite_SelectAllRecords(sTmpDBTable, tmpClient, tmpEnterprise);
        try {
            getRsCResSet().first();
        } catch (SQLException ex) {
            //ignorar
        }

        for (int it = 0; it < Count; it++) {
            String sTmp_01 = getQueryResult(sColumnName);
            LCA.add(sTmp_01);
            try {
                getRsCResSet().next();
            } catch (SQLException ex) {
                ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sTmpDBTable + " Cliente " + tmpClient + " Empresa " + tmpEnterprise);
            }
        }
        return LCA;
    }

    private CLAllObjectList<String> PostgreSQL_LoadList(String sTmpDBTable, String sColumnName, String tmpClient, String tmpEnterprise) {

        CLAllObjectList<String> LCA = new CLAllObjectList<>(1);

        int Count = getLastRow(sTmpDBTable);
        PostgreSQL_SelectAllRecords(sTmpDBTable, tmpClient, tmpEnterprise);
        try {
            getRsCResSet().first();
        } catch (SQLException ex) {
            //ignorar
        }

        for (int it = 0; it < Count; it++) {
            String sTmp_01 = getQueryResult(sColumnName);
            LCA.add(sTmp_01);
            try {
                getRsCResSet().next();
            } catch (SQLException ex) {
                ShowSQLExceptionError(ex, " Error al cargar datos de la tabla:" + sTmpDBTable + " Cliente " + tmpClient + " Empresa " + tmpEnterprise);
            }
        }
        return LCA;
    }

    /**
     *
     * @param ActualDBName
     * @param ActualDBTable
     * @param Columns
     * @param ActualClient
     * @param ActualEnterprise
     * @return
     */
    public CLAllObjectList<CLAllObjectList<String>> LoadList_Table(String ActualDBName, String ActualDBTable, CLAllObjectList<String> Columns, String ActualClient, String ActualEnterprise) {
        CLAllObjectList<CLAllObjectList<String>> LCA = new CLAllObjectList<>(1);
        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                LCA = MySQL_LoadList_Table(ActualDBName, ActualDBTable, Columns, ActualClient, ActualEnterprise);
            }
            case DB_SQLite_Selected -> {
                LCA = SQLite_LoadList_Table(ActualDBName, ActualDBTable, Columns, ActualClient, ActualEnterprise);
            }
            case DB_PostgreSQL_Selected -> {
                LCA = PostgreSQL_LoadList_Table(ActualDBName, ActualDBTable, Columns, ActualClient, ActualEnterprise);
            }

            default -> {
                LCA = MySQL_LoadList_Table(ActualDBName, ActualDBTable, Columns, ActualClient, ActualEnterprise);
            }

        }
        return LCA;
    }

    /**
     *
     * @param ActualDBName
     * @param ActualDBTable
     * @param Columns
     * @return
     */
    public CLAllObjectList<CLAllObjectList<String>> LoadList_Table(String ActualDBName, String ActualDBTable, CLAllObjectList<String> Columns) {
        CLAllObjectList<CLAllObjectList<String>> LCA = new CLAllObjectList<>(1);
        switch (getDbusingpar()) {

            case DB_MYSQL_Selected -> {
                LCA = MySQL_LoadList_Table(ActualDBName, ActualDBTable, Columns);
            }
            case DB_SQLite_Selected -> {
                LCA = SQLite_LoadList_Table(ActualDBName, ActualDBTable, Columns);
            }
            case DB_PostgreSQL_Selected -> {
                LCA = PostgreSQL_LoadList_Table(ActualDBName, ActualDBTable, Columns);
            }

            default -> {
                LCA = MySQL_LoadList_Table(ActualDBName, ActualDBTable, Columns);
            }

        }
        return LCA;
    }

    private CLAllObjectList<CLAllObjectList<String>> SQLite_LoadList_Table(String ActualDBName, String ActualDBTable, CLAllObjectList<String> Columns, String ActualClient, String ActualEnterprise) {
        // CLAllObjectList<String> Columns = MYSQL_GetColumnsName(sTmpDBName, sTmpDBTableName);
        CLAllObjectList<CLAllObjectList<String>> TheTable = new CLAllObjectList<>();
        //int Count = getLastRow(sTmpDBTableName);
        //MySQL_SelectAllRecords(sTmpDBTableName);
        int colnumber = Columns.size();
        for (int col = 0; col < colnumber; col++) {
            CLAllObjectList<String> MiColl = new CLAllObjectList<>();
            TheTable.add(MiColl);
            TheTable.get(col).addAll(SQLite_LoadList(ActualDBTable, Columns.get(col)));
        }
        return TheTable;
    }

    private CLAllObjectList<CLAllObjectList<String>> PostgreSQL_LoadList_Table(String ActualDBName, String ActualDBTable, CLAllObjectList<String> Columns, String ActualClient, String ActualEnterprise) {
        // CLAllObjectList<String> Columns = MYSQL_GetColumnsName(sTmpDBName, sTmpDBTableName);
        CLAllObjectList<CLAllObjectList<String>> TheTable = new CLAllObjectList<>();
        //int Count = getLastRow(sTmpDBTableName);
        //MySQL_SelectAllRecords(sTmpDBTableName);
        int colnumber = Columns.size();
        for (int col = 0; col < colnumber; col++) {
            CLAllObjectList<String> MiColl = new CLAllObjectList<>();
            TheTable.add(MiColl);
            TheTable.get(col).addAll(PostgreSQL_LoadList(ActualDBTable, Columns.get(col)));
        }
        return TheTable;
    }

    private CLAllObjectList<String> SQLite_GetColumnsName(String sTmpDBTable) {
        String sqlStat = "select column_name as column_name "
                + "from information_schema.columns "
                + "where table_name = '" + sTmpDBTable + "' "
                + "order by ordinal_position;";
        CLAllObjectList<String> GCN = new CLAllObjectList<>();

        ExecuteQueryTableDBWithOptions(sqlStat);
        boolean result = true;
        if (getRsCResSet() != null) {
            while (result) {
                try {
                    result = getRsCResSet().next();
                } catch (SQLException ex) {
                    ShowSQLExceptionError(ex, " Error al buscar columnas de tabla " + sTmpDBTable);
                }
                if (result) {
                    String st = "";
                    try {
                        st = getRsCResSet().getString(1);
                    } catch (SQLException ex) {
                        ShowSQLExceptionError(ex, " Error al buscar columnas de tabla " + sTmpDBTable);
                    }
                    GCN.add(st);
                }

            }
        }

        return GCN;
    }

    private CLAllObjectList<String> PostgreSQL_GetColumnsName(String sTmpDBTable) {
        String sqlStat = "select column_name as column_name "
                + "from information_schema.columns "
                + "where table_name = '" + sTmpDBTable + "' "
                + "order by ordinal_position;";
        CLAllObjectList<String> GCN = new CLAllObjectList<>();

        ExecuteQueryTableDBWithOptions(sqlStat);
        boolean result = true;
        if (getRsCResSet() != null) {
            while (result) {
                try {
                    result = getRsCResSet().next();
                } catch (SQLException ex) {
                    ShowSQLExceptionError(ex, " Error al buscar columnas de tabla " + sTmpDBTable);
                }
                if (result) {
                    String st = "";
                    try {
                        st = getRsCResSet().getString(1);
                    } catch (SQLException ex) {
                        ShowSQLExceptionError(ex, " Error al buscar columnas de tabla " + sTmpDBTable);
                    }
                    GCN.add(st);
                }

            }
        }

        return GCN;
    }

    /**
     * @return the rsCResSet
     */
    public ResultSet getRsCResSet() {
        return rsCResSet;
    }

    /**
     * @param rsCResSet the rsCResSet to set
     */
    public void setRsCResSet(ResultSet rsCResSet) {
        this.rsCResSet = rsCResSet;
    }

}
