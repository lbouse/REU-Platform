/*
 * createNewProjectExcel
 * GUI for guiding user through uploading their excel files to the application
 * to integrate it. Includes selecting orientation (horizontal or vertical) and
 * confirmation on schema.
 * Based off of createNewProjectDB, but most of it has been gutted.
 * Interacts with createNewProject.java
 */
package Project;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Bouse
 */
public class createNewProjectExcel {
    public int fieldLength = 5; 
        
    private String sourceTypes[] = { " Database", " Excel", " XML", " Text", " Website" };
    private JList sourceJList = new JList(sourceTypes);
    private ButtonGroup destRadioGroup = new ButtonGroup();
    private JRadioButton destRadioDB = new JRadioButton("Database");
    private JRadioButton destRadioFile = new JRadioButton("File");
    private JButton nextBtn = new JButton("Next");
    private JButton cancelBtn = new JButton("Cancel");
    private JButton helpBtn = new JButton("Help");    
    private JPanel headerPanel = new JPanel();
    private JPanel createButtonPanel = new JPanel();  
    public String chosenOrientation = new String(); 
    
    public static javax.swing.DefaultListModel listModelA = new javax.swing.DefaultListModel();
    public static javax.swing.DefaultListModel listModelB = new javax.swing.DefaultListModel();
    private int stepNumber = 0;
    
    //Public variables
    public JTextField fileA = new JTextField(20);
    public JTextField fileB = new JTextField(20);
    public JLabel horLabel;
    public JLabel vertLabel;
    public JPanel dbPanel = new JPanel();  
    public javax.swing.JScrollPane scrollPaneA;
    public javax.swing.JScrollPane scrollPaneB;
    public ArrayList tblNamesA = new ArrayList();
    public ArrayList tblNamesB = new ArrayList();    
    
    Border border = LineBorder.createGrayLineBorder();              //DELETE LATER&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        
/*******************************************************************************    
**                             GET/SET METHODS                                **
*******************************************************************************/ 
    public String getOrientation(){ return chosenOrientation; }
    public void setOrientation(String x){ chosenOrientation = new String(x); }
    
/*******************************************************************************    
**                             CONSTRUCTORS                                   **
*******************************************************************************/ 
    
    public createNewProjectExcel(int stepNum) throws ClassNotFoundException, SQLException
    {
        stepNumber = stepNum;
        dbPanel.setLayout( new BoxLayout(dbPanel, BoxLayout.Y_AXIS) );
        
        switch(stepNumber)
        {
            case 0: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "createNewProjectDB calld unexpectedly!");
                    break;
            case 1: buildStepTwo(); //Step "two", in the overall scheme of things
                    break;
            case 2: buildStepThree();
                    break;
            default: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "invalid stepNumber occured!\nsource: createNewProkectExcel construct #1");
                    break;
        }
    }
    
    public createNewProjectExcel(int stepNum, String sourceAFields[], String sourceBFields[],
            String srcADBType, String srcBDBType) throws ClassNotFoundException, SQLException
    {
        stepNumber = stepNum;
        dbPanel.setLayout( new BoxLayout(dbPanel, BoxLayout.X_AXIS) );
        
        switch(stepNumber)
        {
            case 0: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "createNewProjectDB calld unexpectedly!");
                    break;
            case 1: buildStepTwo();
                    break;
            case 2: buildStepThree();
            default: JOptionPane.showMessageDialog(null, "ERROR!\n"
                    + "invalid stepNumber occured!\nsource: createNewProkectExcel construct #2");
        }        
    }
    
/*******************************************************************************
**                             METHODS: THE STEPS                             **
*******************************************************************************/
    
/* STEP TWO */
    //User selects two excel files to open.
    public void buildStepTwo()
    {   
        dbPanel.add( Box.createRigidArea(new Dimension(0,10)) );
        JLabel exclLabel = new JLabel("Browse to select two Excel files.");
        exclLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(exclLabel);
        dbPanel.add(Box.createRigidArea(new Dimension(0,15)) );
        
        JPanel rowA = new JPanel();
        rowA.setLayout( new BoxLayout(rowA, BoxLayout.X_AXIS) );
        
        fileA.setMaximumSize( new Dimension(200, 20) );
        fileA.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowA.add(fileA);
        rowA.add( Box.createRigidArea(new Dimension(10,0)) );
        
        JButton browseA = new JButton("Browse");
        browseA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFileA(evt);
            }
        });
        
        browseA.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowA.add(browseA);
        rowA.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(rowA);
        
        dbPanel.add( Box.createRigidArea(new Dimension(0, 5)) );
        
        JPanel rowB = new JPanel();
        rowB.setLayout( new BoxLayout(rowB, BoxLayout.X_AXIS) );
        
        fileB.setMaximumSize( new Dimension(200, 20) );
        fileB.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowB.add(fileB);
        rowB.add( Box.createRigidArea(new Dimension(10,0)) );
        
        JButton browseB = new JButton("Browse");
        browseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFileB(evt);
            }
        });
        
        browseB.setAlignmentX(Component.LEFT_ALIGNMENT);
        rowB.add(browseB);        
        rowB.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(rowB);
    }/* end buildStepTwo */
  
/* STEP THREE */   
    public void buildStepThree()
    {        
        JLabel prompt = new JLabel("How is your data orientated?");
        prompt.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(prompt);
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout( new BoxLayout(optionsPanel, BoxLayout.Y_AXIS) );
        
        ImageIcon horizontal = new ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REUCorpus\\src\\java\\Project\\images\\horizontal.png");
        ImageIcon vertical = new ImageIcon("C:\\Users\\Bouse\\Documents\\NetBeansProjects\\REU-Platform\\REUCorpus\\src\\java\\Project\\images\\vertical.png");
        
        horLabel = new JLabel();
        horLabel.setIcon(horizontal);
        vertLabel = new JLabel();
        vertLabel.setIcon(vertical);
        
        horLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        optionsPanel.add(horLabel);
        optionsPanel.add( Box.createRigidArea(new Dimension(20, 0)) );
        vertLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        optionsPanel.add( vertLabel );
        
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbPanel.add(optionsPanel);
        dbPanel.revalidate();
    }
    
/*******************************************************************************
**                             METHODS: GENERAL                               **
*******************************************************************************/    
    
   public boolean checkAll()
   {
       boolean rtn = true;
       
       return rtn;
   }
   
   //------------------------------
   // The following methods were used from ReadExcelGUI.java
   //------------------------------
      private Sheet readExcelSheet (File file) {
        // open excel file (workbook) for reading
        Workbook wbk;
        try {
            wbk = Workbook.getWorkbook(file);
        } catch (Exception ex) {
            JOptionPane message = new JOptionPane(
                    "Can't read excel file " + file.getPath(),
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (wbk.getNumberOfSheets() <= 0) {
            JOptionPane message = new JOptionPane(
                    "Excel file doesn't have any sheets.",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // return first sheet
        return wbk.getSheet(0);
    }   
   
    public void getFileA(java.awt.event.ActionEvent e)
    {
                File file = chooseExcelFile();
        // do nothing if open dialog was cancelled
        if (file == null) {
            return;
        }

        fileA.setText( file.getPath() );
        //Enable this later, for now we just want the filename. Don't load the data yet.
//        // loading long excel tables may be time consuming, so use wait cursor
//        dbPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//
//        Sheet sheet = readExcelSheet(file);
//        if (sheet == null) { JOptionPane.showMessageDialog(null, "ERROR!\nExcel sheet is empty!"); }
//
//        dbPanel.setCursor(Cursor.getDefaultCursor());
    }
    
    public void getFileB(java.awt.event.ActionEvent e)
    {
        File file = chooseExcelFile();
        if (file == null){ return;}
        
        fileB.setText( file.getPath() );
    }
    
    /** Filter which accepts only xls files */
    private static class XLSFilter extends FileFilter {
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().endsWith(".xls");
        }

        public String getDescription() {
            return "Excel spreadsheet XLS files";
        }
    }   
    
    private File chooseExcelFile () {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new createNewProjectExcel.XLSFilter());

        int returnVal = chooser.showOpenDialog(dbPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        // cancel was clicked
        return null;
    }
}