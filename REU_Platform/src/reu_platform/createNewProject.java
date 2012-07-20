/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reu_platform;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;    //**** For test purposes...
import javax.swing.border.LineBorder; //****** remove when program is finished!!
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Bouse
 */
public class createNewProject extends javax.swing.JFrame {
    //Declaring some public variables.
    private LinkedList<javax.swing.JLabel> userSources = new LinkedList<javax.swing.JLabel>();
    private LinkedList<javax.swing.JButton> sourceButtons = new LinkedList<javax.swing.JButton>();
    private LinkedList<sourceNode> sourceList = new LinkedList<sourceNode>();
    private javax.swing.JList tempList = new javax.swing.JList();
    public static javax.swing.DefaultListModel listModelA;
    public static javax.swing.DefaultListModel listModelB;
    private int cnt = 0;
    
    private String destDatabase[] = new String[5];
    private String sourceDBType;
    private JTextField destFields[] = new JTextField[5];
    private JTextField sourceAFields[] = new JTextField[5];
    private JTextField sourceBFields[] = new JTextField[5];
    
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
    
    Border border = LineBorder.createGrayLineBorder();
    
    private int stepNumber = 0;
    
    /**
     * Creates new form createProject
     */
    public createNewProject()
    {
        initComponents();
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        //Create the header panel for the frame
        headerPanel.setMinimumSize(new Dimension(490, 15));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        JLabel header = new JLabel("New Project");
        header.setFont(new java.awt.Font("Century Gothic", 0, 22));
        header.setForeground(new java.awt.Color(102, 102, 102));
        headerPanel.add( Box.createRigidArea(new Dimension(5, 0)) );
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(header);
        
        //BUTTON PANEL
        // [NEXT/FINISH] - [CANCEL] - [HELP] Options
        createButtonPanel.setLayout( new BoxLayout(createButtonPanel, BoxLayout.X_AXIS) );
        createButtonPanel.setMinimumSize( new Dimension(450, 50) );

        /*Add the buttons's Action Listeners*/
        nextBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                }
 } });
        cancelBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                }
 } });
        helpBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                }
 } });
        
        createButtonPanel.add( Box.createHorizontalGlue() );
        nextBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        createButtonPanel.add(nextBtn);
        cancelBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        createButtonPanel.add(cancelBtn);
        helpBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        createButtonPanel.add(helpBtn);        
        
        buildStepOne();
    }
    
    /* buildStepOne:
     * The first step in creating a new project: The user will select a source type:
     * Database, Excel, XML, Website, or Text
     * and then enter a project name. 
    **/
    public void buildStepOne()
    {
        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(headerPanel);        
        
        //Display "Source types"
        JPanel sourceTypePanel = new JPanel();
        headerPanel.setMinimumSize(new Dimension(490, 15));
        sourceTypePanel.setLayout(new BoxLayout(sourceTypePanel, BoxLayout.X_AXIS));
        JLabel sourceHeader = new JLabel("Source Type");
        sourceHeader.setFont(new java.awt.Font("Century Gothic", 0, 18));
        sourceHeader.setForeground(new java.awt.Color(102, 102, 102));
        sourceTypePanel.add( Box.createRigidArea(new Dimension(10, 0)) );
        sourceHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceTypePanel.add(sourceHeader);
              
        mainPanel.add( Box.createRigidArea( new Dimension(0, 5)) );
        sourceTypePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(sourceTypePanel);
        
       //Display Source Type list to sourceTypePanel
        sourceJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        sourceJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        sourceJList.setVisibleRowCount(-1);
        sourceJList.setFixedCellWidth(300);
        sourceJList.setFixedCellHeight(20);
        
        sourceJList.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(sourceJList);
        mainPanel.add( Box.createVerticalGlue() );
        mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        
        
        //projName row: includes JLabel "Project Name:" and JTextField for name\
        JPanel projNamePanel = new JPanel();
        projNamePanel.setMaximumSize(new Dimension(450, 30));
        projNamePanel.setMinimumSize(new Dimension(400, 20));
        projNamePanel.setLayout(new BoxLayout(projNamePanel, BoxLayout.X_AXIS));
        JLabel projNameLabel = new JLabel("Project Name:");
        projNameLabel.setFont(new java.awt.Font("Century Gothic", 0, 14));
        projNameLabel.setForeground(new java.awt.Color(102, 102, 102));
        projNamePanel.add( Box.createRigidArea(new Dimension(10, 0)) );
        projNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        projNamePanel.add(projNameLabel);
        projNamePanel.add( Box.createRigidArea(new Dimension(10, 0)) );
        projNameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        projNamePanel.add(projNameField);
        
        projNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(projNamePanel);
        
        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(createButtonPanel);        
        mainPanel.revalidate();
    }/* end buildStepOne */
    
    public void buildStepTwo( final String projName, final String srcType ) throws ClassNotFoundException, SQLException
    {
        if( srcType.equals(" Database") )
        {
            mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
            headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(headerPanel); 
            
            final createNewProjectDB s = new createNewProjectDB(stepNumber);
            s.dbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(s.dbPanel);
            
            nextBtn.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String srcAStr[] = new String[s.fieldLength];
                    String srcBStr[] = new String[s.fieldLength];
                    if(s.checkAll()){
                        for(int i = 0; i < s.sourceAFields.length; i++)
                        { srcAStr[i] = new String(s.sourceAFields[i].getText()); }
                        
                        for(int i = 0; i < s.sourceBFields.length; i++)
                        { srcBStr[i] = new String(s.sourceBFields[i].getText()); }
                        
                        try {
                            mainPanel.removeAll();
                            mainPanel.revalidate();
                            mainPanel.repaint();
                            stepNumber++;
                            buildStepThree( projName, srcType, s.sourceADBType,
                                s.sourceBDBType, srcAStr, srcBStr );
                        } catch (ClassNotFoundException ex) {
                            //Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "ERROR!\nClassNotFoundException");
                        } catch (SQLException ex) {
                            //Logger.getLogger(createNewProject.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "ERROR!\nSQLException Step Two");
                        }
                    }else{ JOptionPane.showMessageDialog(null, "ERROR!\nField left blank."); }
                } 
            });
                    
            createButtonPanel.revalidate();
            createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(createButtonPanel);
            mainPanel.revalidate();
        }
//        { buildStepTwoA(projName, destChoice); }
//        else if( srcType.equals(" XML") )
//        { buildStepTwoB(projName, destChoice, 1); }
//        else if( srcType.equals(" Excel") )
//        { buildStepTwoB(projName, destChoice, 2); }
//        else if( srcType.equals(" Text") )
//        { buildStepTwoB(projName, destChoice, 3); }
//        else if( srcType.equals(" Website") )
//        { buildStepTwoC(projName, destChoice); }
        
        else{ JOptionPane.showMessageDialog(null, "Error in: buildStepTwo\n"
                + "Invalid source type specified: " + "'" + srcType + "'"); }
    }/* end buildStepTwo */
    
    //Step two, source type "Database"
    public void buildStepTwoA(String projName, String destChoice)
    {
//        buildHeaderPanel();
        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(headerPanel);        
                 
        if( destChoice == "Database" )
        {
            JPanel dbPanel = new JPanel();
            dbPanel.setLayout( new BoxLayout(dbPanel, BoxLayout.X_AXIS) );
            
            JPanel innerPanelLeft = new JPanel();
            innerPanelLeft.setMaximumSize(new Dimension(300, 400));
            innerPanelLeft.setLayout( new BoxLayout(innerPanelLeft, BoxLayout.Y_AXIS) );
            
            JLabel leftTitle = new JLabel("Source: ");
            leftTitle.setFont(new java.awt.Font("Century Gothic", 0, 18));
            leftTitle.setForeground(new java.awt.Color(102, 102, 102));
            leftTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            innerPanelLeft.add(leftTitle);
            
            String[] dbStrings = { "Oracle", "mySQL", "SQL Server", "PostgreSQL" };
            final JList dbList = new JList(dbStrings);
            dbList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            dbList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            dbList.setVisibleRowCount(-1);
            dbList.setFixedCellWidth(200);
            dbList.setFixedCellHeight(30);
            
            dbList.addListSelectionListener(new ListSelectionListener(){
               public void valueChanged(ListSelectionEvent e) {
                   if(e.getValueIsAdjusting()) { return; }
                   sourceDBType = new String(String.valueOf(dbList.getSelectedValue()));
               }
            });
        
            dbList.setAlignmentX(Component.LEFT_ALIGNMENT);
            innerPanelLeft.add(dbList);
            
            innerPanelLeft.setAlignmentX(Component.LEFT_ALIGNMENT);
            dbPanel.add(innerPanelLeft);
            
            JPanel innerPanelRight = new JPanel();
            innerPanelRight.setMaximumSize(new Dimension(300, 400));
            innerPanelRight.setMinimumSize(new Dimension(100, 100));
            innerPanelRight.setLayout( new BoxLayout(innerPanelRight, BoxLayout.Y_AXIS) );
            
            JPanel destRows[] = new JPanel[5];
            JLabel destLabels[] = new JLabel[5];
            
            //dbPanel.add( new JSeparator(SwingConstants.VERTICAL) );
            buildDBConnectPanel("Destination", innerPanelRight, destFields, destRows, destLabels );
            
            //Add all the main panels to dbPanel
            innerPanelRight.setAlignmentX(Component.LEFT_ALIGNMENT);
            dbPanel.add(innerPanelRight);
            mainPanel.add(dbPanel);
        }
        else if( destChoice == "File" )
        {
            
        }
        else{ JOptionPane.showMessageDialog(null, "Error in: buildStepTwoA\n"
                + "Invalid destination choice specified."); }
        
//        buildButtonPanel();
        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(createButtonPanel);
        mainPanel.revalidate();
    }/*end build StepTwoA*/
 
    public void buildStepThree(String projName, String srcType, String sourceADBType, 
           String sourceBDBType, String sourceAFields[], String sourceBFields[]) throws ClassNotFoundException, SQLException
    {
        if( srcType.equals(" Database") )
        {
            final createNewProjectDB j = new createNewProjectDB(stepNumber, sourceAFields,
                    sourceBFields, sourceADBType, sourceBDBType);
            
            //srcAFields: 0:Host; 1:Port; 2:Database; 3:Username; 4:Password
            SchemaMapping frame = new SchemaMapping(projName, srcType, sourceADBType,
                    sourceBDBType, j.tblNamesA, j.tblNamesB, j.srcAFields, j.srcBFields);
            frame.setVisible(true);
            dispose();         
        }
        else{ JOptionPane.showMessageDialog(null, "Error in: buildStepThreeA\n"
                + "Invalid destination choice specified."); }
    }
    
    //Next screen from buildStepTwoA: Database
    //Assumed that JList dbList and JTextField destFields[5] has content
    public void buildStepThreeA(String projName, String destChoice)
    {
//        buildHeaderPanel(); 
        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(headerPanel);        
        
        if( destChoice.equals("Database") )
        {
            //Set up a form for sources A and B with login information to DB
            if(sourceDBType.equals("Oracle") || sourceDBType.equals("mySQL") ||
                    sourceDBType.equals("SQL Server") || sourceDBType.equals("PostgreSQL"))
            {
                //buildSourceDBPanels();
                JPanel dbPanel = new JPanel();
                dbPanel.setLayout( new BoxLayout(dbPanel, BoxLayout.X_AXIS) );
            
                JPanel innerPanelA = new JPanel();
                innerPanelA.setMaximumSize(new Dimension(300, 400));
                innerPanelA.setMinimumSize(new Dimension(100, 100));
                innerPanelA.setLayout( new BoxLayout(innerPanelA, BoxLayout.Y_AXIS) );
                
                JPanel srcARows[] = new JPanel[5];
                JLabel srcALabels[] = new JLabel[5];
            
                buildDBConnectPanel("Source A", innerPanelA, sourceAFields, srcARows, srcALabels );
                innerPanelA.setAlignmentX(Component.LEFT_ALIGNMENT);
                dbPanel.add(innerPanelA);
                
                dbPanel.add( Box.createHorizontalGlue() );
                
                JPanel srcBRows[] = new JPanel[5];
                JLabel srcBLabels[] = new JLabel[5];
                
                JPanel innerPanelB = new JPanel();
                innerPanelB.setMaximumSize(new Dimension(300, 400));
                innerPanelB.setMinimumSize(new Dimension(100, 100));
                innerPanelB.setLayout( new BoxLayout(innerPanelB, BoxLayout.Y_AXIS) );
                
                buildDBConnectPanel("Source B", innerPanelB, sourceBFields, srcBRows, srcBLabels );
                innerPanelB.setAlignmentX(Component.LEFT_ALIGNMENT);
                dbPanel.add(innerPanelB);                
                
                mainPanel.add(dbPanel);
            }
            else{ JOptionPane.showMessageDialog(null, "Error!\n"
                    + "Invalid destination choice selected.");
            }
        }
        else if( destChoice.equals("File") )
        {
            
        }
        else{ 
            JOptionPane.showMessageDialog(null, "Unknown error!\n"
                + "Error occured in: buildStepThreeA, if('Database')elseif('File')ELSE.." );
        }
        
        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(createButtonPanel);
        mainPanel.revalidate();
    }/*end buildStepThreeA*/
    
    public void buildStepThreeB()
    {
        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(headerPanel);        
        
        
        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(createButtonPanel);
        mainPanel.revalidate();
    }
    
    public void buildStepThreeC()
    {
        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(headerPanel);        
        
        
        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(createButtonPanel);
        mainPanel.revalidate();
    }    
    
    public void buildStepFour()
    {
        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(headerPanel);        
        
    
        
        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(createButtonPanel);
        mainPanel.revalidate();
    }
    
// User has submitted connection information and now the program will attempt to
// connect to the given datbases and display all tables available for merging.
// User may select all of a few.
    public void buildStepFourA(String destChoice) throws SQLException, ClassNotFoundException
    {
        mainPanel.add( Box.createRigidArea(new Dimension(0, 3)) );
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(headerPanel);
        
        if( destChoice.equals("Database") )
        {
            ArrayList tblNamesA;
            ArrayList tblNamesB;
            listModelA = new javax.swing.DefaultListModel();
            listModelB = new javax.swing.DefaultListModel();
            
            if(sourceDBType.equals("Oracle"))
            {
                OracleController s = new OracleController();
                s.LoadOraclePlugin();
                s.ConnectToServer(sourceAFields[0].getText(), Integer.parseInt(sourceAFields[1].getText()), 
                        sourceAFields[2].getText(), sourceAFields[3].getText(), sourceAFields[4].getText());
                
                s.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                s.loadTable();
                tblNamesA = s.getTableNamesFromServer();
                
                OracleController j = new OracleController();
                j.LoadOraclePlugin();
                j.ConnectToServer(sourceBFields[0].getText(), Integer.parseInt(sourceBFields[1].getText()), 
                        sourceBFields[2].getText(), sourceBFields[3].getText(), sourceBFields[4].getText());
                j.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                j.loadTable();
                tblNamesB = j.getTableNamesFromServer();
                
            }
            else if(sourceDBType.equals("mySQL"))
            {
                SQLController s = new SQLController();
                s.LoadSQLPlugin();
                s.ConnectToServer(sourceAFields[0].getText(), Integer.parseInt(sourceAFields[1].getText()), 
                        sourceAFields[2].getText(), sourceAFields[3].getText(), sourceAFields[4].getText());
                s.allThree("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                tblNamesA = s.getTableNamesFromServer();
                
                SQLController j = new SQLController();
                j.LoadSQLPlugin();
                j.ConnectToServer(sourceBFields[0].getText(), Integer.parseInt(sourceBFields[1].getText()), 
                        sourceBFields[2].getText(), sourceBFields[3].getText(), sourceBFields[4].getText());
                j.allThree("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                tblNamesB = j.getTableNamesFromServer();
            }
            else if(sourceDBType.equals("SQL Server"))
            {
                SQLServerController s = new SQLServerController();
                s.LoadSQLServerPlugin();
                s.ConnectToServer(sourceAFields[0].getText(), Integer.parseInt(sourceAFields[1].getText()), 
                        sourceAFields[2].getText(), sourceAFields[3].getText(), sourceAFields[4].getText());
                s.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                s.loadTable();                
                tblNamesA = s.getTableNamesFromServer();
                
                SQLServerController j = new SQLServerController();
                j.LoadSQLServerPlugin();
                j.ConnectToServer(sourceBFields[0].getText(), Integer.parseInt(sourceBFields[1].getText()), 
                        sourceBFields[2].getText(), sourceBFields[3].getText(), sourceBFields[4].getText());
                j.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                j.loadTable();
                tblNamesB = j.getTableNamesFromServer();
            }
            else if(sourceDBType.equals("PostgreSQL"))
            {
                PostgreController s = new PostgreController();
                s.LoadPostgrePlugin();
                s.ConnectToServer(sourceAFields[0].getText(), Integer.parseInt(sourceAFields[1].getText()), 
                        sourceAFields[2].getText(), sourceAFields[3].getText(), sourceAFields[4].getText());
                s.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                s.loadTable();                
                tblNamesA = s.getTableNamesFromServer();
                
                PostgreController j = new PostgreController();
                j.LoadPostgrePlugin();
                j.ConnectToServer(sourceBFields[0].getText(), Integer.parseInt(sourceBFields[1].getText()), 
                        sourceBFields[2].getText(), sourceBFields[3].getText(), sourceBFields[4].getText());
                j.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
                j.loadTable();
                tblNamesB = j.getTableNamesFromServer();
            }
            else{ JOptionPane.showMessageDialog(null, "Error!\n"
                    + "Error found in buildStepFourA:  if(sourceDBType.equals..."
                    + "\nSource type '" + sourceDBType + "' not found.");
                    tblNamesA = new TableList();
                    tblNamesB = new TableList();
            }
            
            //Load tables into List
            for(int i = 0; i < tblNamesA.size(); i++ )
            { listModelA.addElement( tblNamesA.get(i) ); }
            
            for(int i = 0; i < tblNamesB.size(); i++ )
            { listModelB.addElement( tblNamesB.get(i) ); }
        }
        else if( destChoice.equals("File") )
        {
            
        }
        else{ 
            JOptionPane.showMessageDialog(null, "Unknown error!\n"
                + "Error occured in: buildStepFourA, if('Database')elseif('File')ELSE.." );
        }
        
        createButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(createButtonPanel);
        mainPanel.revalidate();        
    }            
    
    
    //checkRequired:
    //Makes sure the current step has submitted the required fields
    public boolean checkRequired()
    {
        boolean isMissing = false;
        
        if( stepNumber == 0 )//Going from step zero to step one
        {
            String missing = "";
            if( sourceJList.isSelectionEmpty() )
            { isMissing = true; missing += "Source Type\n"; }
            
//            if( projNameField.getText().equals("") );
//            { isMissing = true; missing += "Project Name\n"; }
            
//            if( destRadioGroup.isSelected(null) )
//            { isMissing = true; missing += "Destination\n"; }
            
            if(isMissing)
            { JOptionPane.showMessageDialog(null, 
                    "Error in: checkRequired, stepNumber " + stepNumber + 
                    "\n The following fields are empty:\n" + missing);
            }
        }
        
        return isMissing;
    }
    
    /*Action Listener for option Buttons*/
    public void optionBtnEvent(ActionEvent e) throws ClassNotFoundException, SQLException
    {//String projName, String srcType, String destChoice
        if(e.getActionCommand() == "Next")
        {
            switch(stepNumber)
            {
                case 0: mainPanel.removeAll();
                        mainPanel.repaint();
                        if( !checkRequired() )
                        {   stepNumber++; 
                            buildStepTwo(projNameField.getText(), 
                                String.valueOf(sourceJList.getSelectedValue()) );
                        }
                        break;
                case 1: mainPanel.removeAll();
                        mainPanel.repaint();
//                        buildStepThree();
                        stepNumber++;
                        break;
                case 2: mainPanel.removeAll();
                        mainPanel.repaint();
                        buildStepFour();
                        stepNumber++;
                        break;
                default: JOptionPane.showMessageDialog(null, "Unknown Error!");
                        break;
            } 
        }
        else if(e.getActionCommand() == "Cancel")
        {
            int option = JOptionPane.showConfirmDialog(null, "Confirm",
                    "Are you sure you want to cancel?", JOptionPane.YES_NO_OPTION );
            
            if( option == JOptionPane.YES_OPTION )
            { System.exit(0); }
        }
        else if(e.getActionCommand() == "Help")
        {   //Create instance of "Help" JFrame class
            //new projectHelp().setVisible(true);
        }
    }
    
    /*Action Selection Listener for JList*/
    
    
    /*Action Listener for radio Buttons*/
    public void radioEvent(ActionEvent e)
    {
        
    }
    
   public void buildDBConnectPanel(String title, JPanel innerPanel, JTextField textFields[],
            JPanel destRows[], JLabel destLabels[])
    {
            JLabel header = new JLabel(title);
            header.setFont(new java.awt.Font("Century Gothic", 0, 22));
            header.setForeground(new java.awt.Color(102, 102, 102));
            innerPanel.add(header);
            
          //Row 0: "Host: "
            destRows[0] = new JPanel();
            destRows[0].setMaximumSize(new Dimension(200, 15));
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
            destRows[1].setMaximumSize(new Dimension(200, 15));
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
            destRows[2].setMaximumSize(new Dimension(200, 15));
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
            destRows[3].setMaximumSize(new Dimension(200, 15));
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
            destRows[4].setMaximumSize(new Dimension(200, 15));
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
    
    public void buildSourceDBPanels()
    {
//*******       //BUILD FORM FOR SOURCE A
                //JPanel that contains login information for the destination DB
                JPanel innerPanelA = new JPanel();
                innerPanelA.setMaximumSize(new Dimension(300, 400));
                innerPanelA.setMinimumSize(new Dimension(100, 100));
                innerPanelA.setLayout( new BoxLayout(innerPanelA, BoxLayout.Y_AXIS) );

                JLabel destTitle = new JLabel("Source A");
                destTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
                innerPanelA.add(destTitle);

                JPanel srcARows[] = new JPanel[5];
                JLabel srcALabels[] = new JLabel[5];

                //Row 0: "Host: "
                srcARows[0] = new JPanel();
                srcARows[0].setMaximumSize(new Dimension(200, 15));
                srcARows[0].setLayout( new BoxLayout(srcARows[0], BoxLayout.X_AXIS) );
                srcALabels[0] = new JLabel("Host:");
                sourceAFields[0] = new JTextField();

                srcALabels[0].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[0].add(srcALabels[0]);
                srcARows[0].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceAFields[0].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[0].add(sourceAFields[0]);

                //Row 1: "Port: "
                srcARows[1] = new JPanel();
                srcARows[1].setMaximumSize(new Dimension(200, 15));
                srcARows[1].setLayout( new BoxLayout(srcARows[1], BoxLayout.X_AXIS) );
                srcALabels[1] = new JLabel("Port:");
                sourceAFields[1] = new JTextField(5);

                srcALabels[1].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[1].add(srcALabels[1]);
                srcARows[1].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceAFields[1].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[1].add(sourceAFields[1]);

                //Row 2: "Database: "
                srcARows[2] = new JPanel();
                srcARows[2].setMaximumSize(new Dimension(200, 15));
                srcARows[2].setLayout( new BoxLayout(srcARows[2], BoxLayout.X_AXIS) );
                srcALabels[2] = new JLabel("Database:");
                sourceAFields[2] = new JTextField();

                srcALabels[2].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[2].add(srcALabels[2]);
                srcARows[2].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceAFields[2].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[2].add(sourceAFields[2]);            

                //Row 3: "Username: "
                srcARows[3] = new JPanel();
                srcARows[3].setMaximumSize(new Dimension(200, 15));
                srcARows[3].setLayout( new BoxLayout(srcARows[3], BoxLayout.X_AXIS) );
                srcALabels[3] = new JLabel("Username:");
                sourceAFields[3] = new JTextField();

                srcALabels[3].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[3].add(srcALabels[3]);
                srcARows[3].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceAFields[3].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[3].add(sourceAFields[3]);            

                //Row 4: "Password: "
                srcARows[4] = new JPanel();
                srcARows[4].setMaximumSize(new Dimension(200, 15));
                srcARows[4].setLayout( new BoxLayout(srcARows[4], BoxLayout.X_AXIS) );
                srcALabels[4] = new JLabel("Password:");
                sourceAFields[4] = new JTextField();

                srcALabels[4].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[4].add(srcALabels[4]);
                srcARows[4].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceAFields[4].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcARows[4].add(sourceAFields[4]);       

                //Add all rows to innerPanelRight
                for(int i = 0; i < srcARows.length; i++)
                {   srcARows[i].setAlignmentX(Component.LEFT_ALIGNMENT);
                    innerPanelA.add(srcARows[i]);
                    innerPanelA.add( Box.createRigidArea(new Dimension(0, 5)) );
                }
            
//*******       //BUILD FORM FOR SOURCE TWO
                //JPanel that contains login information for the destination DB
                JPanel innerPanelB = new JPanel();
                innerPanelB.setMaximumSize(new Dimension(300, 400));
                innerPanelB.setMinimumSize(new Dimension(100, 100));
                innerPanelB.setLayout( new BoxLayout(innerPanelB, BoxLayout.Y_AXIS) );

                JLabel srcBTitle = new JLabel("Source B");
                srcBTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
                innerPanelB.add(srcBTitle);

                JPanel srcBRows[] = new JPanel[5];
                JLabel srcBLabels[] = new JLabel[5];

                //Row 0: "Host: "
                srcBRows[0] = new JPanel();
                srcBRows[0].setMaximumSize(new Dimension(200, 15));
                srcBRows[0].setLayout( new BoxLayout(srcBRows[0], BoxLayout.X_AXIS) );
                srcBLabels[0] = new JLabel("Host:");
                sourceBFields[0] = new JTextField();

                srcBLabels[0].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[0].add(srcBLabels[0]);
                srcBRows[0].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceBFields[0].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[0].add(sourceBFields[0]);

                //Row 1: "Port: "
                srcBRows[1] = new JPanel();
                srcBRows[1].setMaximumSize(new Dimension(200, 15));
                srcBRows[1].setLayout( new BoxLayout(srcBRows[1], BoxLayout.X_AXIS) );
                srcBLabels[1] = new JLabel("Port:");
                sourceBFields[1] = new JTextField(5);

                srcBLabels[1].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[1].add(srcBLabels[1]);
                srcBRows[1].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceBFields[1].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[1].add(sourceBFields[1]);

                //Row 2: "Database: "
                srcBRows[2] = new JPanel();
                srcBRows[2].setMaximumSize(new Dimension(200, 15));
                srcBRows[2].setLayout( new BoxLayout(srcBRows[2], BoxLayout.X_AXIS) );
                srcBLabels[2] = new JLabel("Database:");
                sourceBFields[2] = new JTextField();

                srcBLabels[2].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[2].add(srcBLabels[2]);
                srcBRows[2].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceBFields[2].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[2].add(sourceBFields[2]);            

                //Row 3: "Username: "
                srcBRows[3] = new JPanel();
                srcBRows[3].setMaximumSize(new Dimension(200, 15));
                srcBRows[3].setLayout( new BoxLayout(srcBRows[3], BoxLayout.X_AXIS) );
                srcBLabels[3] = new JLabel("Username:");
                sourceBFields[3] = new JTextField();

                srcBLabels[3].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[3].add(srcBLabels[3]);
                srcBRows[3].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceBFields[3].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[3].add(sourceBFields[3]);            

                //Row 4: "Password: "
                srcBRows[4] = new JPanel();
                srcBRows[4].setMaximumSize(new Dimension(200, 15));
                srcBRows[4].setLayout( new BoxLayout(srcBRows[4], BoxLayout.X_AXIS) );
                srcBLabels[4] = new JLabel("Password:");
                sourceBFields[4] = new JTextField();

                srcBLabels[4].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[4].add(srcBLabels[4]);
                srcBRows[4].add( Box.createRigidArea(new Dimension(10, 0)) );
                sourceBFields[4].setAlignmentX(Component.LEFT_ALIGNMENT);
                srcBRows[4].add(sourceBFields[4]);       

                //Add all rows to innerPanelRight
                for(int i = 0; i < srcBRows.length; i++)
                {   srcBRows[i].setAlignmentX(Component.LEFT_ALIGNMENT);
                    innerPanelB.add(srcBRows[i]);
                    innerPanelB.add( Box.createRigidArea(new Dimension(0, 5)) );
                }            
                
                JPanel sourceABPanel = new JPanel();
                //sourceABPanel.setMaximumSize(new Dimension(490,));
                sourceABPanel.setMinimumSize(new Dimension( 400, 300));
                sourceABPanel.setLayout( new BoxLayout(sourceABPanel, BoxLayout.X_AXIS) );                
                
                innerPanelA.setAlignmentX(Component.LEFT_ALIGNMENT);
                sourceABPanel.add(innerPanelA);
                innerPanelB.setAlignmentX(Component.RIGHT_ALIGNMENT);
                sourceABPanel.add(innerPanelB);
                
                sourceABPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                mainPanel.add(sourceABPanel);
    }/* end buildSourceDBPanels */
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create New Project");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new createNewProject().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
