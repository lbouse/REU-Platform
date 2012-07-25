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
    private ArrayList<Integer> dataSizes = new ArrayList<Integer>();
    
    public DataClass(){
        name = "";
    }
    
    public DataClass(String name){
        this.name = name;
    }
    
    public void addNewSize(int size){
        dataSizes.add(size);
    }
    
    public ArrayList<Integer> getDataSizes(){
        return dataSizes;
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
    
    public void replace(DataClass real){
        this.clear();
        for(int i = 0; i < real.size(); i++){
            this.add(real.get(i));
        }
    }
    
    public String toString(){
        String returnMe = "\nData Class - " + name + "\n";
        for(int i = 0; i < this.size(); i++){
            returnMe += "\n"+this.get(i);
        }
        return returnMe + "\n";
    }
}
