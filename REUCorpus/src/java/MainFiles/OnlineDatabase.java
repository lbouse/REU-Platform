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
public class OnlineDatabase extends ArrayList{
    
    public String name;
    
    public OnlineDatabase(){
        
    }
    
    public OnlineDatabase(String name){
        this.name = name;
    }
    
    public ArrayList getSchema(){
        return ((SourceData)(this.get(0))).getKeys();
    }
    
    
}
