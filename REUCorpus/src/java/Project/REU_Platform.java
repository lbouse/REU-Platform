/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author josealvarado
 */
public class REU_Platform {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
               
        SQLController s = new SQLController();
        s.LoadSQLPlugin();
        s.ConnectToSever();
        s.query("SELECT * FROM food");
        s.executeQuery();
        s.loadTable();       
        
        s.query("SELECT * FROM customers");
        s.executeQuery();
        s.loadTable();
               
        s.joinTwoTables("joinone", "jointwo");
        
        s.printTables();
        
//        s.insertColumnIntoTable("Employee11", "age", "integer");
//        s.changeColumnName("Employee11", "age", "school", "varchar(10)");
                
//        //"CREATE TABLE Employee11(Emp_code integer, Emp_name varchar(10))"
//        ArrayList fields = new ArrayList(){{add("Emp_code"); add("Emp_name");}};
//        ArrayList fieldTypes = new ArrayList(){{add("integer");add("varchar(10)");}};
//        s.createTable("Employee11",fields, fieldTypes);
//        
//        //st.executeUpdate("INSERT employee VALUES("+13+","+"'Aman'"+")
//        ArrayList input = new ArrayList(){{add("13");add("'Amie'");}};
//        s.insertIntoTable("employee11", input);
        
//        System.out.println("Printing out all Tables");
//        s.clearTableList();
//        s.loadAllTablesFromServer();
//        s.printTables();        
        
        new PlatformControlCenter();
//        
//        new ReadExcelGUI();
    }
}
