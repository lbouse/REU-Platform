/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFiles;

import java.util.ArrayList;

/**
 *
 * @author josealvarado
 */
public class OnlineDatabase extends ArrayList<SourceData>{
    
    /*
     * @param name stores the url
     */
    private String name;
    
    /*
     * Default constructor
     */
    public OnlineDatabase(){
        
    }
    
    public OnlineDatabase(String name){
        this.name = name;
    }
    
    /*
     * Returns the variable names, every element in OnlineDatabase should have the same variable names
     */
    public ArrayList getSchema(){
        return ((SourceData)(this.get(0))).getKeys();
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
