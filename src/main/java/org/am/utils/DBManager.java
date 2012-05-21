package org.am.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The DBManager.
 *
 * @author Alexander Maximenya
 * @version 1.0
 */
public class DBManager {
    /**
     * Stores a DBManager instance.
     */
    private static DBManager dbManager;
    /**
     * Stores a database connection.
     */
    private Connection connection;

    /**
     * Creates a new DBManager object.
     */
    private DBManager(String driverString) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        Class.forName(driverString).newInstance();
    }

    /**
     * Gets an instance of DBManager object.
     *
     * @return an instance of DBManager object
     */
    public static synchronized DBManager getInstance(String driverString) throws IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        if (dbManager == null) {
            dbManager = new DBManager(driverString);
        }

        return dbManager;
    }

    /**
     * Connects to a database.
     *
     * @param databaseUrl   a database url
     * @param newUsername   a username
     * @param newPassword   a password
     * @throws java.sql.SQLException an exception
     */
    public void connect(String databaseUrl, String newUsername, String newPassword) throws SQLException {
        String dbURL = databaseUrl + "?user=" + newUsername + "&password=" + newPassword;
        if (connection == null) {
            connection = (Connection) DriverManager.getConnection(dbURL);
        }
    }

    /**
     * Disconnects from a database.
     *
     * @throws SQLException an exception
     */
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * Gets a DatabaseMetaData object.
     *
     * @return              a DatabaseMetaData object
     * @throws SQLException an exception
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        if (connection != null) {
            return connection.getMetaData();
        }
        return null;
    }

    /**
     * Gets table names.
     *
     * @return              table names
     * @throws SQLException an exception
     */
    public List getTableNames() throws SQLException {
        if (connection != null) {
            final String TABLE_NAME = "TABLE_NAME";
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, null);
            List list = new ArrayList();
            while(rs.next())  {
                list.add(rs.getString(TABLE_NAME)) ;
            }
            return list;
        }
        return null;
    }

    /**
     * Gets column names.
     *
     * @param tableName     a table name
     * @return              column names
     * @throws SQLException an exception
     */
    public List getColumnNames(String tableName) throws SQLException {
        if (connection != null) {
            final String COLUMN_NAME = "COLUMN_NAME";
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getColumns(null, null, tableName, null);
            List list = new ArrayList();
            while(rs.next())  {
                list.add(rs.getString(COLUMN_NAME));
            }
            return list;
        }
        return null;
    }

    /**
     * Gets column types.
     *
     * @param tableName     a table name
     * @return              column types
     * @throws SQLException an exception
     */
    public List getColumnTypes(String tableName) throws SQLException {
        if (connection != null) {
            final String TYPE_NAME = "TYPE_NAME";
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getColumns(null, null, tableName, null);
            List list = new ArrayList();
            while(rs.next())  {
                list.add(rs.getString(TYPE_NAME));
            }
            return list;
        }
        return null;
    }
}