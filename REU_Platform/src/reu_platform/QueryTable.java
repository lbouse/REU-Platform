/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.util.ArrayList;
import java.util.List;

/**
 * @author josealvarado
 */
public class QueryTable {
    
    private ArrayList<ArrayList> table;
    private String name;
    private List<String> schema;
    
    public QueryTable(){
        table = new ArrayList<ArrayList>();
        schema = new ArrayList<String>();
    }
    
    public QueryTable(ArrayList<ArrayList> table){        this.table = table;}
    
    public void setTable(ArrayList<ArrayList> table){     this.table = table;}
    public void setSchema(List<String> schema){         this.schema = schema;}
    public void setName(String name){                   this.name = name;}
    
    public ArrayList<ArrayList> getTable(){   return table;}
    public List<String> getSchema(){        return schema;}
    public String getName(){                return name;}
    
    public void addRow(ArrayList row){      table.add(row);}
    public ArrayList getRow(int k){
        return table.get(k);
    }
    
    public String toString(){
        String returnMe = "Table: "+name+"\nSchema "+schema+"\n";
        for(int i = 0; i < table.size(); i++){
            int length = table.get(i).size();
            for(int j = 0; j < length; j++)
                returnMe += ""+table.get(i).get(j)+"\t";
            returnMe += "\n";
        }
        return returnMe;
    }
}
