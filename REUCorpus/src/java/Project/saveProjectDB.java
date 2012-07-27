/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

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
public class saveProjectDB {
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
    private int stepNumber = 0;
    
    //Public variables
    public JPanel dbPanel = new JPanel();
    public String sourceADBType;
    public JTextField sourceAFields[] = new JTextField[fieldLength];
    public String srcAFields[] = new String[fieldLength];
    public javax.swing.JScrollPane scrollPaneA;
    public ArrayList tblNamesA = new ArrayList();
    
    //Get & Set methods
    public void setSrcFields(String srcA[]){ srcAFields = srcA; }
    public void setSrcDBTypes(String srcA){ sourceADBType = new String(srcA); }
    
    public String[] getSrcFields(){ return srcAFields; }
    public String getSrcDBTypes(){ return sourceADBType; }
    
    Border border = LineBorder.createGrayLineBorder();
    
/*******************************************************************************
**                                CONSTRUCTORS                                **
*******************************************************************************/   
    
    public saveProjectDB() throws ClassNotFoundException, SQLException
    {
        JPanel innerPanelA = new JPanel();
        innerPanelA.setMaximumSize(new Dimension(300, 400));
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

        dbBoxA.setPreferredSize(new Dimension(200, 20));
        dbBoxA.setAlignmentX(Component.LEFT_ALIGNMENT);
        innerPanelA.add(dbBoxA);
        innerPanelA.add( Box.createRigidArea(new Dimension(0,5)) );

        JPanel srcARows[] = new JPanel[5];
        JLabel srcALabels[] = new JLabel[5];

        buildDBConnectPanel("Source A", innerPanelA, sourceAFields, srcARows, srcALabels );
        innerPanelA.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(innerPanelA);        
    }/* end constructor saveProjectDB() */
    

/*******************************************************************************
**                             GENERAL METHODS                                **
*******************************************************************************/       
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
            destRows[0].setPreferredSize(new Dimension(200, 20));
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
            destRows[1].setPreferredSize(new Dimension(200, 20));
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
            destRows[2].setPreferredSize(new Dimension(200, 20));
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
            destRows[3].setPreferredSize(new Dimension(200, 20));
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
            destRows[4].setPreferredSize(new Dimension(200, 20));
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
       
       for(int i = 0; i < fieldLength; i++)
       {    if(i != 1 && i != 4)
            {   if( sourceAFields[i].getText().isEmpty() || sourceADBType.isEmpty() )
                { rtn = false; }
            }
       }
       
       return rtn;
   }
}