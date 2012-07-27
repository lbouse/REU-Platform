/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFiles;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author josealvarado
 */
public class SourceData {
    
    /*
     * Stores the variable names
     */
    private ArrayList keys;
    /*
     * Stores the values, where the variable names are the keys
     */
    private HashMap data;
    
    public SourceData(){
        keys = new ArrayList();
        data = new HashMap();
    }
    
    /*
     * Saves data
     */
    public void save(String key, String value){
        keys.add(key);
        data.put(key, value);
    }
    
    public String getValue(String key){
        return (String)(data.get(key));
    }
    
    public String toString(){
        String returnMe = "";
        for(int i = 0; i < keys.size(); i++){
            returnMe +="\n" + keys.get(i) + " : " + data.get(keys.get(i));
        }
        return returnMe;
    }
    
    public ArrayList getKeys(){
        return keys;
    }
    
    public HashMap getData(){
        return data;
    }
}
