/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.util.ArrayList;

/**
 *
 * @author josealvarado
 */
public class TableList extends ArrayList<QueryTable>{

    public String toString(){
        String returnMe = "Complete Table List\n\n";
        for(QueryTable table: this)
            returnMe+= ""+table+"\n";
        return returnMe;
    }
    
    public QueryTable getTable(String tableName){
        for(QueryTable table: this){
            if(table.getName().equals(tableName)){
                return table.copy();
            }
        }
        return null;
    }
}
