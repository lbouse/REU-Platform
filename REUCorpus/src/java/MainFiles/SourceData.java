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
    
    private ArrayList keys;
    private HashMap data;
    
    public SourceData(){
        keys = new ArrayList();
        data = new HashMap();
    }
    
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
