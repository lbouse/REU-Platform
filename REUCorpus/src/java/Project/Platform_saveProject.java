/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author Bouse
 */
public class Platform_saveProject extends javax.swing.JInternalFrame {
/*******************************************************************************
**                                  VARIABLES                                 **
*******************************************************************************/
    private JLabel headerLabel;
    private JPanel saveButtonPanel = new JPanel();
    private JButton nextBtn = new JButton("Next");
    private JButton cancelBtn = new JButton("Cancel");
    private JButton helpBtn = new JButton("Help");
    
    private int DBfields = 6;
    private List<String[]> DBconnectDestList = new ArrayList<String[]>();
    private String DBconnectDest[] = new String[DBfields];
    private String projName;
    private String selectedSaveFormat;
    private String selectedAttributeOption;
    private String selectedNameOption;
    private int stepNumber = 0; //Step number "save" is currently on
    
    private int sourceCnt;
    private String dataTypes[];
    private List<String[]> dbConnects = new ArrayList<String[]>();
    private String excelFiles[];
    private LinkedList<sourceNode> localSchema = new LinkedList<sourceNode>();
    private LinkedList<linkNode> schemaLinks = new LinkedList<linkNode>();
    
/*******************************************************************************
**                                CONSTRUCTORS                                **
*******************************************************************************/    
    /**
     * Creates new form saveProject
     */
    /**
     * Creates new form Platform_saveProject
     */
    public Platform_saveProject() {
        projName = new String("Example");
        sourceCnt = 0;
        initComponents();
        
        mainPanel.setLayout( new BoxLayout(mainPanel, BoxLayout.Y_AXIS) );
        
        headerLabel = new JLabel("Save Project " + projName);
        headerLabel.setFont(new java.awt.Font("Century Gothic", 0, 22));
        headerLabel.setForeground(new java.awt.Color(102, 102, 102));
        headerLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
        mainPanel.add(headerLabel);    
        
        
      //BUTTON PANEL
      // [NEXT/FINISH] - [CANCEL] - [HELP] Options
        saveButtonPanel.setLayout( new BoxLayout(saveButtonPanel, BoxLayout.X_AXIS) );
        saveButtonPanel.setMinimumSize( new Dimension(300, 50) );

      /*Add the buttons's Action Listeners*/
        nextBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                }
        } });
        cancelBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                }
        } });
        helpBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                }
        } });
        
        saveButtonPanel.add( Box.createHorizontalGlue() );
        nextBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveButtonPanel.add(nextBtn);
        cancelBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveButtonPanel.add(cancelBtn);
        helpBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveButtonPanel.add(helpBtn);
        
        buildStepOne();
    }//end Platform_saveProject()    

    public Platform_saveProject(String pName, int srcCnt, String dTypes[], List<String[]> dbConns,
            String exlFiles[], LinkedList<sourceNode> lSchemas, LinkedList<linkNode> sLinks)
    {
        projName = new String(pName);
        sourceCnt = srcCnt;
        dataTypes = dTypes;
        dbConnects = dbConns;
        excelFiles = exlFiles;
        localSchema = lSchemas;
        schemaLinks = sLinks;
        
        initComponents();
        mainPanel.setLayout( new BoxLayout(mainPanel, BoxLayout.Y_AXIS) );
        
        headerLabel = new JLabel("Save Project " + projName);
        headerLabel.setFont(new java.awt.Font("Century Gothic", 0, 22));
        headerLabel.setForeground(new java.awt.Color(102, 102, 102));
        headerLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
        
      //BUTTON PANEL
      // [NEXT/FINISH] - [CANCEL] - [HELP] Options
        saveButtonPanel.setLayout( new BoxLayout(saveButtonPanel, BoxLayout.X_AXIS) );
        saveButtonPanel.setMinimumSize( new Dimension(450, 50) );

      /*Add the buttons's Action Listeners*/
        nextBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                }
        } });
        cancelBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                }
        } });
        helpBtn.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(ActionEvent e) {try {
                    optionBtnEvent(e);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(saveProject.class.getName()).log(Level.SEVERE, null, ex);
                }
        } });
        
        saveButtonPanel.add( Box.createHorizontalGlue() );
        nextBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveButtonPanel.add(nextBtn);
        cancelBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveButtonPanel.add(cancelBtn);
        helpBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        saveButtonPanel.add(helpBtn);
        
        buildStepOne();
    }/* end constructor saveProject */
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        setClosable(true);
        setResizable(true);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

/*******************************************************************************
**                              BUILDING STEPS                                **
*******************************************************************************/  
    private void buildStepOne()
    {   //Remove all from mainPanel; make sure the slate is clean.
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(headerLabel);
        stepNumber = 1;
        
        //STEP CONTENTS
        JPanel stepOnePanel = new JPanel();
        stepOnePanel.setLayout( new BoxLayout(stepOnePanel, BoxLayout.X_AXIS) );
        
        JLabel stepOneDialog = new JLabel("How do you want the Global Schema stored?");
        
        mainPanel.add( Box.createRigidArea(new Dimension(0,20)) );
        stepOneDialog.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(stepOneDialog);
        
        JRadioButton fileOption = new JRadioButton("File");
        fileOption.addActionListener(new java.awt.event.ActionListener(){
          public void actionPerformed(ActionEvent e) { selectedSaveFormat = new String(e.getActionCommand()); } });
        JRadioButton dbOption = new JRadioButton("Database");
        dbOption.addActionListener(new java.awt.event.ActionListener(){
          public void actionPerformed(ActionEvent e) { selectedSaveFormat = new String(e.getActionCommand()); } });
        ButtonGroup optionGroup = new ButtonGroup();
        optionGroup.add(fileOption);
        optionGroup.add(dbOption);
        
        fileOption.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbOption.setAlignmentX(Component.LEFT_ALIGNMENT);
        stepOnePanel.add(fileOption);
        stepOnePanel.add(dbOption);

        mainPanel.add( Box.createRigidArea(new Dimension(0, 15)) );
        stepOnePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(stepOnePanel);
        
        mainPanel.add( Box.createVerticalGlue() );
        saveButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(saveButtonPanel);        
        mainPanel.revalidate();        
    }/* end buildStepOne */
    
        private void buildStepTwo() throws ClassNotFoundException, SQLException
    {//Remove all from mainPanel; make sure the slate is clean.
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(headerLabel);
        stepNumber = 2;
        
        //STEP CONTENTS
        if(  selectedSaveFormat.equals("File") )
        { 
            
        }
        else if( selectedSaveFormat.equals("Database") )
        {
            //Get information for connecting to target DB
            final saveProjectDB s = new saveProjectDB();
            s.dbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(s.dbPanel);
            
            nextBtn.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    /* Check to make sure user specified two files */
//                    if( !s.checkAll() ){
                        DBconnectDest = new String[DBfields]; //Clear current array
                        DBconnectDest[0] = new String(s.sourceADBType);
                        for(int i = 0; i < DBfields-1; i++)//Get the database connection information
                        { DBconnectDest[i+1] = new String( s.sourceAFields[i].getText() ); }
//                    }else{ JOptionPane.showMessageDialog(null, "ERROR!\nField left blank."); }
                } 
            });            
        }
        else{ JOptionPane.showMessageDialog(null, "ERROR!\nInvalid save format selected."); }
        
        mainPanel.add( Box.createVerticalGlue() );        
        saveButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(saveButtonPanel);        
        mainPanel.revalidate();            
    }/* end buildStepTwo */
    
    private void buildStepThree()
    {        
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(headerLabel);
        stepNumber = 3;
        
        JPanel stepThreePanel = new JPanel();
        stepThreePanel.setLayout( new BoxLayout(stepThreePanel, BoxLayout.Y_AXIS) );
        
        JLabel threeHeader = new JLabel("Global Schema Options");
        threeHeader.setAlignmentX( Component.LEFT_ALIGNMENT );
        stepThreePanel.add(threeHeader);

        JLabel threeLabel1 = new JLabel("Field Attributes");
        threeLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18));
        threeLabel1.setForeground(new java.awt.Color(102, 102, 102));
        threeLabel1.setAlignmentX( Component.LEFT_ALIGNMENT );
        stepThreePanel.add(threeLabel1);
        
        JRadioButton convertOption = new JRadioButton("Convert linked attributes to match");
        convertOption.addActionListener(new java.awt.event.ActionListener(){
          public void actionPerformed(ActionEvent e) { selectedAttributeOption = new String(e.getActionCommand()); } });
        JRadioButton requireOption = new JRadioButton("Require linked attributes to match");
        requireOption.addActionListener(new java.awt.event.ActionListener(){
          public void actionPerformed(ActionEvent e) { selectedAttributeOption = new String(e.getActionCommand()); } });
        ButtonGroup attributeGroup = new ButtonGroup();
        attributeGroup.add(convertOption);
        attributeGroup.add(requireOption);
        
        convertOption.setAlignmentX(Component.LEFT_ALIGNMENT);
        requireOption.setAlignmentX(Component.LEFT_ALIGNMENT);
        stepThreePanel.add(convertOption);
        stepThreePanel.add(requireOption);        
        
        JLabel threeLabel2 = new JLabel("Field Name");
        threeLabel2.setFont(new java.awt.Font("Century Gothic", 0, 18));
        threeLabel2.setForeground(new java.awt.Color(102, 102, 102));
        threeLabel2.setAlignmentX( Component.LEFT_ALIGNMENT );
        stepThreePanel.add(threeLabel2);
        
        JRadioButton  manuallyOption = new JRadioButton("Manually enter each new field name");
        manuallyOption.addActionListener(new java.awt.event.ActionListener(){
          public void actionPerformed(ActionEvent e) { selectedNameOption = new String(e.getActionCommand()); } });
        JRadioButton automaticOption = new JRadioButton("Automatically name new fields");
        automaticOption.addActionListener(new java.awt.event.ActionListener(){
          public void actionPerformed(ActionEvent e) { selectedNameOption = new String(e.getActionCommand()); } });
        ButtonGroup nameGroup = new ButtonGroup();
        nameGroup.add(manuallyOption);
        nameGroup.add(automaticOption);
        
        manuallyOption.setAlignmentX(Component.LEFT_ALIGNMENT);
        automaticOption.setAlignmentX(Component.LEFT_ALIGNMENT);
        stepThreePanel.add(manuallyOption);
        stepThreePanel.add(automaticOption);     
        
        mainPanel.add( Box.createRigidArea(new Dimension(0,20)) );
        stepThreePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(stepThreePanel);
        
        mainPanel.add( Box.createVerticalGlue() );        
        saveButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(saveButtonPanel);        
        mainPanel.revalidate();             
    }/* end buildStepThree */
   
    private void buildStepFour()
    {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.add(headerLabel);
        stepNumber = 4;
        
      //THE FOLLOWING DISPLAY LIST IS MADE FOR TESTING PURPOSES ONLY.
        JPanel linksPanel = new JPanel();
        linksPanel.setLayout( new BoxLayout(linksPanel, BoxLayout.Y_AXIS) );
        JTextArea linksText = new JTextArea();
        linksText.setText("New Global Schema::\n");
//        for(int i = 0; i < schemaLinks.size(); i++)
//        { linksText.append(schemaLinks.get(i).toString()); }
        linksText.setAlignmentX(Component.LEFT_ALIGNMENT);
        linksPanel.add(linksText);

        JScrollPane displayLinks = new JScrollPane();
        displayLinks.setViewportView(linksPanel);
        displayLinks.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(displayLinks);
      //---------
        
        //Begin Global Schema Generation
        LinkedList<saveProject.GlobalSchemaNode> gSchemaList = new LinkedList<saveProject.GlobalSchemaNode>();
        LinkedList<linkNode> linkList = schemaLinks;
        LinkedList<fieldNode> queue = new LinkedList<fieldNode>();
        LinkedList<fieldNode> listA = new LinkedList<fieldNode>();
        LinkedList<fieldNode> listB = new LinkedList<fieldNode>();
        
        for(int i = 0; i < schemaLinks.size(); i++)
        { 
            schemaLinks.get(i).nodeA.setSourceName( "listA");
            schemaLinks.get(i).nodeB.setSourceName( "listB");
            listA.add(schemaLinks.get(i).nodeA); 
            listB.add(schemaLinks.get(i).nodeB); 
        }
        
        System.out.println("\nlinkList size: " + linkList.size());
        for(int i = 0; i < linkList.size(); i++)
        {
            System.out.print( "linkList(" + i + "), ");
            if( !linkList.get(i).nodeA.completed && !linkList.get(i).nodeB.completed)
            {
                gSchemaList.add(new saveProject.GlobalSchemaNode( linkList.get(i).nodeA.getTitle() ));
                
                queue.add(linkList.get(i).nodeA);
                queue.add(linkList.get(i).nodeB);     

                int k = 0;
                System.out.println("\nQueue " + i + " Begin:");
                while( !queue.isEmpty() )
                {
                    System.out.print( "Queue(" + k + "), ");
                    if(queue.getFirst().getFieldSource() < sourceCnt)//Assume its listA
                    { //Search listA for queue.First
                        int index = listA.indexOf( queue.getFirst() ); //returns -1 if none found
                        while( index >= 0 )
                        {   //Check if this node has already been added to queue.
                            if(!listB.get(index).check && !listB.get(index).completed)
                            { 
                                queue.add(listB.get(index));
                                listB.get(index).check = true;
                                listB.remove(index);
                                listA.remove(index);
                            }
                            
                            index = listA.indexOf( queue.getFirst() );
                        }
                    }//end if(queue.getFirst.getFieldSource < sourceCnt)
                    else{ //else assume its listB
                        int index = listB.indexOf( queue.getFirst() ); //returns -1 if none found
                        while( index >= 0 )
                        {
                            if( index < listA.size() )
                            {
                                if(!listA.get(index).check && !listA.get(index).completed)
                                { 
                                    queue.add(listA.get(index));
                                    listA.get(index).check = true;
                                    listA.remove(index);
                                    listB.remove(index);
                                }
                                index = listB.indexOf( queue.getFirst() );
                            }
                        }                
                    }//end else

                    queue.getFirst().completed = true;
                    gSchemaList.getLast().relationalList.add(queue.getFirst());
                    queue.remove(queue.getFirst());
                    k++;
                }//end while !queue.isEmpty
            }//end if(nodeA != completed && nodeB != completed
        }//end for(i < linkList.size)
        
      //Done?? Print out to System.out to test.
        for(int i = 0; i < gSchemaList.size(); i ++)
        { System.out.print(gSchemaList.get(i).printSchema()); }
        
        System.out.println("\n\nDONE!");
        mainPanel.add( Box.createVerticalGlue() );        
        saveButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(saveButtonPanel);        
        mainPanel.revalidate();          
    }/* end buildStepFour */

/*******************************************************************************
**                               METHODS: GENERAL                             **
*******************************************************************************/    
    
    private void optionBtnEvent(ActionEvent e) throws ClassNotFoundException, SQLException
    {
        if( e.getActionCommand().equals("Next") )
        { //Decide what to do depending on what step number currently on
            switch(stepNumber)
            {
                case 0: //Not even on a step yet.
                    JOptionPane.showMessageDialog(null, "ERROR!\n"
                            + "Save Project is at step zero; cannot go next.");
                    break;
                case 1: //Next was pressed from "Step One"; save format selection
                    if( passCheck() ){ buildStepTwo(); }
                    else{ JOptionPane.showMessageDialog(null, "ERROR!\nMissing Field."); }
                    break;
                case 2: //'Next' was pressed from "Step Two"; file select / db connect info
                    if( passCheck() ){ buildStepThree(); }
                    else{ JOptionPane.showMessageDialog(null, "ERROR!\nUnknown error in:"
                            + "optionBtnEvent switch(stepnumber) case 2"); }
                    break;
                case 3: //'Next' was pressed from "Step Three"; global schema constraints
                    if( passCheck() ){ buildStepFour(); }
                    else{ JOptionPane.showMessageDialog(null, "ERROR!\n"
                            + "Unknown error in: optionBtnEvent switch(stepNumber) case 3"); }
                    break;
                default: JOptionPane.showMessageDialog(null, "ERROR!\nInvalid step number occured "
                        + "in optionBtnEvent switch(stepNumber) default");
                    break;
            }/*switch(stepNumber)*/
        }/*if( e == "Next")*/
        else if(e.getActionCommand().equals("Cancel"))
        { dispose(); }
    }/* end method private void optionBtnEvent */
    
    private boolean passCheck()
    {
        boolean ans = false;
        
        if(stepNumber == 1)
        { //Check passes if a save format is selected
            if(!selectedSaveFormat.isEmpty()){ ans = true; }
        }
        else if(stepNumber == 2)
        { //Check if Excel documents are selected or Database information
            ans = true;            
        }
        else if(stepNumber == 3)
        { //Check if all options are selected
            if( !selectedNameOption.isEmpty() && !selectedAttributeOption.isEmpty() )
            { ans = true; }
        }
        else{ JOptionPane.showMessageDialog(null, "ERROR!\n"
                + "Invalid stepNumber: " + stepNumber + " in passCheck."); }
        
        return true; //FIX LATER. PRETEND EVERYTHING'S FINE FOR NOW.
    }
   
    //Returns a linked list of indexes of the nodes in this list.
    //Index correlates to the index of this node in the linked list of linkNodes
    public LinkedList<Integer> findNodes(LinkedList<saveProject.nodeList> lst, String findMe)
    {
        LinkedList<Integer> rtnList = new LinkedList<Integer>();

        for(int i = 0; i < lst.size(); i++)
        {   //First check if node is "completed". If it is, ignore it.
            if( !lst.get(i).isCompleted() )
            { if(lst.get(i).getNodeName().equals(findMe)){rtnList.add(i);} }
        }

        return rtnList;
    }
    
/*******************************************************************************
**                         GENERATING GLOBAL SCHEMA                           **
*******************************************************************************/          
        
/*******************************************************************************
**                               INNER CLASSES                                **
*******************************************************************************/  
    
      //Each GlobalSchemaNode is a set of related fieldNodes
    public static class GlobalSchemaNode{
        private String fieldName;
        public LinkedList<fieldNode> relationalList = new LinkedList<fieldNode>();
        
        public GlobalSchemaNode(String name)
        { fieldName = name; }
        
        public String printSchema()
        {
            String temp = "\nField Name: " + fieldName + " Related Fields:\n";
            for(int i = 0; i < relationalList.size(); i++)
            {
                temp = temp + "\tName: " + relationalList.get(i).getTitle()
                        + "   Source: " + relationalList.get(i).getSourceName();
                
                if(i%2 > 0){ temp = temp + "\n"; }
            }
            
            return temp;
        }
    }
    public static class nodeList{
        private String listName;
        private String nodeName;
        private boolean check;
        private boolean completed;
        
        public fieldNode node;
        
        public void setNodeName(String s){ nodeName = new String(s); }
        public void setName(String s){ listName = new String(s); }
        public void setCheck(boolean b){ check = b; }
        public void setCompleted(boolean b){ completed = b; }
        public void setCheck(){ check = true; }
        public void setCompleted(){ completed = true; }
        
        public String getNodeName(){ return nodeName; }
        public String getName(){ return listName; }
        public boolean isChecked(){ return check; }
        public boolean isCompleted(){ return completed; }
        
        public nodeList(fieldNode n, String lName)
        {
            node = n;
            listName = lName;
            nodeName = n.getText();
        }
        
    }
 
    
}
