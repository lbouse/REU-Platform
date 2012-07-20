/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Bouse
 */
public class createNewProjectDB {
    public int fieldLength = 5; 
        
    private String destDatabase[] = new String[5];
    private JTextField destFields[] = new JTextField[5];
    
    private String sourceTypes[] = { " Database", " Excel", " XML", " Text", " Website" };
    private JList sourceJList = new JList(sourceTypes);
    private JTextField projNameField = new JTextField();
    private ButtonGroup destRadioGroup = new ButtonGroup();
    private JRadioButton destRadioDB = new JRadioButton("Database");
    private JRadioButton destRadioFile = new JRadioButton("File");
    private JButton nextBtn = new JButton("Next");
    private JButton cancelBtn = new JButton("Cancel");
    private JButton helpBtn = new JButton("Help");    
    private JPanel headerPanel = new JPanel();
    private JPanel createButtonPanel = new JPanel();   
    
    public static javax.swing.DefaultListModel listModelA = new javax.swing.DefaultListModel();
    public static javax.swing.DefaultListModel listModelB = new javax.swing.DefaultListModel();
    private int stepNumber = 0;
    
    //Public variables
    public JPanel dbPanel = new JPanel();
    public String sourceADBType;
    public String sourceBDBType;
    public JTextField sourceAFields[] = new JTextField[fieldLength];
    public JTextField sourceBFields[] = new JTextField[fieldLength];  
    public String srcAFields[] = new String[fieldLength];
    public String srcBFields[] = new String[fieldLength];    
    public javax.swing.JScrollPane scrollPaneA;
    public javax.swing.JScrollPane scrollPaneB;
    public ArrayList tblNamesA = new ArrayList();
    public ArrayList tblNamesB = new ArrayList();    
    
    //Public Methods
    public void setSrcFields(String srcA[], String srcB[])
    {
        srcAFields = srcA;
        srcBFields = srcB;
    }
    
    public void setSrcDBTypes(String srcA, String srcB)
    {
        sourceADBType = new String(srcA);
        sourceBDBType = new String(srcB);
    }
    
    Border border = LineBorder.createGrayLineBorder();
    
//**********************************************************************************************************
    
    public createNewProjectDB(int stepNum) throws ClassNotFoundException, SQLException
    {
        stepNumber = stepNum;
        dbPanel.setLayout( new BoxLayout(dbPanel, BoxLayout.X_AXIS) );
        
        switch(stepNumber)
        {
            case 0: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "createNewProjectDB calld unexpectedly!");
                    break;
            case 1: buildStepTwoA();
                    break;
            case 2: tblNamesA = new ArrayList( loadDBTables(sourceADBType, tblNamesA, srcAFields) );
                    tblNamesB = new ArrayList( loadDBTables(sourceBDBType, tblNamesB, srcBFields)) ; 
                    break;
            default: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "invalid stepNumber occured!");
        }
    }
    
    public createNewProjectDB(int stepNum, String sourceAFields[], String sourceBFields[],
            String srcADBType, String srcBDBType) throws ClassNotFoundException, SQLException
    {
        stepNumber = stepNum;
        dbPanel.setLayout( new BoxLayout(dbPanel, BoxLayout.X_AXIS) );
        setSrcFields(sourceAFields, sourceBFields);
        setSrcDBTypes(srcADBType, srcBDBType);
        
        switch(stepNumber)
        {
            case 0: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "createNewProjectDB calld unexpectedly!");
                    break;
            case 1: buildStepTwoA();
                    break;
            case 2: tblNamesA = new ArrayList( loadDBTables(sourceADBType, tblNamesA, srcAFields) );
                    tblNamesB = new ArrayList( loadDBTables(sourceBDBType, tblNamesB, srcBFields)) ;                
                    break;
            default: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "invalid stepNumber occured!");
        }        
    }
    
        //Step two, source type "Database"
    public void buildStepTwoA()
    {           
            JPanel innerPanelA = new JPanel();
            innerPanelA.setMaximumSize(new Dimension(300, 400));
            innerPanelA.setMinimumSize(new Dimension(100, 100));
            innerPanelA.setLayout( new BoxLayout(innerPanelA, BoxLayout.Y_AXIS) );
            
            JLabel headerA = new JLabel("Source A");
            headerA.setFont(new java.awt.Font("Century Gothic", 0, 18));
            headerA.setForeground(new java.awt.Color(102, 102, 102));
            innerPanelA.add(headerA);   
            
            innerPanelA.add( Box.createRigidArea(new Dimension(0,25)) );
            
            String[] dbStrings = { "Oracle", "mySQL", "SQL Server", "PostgreSQL" };
            final JComboBox dbBoxA = new JComboBox(dbStrings);
            dbBoxA.addActionListener( new java.awt.event.ActionListener(){
               public void actionPerformed(java.awt.event.ActionEvent e){
                   JComboBox cb = (JComboBox)e.getSource();
                   sourceADBType = (String)cb.getSelectedItem();
               } 
            });
            
            dbBoxA.setMaximumSize(new Dimension(200, 20));            
            dbBoxA.setAlignmentX(Component.LEFT_ALIGNMENT);
            innerPanelA.add(dbBoxA);
            innerPanelA.add( Box.createRigidArea(new Dimension(0,5)) );
            
            JPanel srcARows[] = new JPanel[5];
            JLabel srcALabels[] = new JLabel[5];
            
            buildDBConnectPanel("Source A", innerPanelA, sourceAFields, srcARows, srcALabels );
            innerPanelA.setAlignmentX(Component.LEFT_ALIGNMENT);
            dbPanel.add(innerPanelA);

            dbPanel.add( Box.createRigidArea(new Dimension(20,0)) );
               
            JPanel srcBRows[] = new JPanel[5];
            JLabel srcBLabels[] = new JLabel[5];
               
            JPanel innerPanelB = new JPanel();
            innerPanelB.setMaximumSize(new Dimension(300, 400));
            innerPanelB.setMinimumSize(new Dimension(100, 100));
            innerPanelB.setLayout( new BoxLayout(innerPanelB, BoxLayout.Y_AXIS) );
            
            JLabel headerB = new JLabel("Source B");
            headerB.setFont(new java.awt.Font("Century Gothic", 0, 18));
            headerB.setForeground(new java.awt.Color(102, 102, 102));
            innerPanelB.add(headerB);   
            
            innerPanelB.add( Box.createRigidArea(new Dimension(0,25)) );            
            
            String[] dbStringsB = { "Oracle", "mySQL", "SQL Server", "PostgreSQL" };
            final JComboBox dbBoxB = new JComboBox(dbStringsB);
            dbBoxB.addActionListener( new java.awt.event.ActionListener(){
               public void actionPerformed(java.awt.event.ActionEvent e){
                   JComboBox cb = (JComboBox)e.getSource();
                   sourceBDBType = (String)cb.getSelectedItem();
               } 
            });
            
            dbBoxB.setMaximumSize(new Dimension(200, 20));            
            dbBoxB.setAlignmentX(Component.LEFT_ALIGNMENT);
            innerPanelB.add(dbBoxB);
            innerPanelB.add( Box.createRigidArea(new Dimension(0,5)) );            
            
            buildDBConnectPanel("Source B", innerPanelB, sourceBFields, srcBRows, srcBLabels );
            innerPanelB.setAlignmentX(Component.LEFT_ALIGNMENT);
            dbPanel.add(innerPanelB);
    }/* end buildStepTwoA */
    
    //Next screen from buildStepTwoA: Database
    public void buildStepThreeA() throws ClassNotFoundException, SQLException
    {     
        ArrayList tblNamesA = new ArrayList();
        ArrayList tblNamesB = new ArrayList();

        tblNamesA = new ArrayList( loadDBTables(sourceADBType, tblNamesA, srcAFields) );
        tblNamesB = new ArrayList( loadDBTables(sourceBDBType, tblNamesB, srcBFields)) ;
        
      //Load tables into List
//        for(int i = 0; i < tblNamesA.size(); i++ )
//        { listModelA.addElement( tblNamesA.get(i) ); }
//
//        for(int i = 0; i < tblNamesB.size(); i++ )
//        { listModelB.addElement( tblNamesB.get(i) ); }
        
        String lsA[] = {"A One", "A Two", "A Three"};
        String lsB[] = {"B Beep", "B Boop", "B Laala", "B Bububuu"};
        
      //Create source tables lists
        javax.swing.JList tableAList = new javax.swing.JList(lsA);
        
        tableAList.setLayoutOrientation(JList.VERTICAL);
        tableAList.setVisibleRowCount(20);
        tableAList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPaneA = new javax.swing.JScrollPane(tableAList);        
        scrollPaneA.setPreferredSize( new Dimension(100,200) );
        scrollPaneA.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(scrollPaneA);

        javax.swing.JList tableBList = new javax.swing.JList(lsB);
        
        tableBList.setLayoutOrientation(JList.VERTICAL);
        tableBList.setVisibleRowCount(20);
        tableBList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);    
           
        scrollPaneB = new javax.swing.JScrollPane(tableBList);   
        scrollPaneB.setPreferredSize( new Dimension(100, 200) );  
        scrollPaneB.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(scrollPaneB);
    }    
    
    public ArrayList loadDBTables(String dbType, ArrayList tblList, String srcFields[]) 
            throws ClassNotFoundException, SQLException    
    {
        if(dbType.equals("Oracle"))
        {
            OracleController s = new OracleController();
            s.LoadOraclePlugin();
            s.ConnectToServer(srcFields[0], Integer.parseInt(srcFields[1]), 
                    srcFields[2], srcFields[3], srcFields[4]);

            s.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
            s.loadTable();
            tblList = s.getTableNamesFromServer();

        }
        else if(dbType.equals("mySQL"))
        {
            SQLController s = new SQLController();           
            s.LoadSQLPlugin();
            s.ConnectToServer(srcFields[0], Integer.parseInt(srcFields[1]), 
                    srcFields[2], srcFields[3], srcFields[4]);
                   
            s.allThree("SELECT * FROM INFORMATION_SCHEMA.TABLES");
            tblList = s.getTableNamesFromServer();
        }
        else if(dbType.equals("SQL Server"))
        {
            SQLServerController s = new SQLServerController();
            s.LoadSQLServerPlugin();
            s.ConnectToServer(srcFields[0], Integer.parseInt(srcFields[1]), 
                    srcFields[2], srcFields[3], srcFields[4]);
            
            s.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
            s.loadTable();                
            tblList = s.getTableNamesFromServer();
        }
        else if(dbType.equals("PostgreSQL"))
        {
            PostgreController s = new PostgreController();
            s.LoadPostgrePlugin();
            s.ConnectToServer(srcFields[0], Integer.parseInt(srcFields[1]), 
                    srcFields[2], srcFields[3], srcFields[4]);
            
            s.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
            s.loadTable();                
            tblList = s.getTableNamesFromServer();
        }
        else{ JOptionPane.showMessageDialog(null, "Error!\n"
                + "Error found in buildStepFourA:  if(sourceDBType.equals..."
                + "\nSource type '" + dbType + "' not found.");
                tblList = new TableList();
        }        
        
        return tblList;
    }            
    
    public void buildDBConnectPanel(String title, JPanel innerPanel, JTextField textFields[],
            JPanel destRows[], JLabel destLabels[])
    {
          //Row 0: "Host: "
            destRows[0] = new JPanel();
            destRows[0].setMaximumSize(new Dimension(200, 20));
            destRows[0].setLayout( new BoxLayout(destRows[0], BoxLayout.X_AXIS) );
            destLabels[0] = new JLabel("Host:");
            textFields[0] = new JTextField();
            
            destLabels[0].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[0].add(destLabels[0]);
            destRows[0].add( Box.createRigidArea(new Dimension(10, 0)) );
            textFields[0].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[0].add(textFields[0]);
            
            //Row 1: "Port: "
            destRows[1] = new JPanel();
            destRows[1].setMaximumSize(new Dimension(200, 20));
            destRows[1].setLayout( new BoxLayout(destRows[1], BoxLayout.X_AXIS) );
            destLabels[1] = new JLabel("Port:");
            textFields[1] = new JTextField(5);
            
            destLabels[1].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[1].add(destLabels[1]);
            destRows[1].add( Box.createRigidArea(new Dimension(10, 0)) );
            textFields[1].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[1].add(textFields[1]);
            
            //Row 2: "Database: "
            destRows[2] = new JPanel();
            destRows[2].setMaximumSize(new Dimension(200, 20));
            destRows[2].setLayout( new BoxLayout(destRows[2], BoxLayout.X_AXIS) );
            destLabels[2] = new JLabel("Database:");
            textFields[2] = new JTextField();
            
            destLabels[2].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[2].add(destLabels[2]);
            destRows[2].add( Box.createRigidArea(new Dimension(10, 0)) );
            textFields[2].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[2].add(textFields[2]);            
            
            //Row 3: "Username: "
            destRows[3] = new JPanel();
            destRows[3].setMaximumSize(new Dimension(200, 20));
            destRows[3].setLayout( new BoxLayout(destRows[3], BoxLayout.X_AXIS) );
            destLabels[3] = new JLabel("Username:");
            textFields[3] = new JTextField();
            
            destLabels[3].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[3].add(destLabels[3]);
            destRows[3].add( Box.createRigidArea(new Dimension(10, 0)) );
            textFields[3].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[3].add(textFields[3]);            
            
            //Row 4: "Password: "
            destRows[4] = new JPanel();
            destRows[4].setMaximumSize(new Dimension(200, 20));
            destRows[4].setLayout( new BoxLayout(destRows[4], BoxLayout.X_AXIS) );
            destLabels[4] = new JLabel("Password:");
            textFields[4] = new JTextField();
            
            destLabels[4].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[4].add(destLabels[4]);
            destRows[4].add( Box.createRigidArea(new Dimension(10, 0)) );
            textFields[4].setAlignmentX(Component.LEFT_ALIGNMENT);
            destRows[4].add(textFields[4]);       
            
            //Add all rows to innerPanelRight
            for(int i = 0; i < destRows.length; i++)
            {   destRows[i].setAlignmentX(Component.LEFT_ALIGNMENT);
                innerPanel.add(destRows[i]);
                innerPanel.add( Box.createRigidArea(new Dimension(0, 5)) );
            }
    
    }/* end public method buildDBConnectPanel() */    
   
   public boolean checkAll()
   {
       boolean rtn = true;
       
       if( sourceAFields[0].getText().isEmpty() ){ sourceAFields[0].setText("localhost"); }
       if( sourceAFields[3].getText().isEmpty() ){ sourceAFields[3].setText("root"); }

       if( sourceBFields[0].getText().isEmpty() ){ sourceBFields[0].setText("localhost"); }
       if( sourceBFields[3].getText().isEmpty() ){ sourceBFields[3].setText("root"); }
       
       for(int i = 0; i < fieldLength; i++)
       {    if(i != 1 && i != 4)
            {    if( sourceAFields[i].getText().isEmpty() || sourceBFields[i].getText().isEmpty()
                        || sourceADBType.isEmpty() || sourceBDBType.isEmpty() )
                { rtn = false; }
            }
       }
       
       return rtn;
   }
}