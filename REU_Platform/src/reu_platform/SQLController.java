package reu_platform;


import java.sql.*;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josealvarado
 */
public class SQLController {
    
    Connection con;
    PreparedStatement statement;
    ResultSet result;
    ResultSetMetaData rsmd;
    DatabaseMetaData dmd;
//    ParameterMetaData pmd;
    TableList tableList = new TableList();
    
    /*
     * Loads the JDBC driver if not already loaded
     */
    public void LoadSQLPlugin() throws ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
    }
    
    /*
     * Connects to localhost server setup up by XAMPP, and accesses the database called test
     */
    public void ConnectToSever() throws SQLException{
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test", "root", "");
    }
    
    /*
     * Connects to host server given the portNumber, database, user, and password
     */
    public void ConnectToServer(String host, int portNumber, String database, String user, String password) throws SQLException{
        con = DriverManager.getConnection(
                "jdbc:mysql://"+host+":"+portNumber+"/"+database, user, password);
    }
    
    /*
     * Generates the query. Parse the query and check what's its doing.
     */
    public void query(String query) throws SQLException{
        statement = con.prepareStatement(query);
    }
    
    /*
     * Executes the query
     */
    public void executeQuery() throws SQLException{
        result = statement.executeQuery();
        rsmd = result.getMetaData();
        dmd = con.getMetaData();     
    }
    
    /*
     * Loads as much information from the table as possible
     */
    public void loadTable() throws SQLException{
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
        
        //Returns the database name
        //System.out.println("1"+rsmd.getCatalogName(1));
    }
    
    public void allThree(String query) throws SQLException{
        query(query);
        executeQuery();
        loadTable();
    }
    
    /*
     * If possible, displays the query. Not every query can be or should be displayed
     */
    public void printTables() throws SQLException{
//        while(result.next()){   
//            System.out.print(""+result.getString(1)+" "+result.getString(2));
//            System.out.println(" "+result.getString("calories")+" "+result.getString("healthy_unhealthy"));
//        }
        System.out.println(""+tableList);
    }
    
    public ArrayList getTableNamesFromServer() throws SQLException{
        
        ResultSet rs = dmd.getTables(null, null, "%", null);
        ArrayList tableNames = new ArrayList();
        while (rs.next()) {
            tableNames.add(rs.getString(3));
        }
        return tableNames;
    }
    
    public void loadAllTablesFromServer() throws SQLException{
        
        ArrayList allTableNames = getTableNamesFromServer();
        for(int i = 0; i < allTableNames.size(); i++){
            allThree("SELECT * FROM "+allTableNames.get(i));
        }
    }
    
    public void clearTableList(){
        tableList = new TableList();
    }
    
    public void createDatabase(String databaseName) throws SQLException{
        statement.executeUpdate("CREATE DATABASE "+databaseName);
    }
    
    public void createTable(String tableName, ArrayList fields, ArrayList fieldTypes) throws SQLException{
        if(fields.size()==fieldTypes.size()){
            String table = "";
            table += ""+tableName+"(";
            for(int i = 0; i < fields.size(); i++){
                table += ""+fields.get(i)+" "+fieldTypes.get(i)+", ";
            }
            table = table.substring(0, table.length()-2);
            table += ")";
            System.out.println(""+table);
            statement.executeUpdate("CREATE TABLE "+table);
        }
    }
    
    public void deleteTable(String tableName) throws SQLException{
        statement.executeUpdate("DROP TABLE "+tableName);
    }
     
    /*
     * EX. st.executeUpdate("INSERT employee VALUES("+13+","+"'Aman'"+")
     * It took 
     */
    public void insertIntoTable(String tablename, ArrayList values) throws SQLException{
        String str = "(";
        for(int i = 0; i < values.size(); i++){
            str += values.get(i)+",";
        }
        str = str.substring(0, str.length()-1);
        str += ")";
        System.out.println(""+str);
        statement.executeUpdate("INSERT "+tablename+" VALUES"+str);
    }
}
