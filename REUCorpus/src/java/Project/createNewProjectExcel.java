/*
 * createNewProjectExcel
 * GUI for guiding user through uploading their excel files to the application
 * to integrate it. Includes selecting orientation (horizontal or vertical) and
 * confirmation on schema.
 * Based off of createNewProjectDB, but most of which has been gutted.
 * Interacts with createNewProject.java
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
public class createNewProjectExcel {
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
    public javax.swing.JScrollPane scrollPaneA;
    public javax.swing.JScrollPane scrollPaneB;
    public ArrayList tblNamesA = new ArrayList();
    public ArrayList tblNamesB = new ArrayList();    
    
    Border border = LineBorder.createGrayLineBorder();              //DELETE LATER&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    
//**********************************************************************************************************
    
    public createNewProjectExcel(int stepNum) throws ClassNotFoundException, SQLException
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
//            case 2: tblNamesA = new ArrayList( loadDBTables(sourceADBType, tblNamesA, srcAFields) );
//                    tblNamesB = new ArrayList( loadDBTables(sourceBDBType, tblNamesB, srcBFields)) ; 
//                    break;
            default: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "invalid stepNumber occured!");
        }
    }
    
    public createNewProjectExcel(int stepNum, String sourceAFields[], String sourceBFields[],
            String srcADBType, String srcBDBType) throws ClassNotFoundException, SQLException
    {
        stepNumber = stepNum;
        dbPanel.setLayout( new BoxLayout(dbPanel, BoxLayout.X_AXIS) );
//        setSrcFields(sourceAFields, sourceBFields);
//        setSrcDBTypes(srcADBType, srcBDBType);
        
        switch(stepNumber)
        {
            case 0: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "createNewProjectDB calld unexpectedly!");
                    break;
            case 1: buildStepTwoA();
                    break;
//            case 2: tblNamesA = new ArrayList( loadDBTables(sourceADBType, tblNamesA, srcAFields) );
//                    tblNamesB = new ArrayList( loadDBTables(sourceBDBType, tblNamesB, srcBFields)) ;                
//                    break;
            default: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "invalid stepNumber occured!");
        }        
    }
    
        //Step two, source type "Database"
    public void buildStepTwoA()
    {           

    }/* end buildStepTwoA */
    
    //Next screen from buildStepTwoA: Database
    public void buildStepThreeA() throws ClassNotFoundException, SQLException
    {     

    }    
    

   public boolean checkAll()
   {
       boolean rtn = true;
       
//       if( sourceAFields[0].getText().isEmpty() ){ sourceAFields[0].setText("localhost"); }
//       if( sourceAFields[3].getText().isEmpty() ){ sourceAFields[3].setText("root"); }
//
//       if( sourceBFields[0].getText().isEmpty() ){ sourceBFields[0].setText("localhost"); }
//       if( sourceBFields[3].getText().isEmpty() ){ sourceBFields[3].setText("root"); }
//       
//       for(int i = 0; i < fieldLength; i++)
//       {    if(i != 1 && i != 4)
//            {    if( sourceAFields[i].getText().isEmpty() || sourceBFields[i].getText().isEmpty()
//                        || sourceADBType.isEmpty() || sourceBDBType.isEmpty() )
//                { rtn = false; }
//            }
//       }
//       
       return rtn;
   }
}