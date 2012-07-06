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
    private ArrayList<String> schema;
    
    public QueryTable(){
        table = new ArrayList<ArrayList>();
        schema = new ArrayList<String>();
    }
    
    public QueryTable(ArrayList<ArrayList> table){        this.table = table;}
    
    public void setTable(ArrayList<ArrayList> table){     this.table = table;}
    public void setSchema(ArrayList<String> schema){         this.schema = schema;}
    public void setName(String name){                   this.name = name;}
    
    public ArrayList<ArrayList> getTable(){   return table;}
    public List<String> getSchema(){        return schema;}
    public String getName(){                return name;}
    
    public void addRow(ArrayList row){      table.add(row);}
    public ArrayList getRow(int k){
        return table.get(k);
    }
    
    public QueryTable getRows(ArrayList rowList){
        for(int i = 0; i < rowList.size(); i++){
            if(Integer.parseInt(""+rowList.get(i)) > table.size()){
                return null;
            }
        }
        QueryTable returnMe = new QueryTable();
        for(int i = 0; i < table.size(); i++){
            returnMe.addRow(table.get(i));
        }
        return returnMe;
    }
    
    public void addColumn(ArrayList column, String columnName){
        if(column.size() == table.size()){
            schema.add(name);
            for(int i = 0; i < table.size(); i++){
                table.get(i).add(column.get(i));
            }    
        }
    }
    
    public ArrayList getColumn(int num){
        if(num>table.get(0).size())
            return null;
        ArrayList column = new ArrayList();
        for(int i = 0; i < table.size(); i++){
            column.add(table.get(i).get(num));
        }
        return column;
    }
    
    public QueryTable getColumns(ArrayList columnList){
        for(int i = 0; i < columnList.size(); i++){
            if(Integer.parseInt(""+columnList.get(i)) > table.get(0).size())
                return null;
        }
        QueryTable returnMe = new QueryTable();
        for(int i = 0; i < table.size(); i++){
            ArrayList tableRow = table.get(i);
            ArrayList addRow = new ArrayList();
            for(int j = 0; j < columnList.size(); j++){
                addRow.add(tableRow.get(Integer.parseInt(""+columnList.get(j))));
            }
            returnMe.addRow(addRow);
        }
        return returnMe;
    }
    
    public QueryTable copy(){
        QueryTable returnMe = new QueryTable();
        for(int i = 0; i < table.size(); i++){
            returnMe.addRow(this.getRow(i));
        }
        return returnMe;
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
