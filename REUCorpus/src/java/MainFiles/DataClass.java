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
public class DataClass extends ArrayList{
    
    private String name;
    
    public DataClass(){
        name = "";
    }
    
    public DataClass(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public DataClass copy(){
        DataClass returnMe = new DataClass(this.name);
        for(int i = 0; i < this.size(); i++){
            returnMe.add(this.get(i));
        }
        return returnMe;
    }
    
    public String toString(){
        String returnMe = "\nData Class - " + name + "\n";
        for(int i = 0; i < this.size(); i++){
            returnMe += "\n"+this.get(i);
        }
        return returnMe + "\n";
    }
}
