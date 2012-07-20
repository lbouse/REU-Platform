/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Bouse
 * "sourceNode" lists all the contents of a table in a database.
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
    public int fieldCount;
    
    private String srcType;
    private String dbType;
    
    private SQLController sqlContr;
    private OracleController oContr;
    private SQLServerController ssContr;
    private PostgreController greContr;
    
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
    public sourceNode(String[] dbInfo, String name, int id, int fieldCnt, String sType,
            String dType) 
            throws ClassNotFoundException, SQLException 
    {
        setID(id);
        setName(name);
        setLabel();
        fieldCount = fieldCnt;
        srcType = new String(sType);
        dbType = new String(dType);
        
        if(srcType.equals(" Database"))
        {
            if(dbType.equals("mySQL"))
            {
                sqlContr = new SQLController();
                sqlContr.LoadSQLPlugin(); 
                sqlContr.ConnectToServer(dbInfo[0], Integer.valueOf(dbInfo[1]), dbInfo[2], dbInfo[3], dbInfo[4]);              
            }
            if(dbType.equals("Oracle"))
            {
                oContr = new OracleController();
                oContr.LoadOraclePlugin();
                oContr.ConnectToServer(dbInfo[0], Integer.valueOf(dbInfo[1]), dbInfo[2], dbInfo[3], dbInfo[4]);
            }
            if(dbType.equals("SQL Server"))
            {
                ssContr = new SQLServerController();
                ssContr.LoadSQLServerPlugin();
                ssContr.ConnectToServer(dbInfo[0], Integer.valueOf(dbInfo[1]), dbInfo[2], dbInfo[3], dbInfo[4]);
            }       
            if(dbType.equals("Postgre"))
            {
                greContr = new PostgreController();
                greContr.LoadPostgrePlugin();
                greContr.ConnectToServer(dbInfo[0], Integer.valueOf(dbInfo[1]), dbInfo[2], dbInfo[3], dbInfo[4]);
            }            
        }

        
        //Set up the panel
        sourcePanel = new javax.swing.JPanel();
        sourcePanel.setLayout( 
                new javax.swing.BoxLayout(sourcePanel, javax.swing.BoxLayout.PAGE_AXIS));
        
        //Make background transparent..
        sourcePanel.setOpaque(false);
        sourceLabel.setFont(new java.awt.Font("Century Gothic", 0, 18));
        sourceLabel.setForeground(new java.awt.Color(102, 102, 102));
        sourcePanel.add(sourceLabel);//Header
        
        tableList.add( new javax.swing.JLabel(name) );
        populateSchemaList(name);
    }
    
    public sourceNode()
    {
        setID(1);
        setName("Source");
        setLabel();
        
        //Set up the panel
        //sourcePanel = new javax.swing.JPanel(new java.awt.GridLayout(0,1) );
        sourcePanel = new javax.swing.JPanel(
                new javax.swing.BoxLayout(sourcePanel, javax.swing.BoxLayout.PAGE_AXIS));
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
    public void populateSchemaList(String table) throws ClassNotFoundException, SQLException 
    {
        TableList tableList = new TableList();
        
        if(srcType.equals(" Database"))
        {
            if(dbType.equals("mySQL"))
            {
                sqlContr.allThree("SELECT * FROM " + table);
                tableList = sqlContr.tableList;
            }
            if(dbType.equals("Oracle"))
            {
                oContr.executeQuery("SELECT * FROM " + table);
                oContr.loadTable();
                tableList = oContr.tableList;
            }
            if(dbType.equals("SQL Server"))
            {
                ssContr.executeQuery("SELECT * FROM " + table);
                ssContr.loadTable();
                tableList = ssContr.tableList;
            }       
            if(dbType.equals("Postgre"))
            {
                greContr.executeQuery("SELECT * FROM " + table);
                greContr.loadTable();
                tableList = greContr.tableList;
            }            
        }
        
        sourcePanel.add( Box.createRigidArea(new Dimension(0, 10)) );
        
        for(int i = 0; i < tableList.size(); i++)
        {
            for(int j = 0; j < tableList.get(i).getSchema().size(); j ++)
            {
                sourceFields.add(new fieldNode( tableList.get(i).getSchema().get(j), sourceID, fieldCount));
                sourceFields.getLast().setPreferredSize(new Dimension(sourcePanel.getSize().width - 10, 15));
                
                sourceFields.getLast().setAlignmentX(Component.LEFT_ALIGNMENT);
                sourcePanel.add(sourceFields.getLast());
                sourcePanel.add(Box.createRigidArea(new Dimension(0,5)));
                sourceFields.getLast().setPoint();
                fieldCount++;
            }
        }
        sourcePanel.add(Box.createVerticalGlue());
        sourcePanel.revalidate();
    }/* end method populateSchemaList */
    
    public void populateSchemaList()
    {
        for(int i = 0; i < (10 + sourceID); i++)
        { 
            sourceFields.add(new fieldNode(sourceName+"Node"+i, sourceID, i+1));
            sourcePanel.add(sourceFields.getLast());
            sourceFields.getLast().setPoint();
            sourcePanel.revalidate();
        }
    }/* end method populateSchemaList */
}