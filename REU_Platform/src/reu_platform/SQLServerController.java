/* OracleController
 * Developed as a variation of SQLController.java
 *  to use with Oracle Databases
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Bouse
 */
public class SQLServerController {
    /* Private Variables */
    Connection conn;
    PreparedStatement statement;
    ResultSet result;
    ResultSetMetaData rsmd;
    DatabaseMetaData dmd;    
    TableList tableList = new TableList();
    
    /* Public Methods */
    public void LoadSQLServerPlugin() throws ClassNotFoundException
    { Class.forName("net.sourceforge.jtds.jdbc.Driver"); }
    
    public void ConnectToServer() throws SQLException
    { conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/test", "root", ""); }
    
    public void ConnectToServer(String host, int port, String database, 
            String userName, String pword) throws SQLException
    {
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + host + ":" 
                + port + "/" + database, userName, pword);
    }
    
    public void ConnectToServer(String database) throws SQLException
    {
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/" + database, "root", "");
    }
    
    public void ConnectToServer(String host, String port, String database)
            throws SQLException
    {
        conn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + host + ":"
                + port + "/" + database, "root", "");
    }
    
    /* Enter a String of the query */
    public void executeQuery(String query) throws SQLException
    {
        statement = conn.prepareStatement(query);
        result = statement.executeQuery();
        rsmd = result.getMetaData();
        dmd = conn.getMetaData();
    }
    
    public void loadTable() throws SQLException
    {
        QueryTable table = new QueryTable();
        ArrayList row, schema = new ArrayList();
        while(result.next()){
            row = new ArrayList();
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
                row.add(result.getString(i));
            table.addRow(row);
            
        }
        
        for(int i = 1; i <= rsmd.getColumnCount(); i++)
                schema.add(""+rsmd.getColumnName(i));
        table.setSchema(schema);
        table.setName(""+rsmd.getTableName(1));
        tableList.add(table);        
    }
    
    public void printTables()
    { System.out.println(""+tableList); }
    
    public ArrayList getTableNamesFromServer() throws SQLException{
        
        ResultSet rs = dmd.getTables(null, null, "%", null);
        ArrayList tableNames = new ArrayList();
        while (rs.next()) 
        { tableNames.add(rs.getString(3)); }
        return tableNames;
    }
    
    public void loadAllTablesFromServer() throws SQLException
    {
        ArrayList allTableNames = getTableNamesFromServer();
        for(int i = 0; i < allTableNames.size(); i++)
        {
            executeQuery("SELECT * FROM "+allTableNames.get(i));
            loadTable();
        }
    }
    
    public void clearTableList()
    { tableList = new TableList(); }
    
    public void createDatabase(String databaseName) throws SQLException
    { statement.executeUpdate("CREATE DATABASE " + databaseName); }
    
    public void createTable(String tableName, ArrayList fields, ArrayList fieldTypes) throws SQLException
    {
        if(fields.size()==fieldTypes.size())
        {
            String table = "";
            table += ""+tableName+"(";
            for(int i = 0; i < fields.size(); i++)
            { table += ""+fields.get(i)+" "+fieldTypes.get(i)+", "; }
            table = table.substring(0, table.length()-2);
            table += ")";
            System.out.println(""+table);
            statement.executeUpdate("CREATE TABLE "+table);
        }
    }
    
    public void deleteTable(String tableName) throws SQLException
    { statement.executeUpdate("DROP TABLE "+tableName); }
    
    /*
     * EX. st.executeUpdate("INSERT employee VALUES("+13+","+"'Aman'"+")
     * It took 
     */
    public void insertIntoTable(String tablename, ArrayList values) throws SQLException
    {
        String str = "(";
        for(int i = 0; i < values.size(); i++)
        { str += values.get(i)+","; }
        str = str.substring(0, str.length()-1);
        str += ")";
        System.out.println(""+str);
        statement.executeUpdate("INSERT "+tablename+" VALUES"+str);
    }
    
}
