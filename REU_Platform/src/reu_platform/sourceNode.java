/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Bouse
 */
public class sourceNode {
    //-------------------------------------------------------------------
    //                          DECLARATIONS
    //-------------------------------------------------------------------    
    /*Private Variables*/
    private String sourceName;
    private int sourceID;
    private String sourceType;
    private javax.swing.JLabel sourceLabel;
    
    
    /*Public Variables*/
    public LinkedList<sourceNode> sourceLinks = new LinkedList<sourceNode>();
    public LinkedList<javax.swing.JLabel> tableList = 
            new LinkedList<javax.swing.JLabel>();
    public LinkedList<fieldNode> sourceFields = new LinkedList<fieldNode>();
    public javax.swing.JPanel sourcePanel;
    
    /*Public get&set methods*/
    public void setID(int i){ sourceID = i; }
    public void setName(String n){ sourceName = new String(n); }
    public void setType(String n){ sourceType = new String(n); }
    public void setLabel(){ sourceLabel = new javax.swing.JLabel(sourceName); }
    
    public String getName(){ return sourceName; }
    public javax.swing.JLabel getLabel(){ return sourceLabel; }
    
    //-------------------------------------------------------------------
    //                          CONSTRUCTORS
    //-------------------------------------------------------------------   
    public sourceNode(String name, int id)
    {
        setID(id);
        setName(name);
        setLabel();
        
        //Set up the panel
        sourcePanel = new javax.swing.JPanel(new java.awt.GridLayout(0,1) );
        sourceLabel.setFont(new java.awt.Font("Century Gothic", 0, 18));
        sourceLabel.setForeground(new java.awt.Color(102, 102, 102));
        sourcePanel.add(sourceLabel);//Header
        //Add in seperator                                      <<<<<<<<<<<<<   <<<<<<<<<<<
        tableList.add( new javax.swing.JLabel(sourceName) );
        populateSchemaList();
    }
    
    public sourceNode()
    {
        setID(1);
        setName("Source");
        setLabel();
        
        //Set up the panel
        sourcePanel = new javax.swing.JPanel(new java.awt.GridLayout(0,1) );
        sourceLabel.setFont(new java.awt.Font("Century Gothic", 0, 18));
        sourceLabel.setForeground(new java.awt.Color(102, 102, 102));
        sourcePanel.add(sourceLabel);
        //Add in seperator                                      <<<<<<<<<<<<<   <<<<<<<<<<<
        //tableList.add( new javax.swing.JLabel(sourceName) );
    }
    
    //-------------------------------------------------------------------
    //                            METHODS
    //-------------------------------------------------------------------   
    
    /* Populate Schema List */
    /** 
     * Takes a list given to the sourceNode of values and creates
     * a list of fieldNodes with it.
     * TEMPORARILY uses fieldNode as linked list. change this later.   **************************
    **/
    public void populateSchemaList(LinkedList<fieldNode> lst)
    {
        for(int i = 0; i < lst.size(); i++)
        {
            
        }
    }
    
    public void populateSchemaList()
    {
        for(int i = 0; i < (10 + sourceID); i++)
        { 
            sourceFields.add(new fieldNode(sourceName+"Node"+i, sourceID));
            sourcePanel.add(sourceFields.getLast().fieldLabel);
        }
    }
}
